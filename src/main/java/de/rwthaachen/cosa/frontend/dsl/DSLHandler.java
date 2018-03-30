package de.rwthaachen.cosa.frontend.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rwthaachen.cosa.frontend.database.DBDao;
import de.rwthaachen.cosa.frontend.logic.Logic;

public class DSLHandler {
	private DBDao dao;
	private String xml;
	private DSLParser parser;
	private Map<String, Object> analyzeResult;
	
	public DSLHandler(String xml, DBDao dao) {
		// TODO Auto-generated constructor stub
		/**
		 * initialization
		 */
		this.xml = xml;
		this.analyzeResult = new HashMap<String, Object>();
		this.dao = dao;
		
		this.parser = new DSLParser(this.xml);
	}

	public String getXml() {
		return xml;
	}

	public DSLParser getParser() {
		return parser;
	}

	public Map<String, Object> getAnalyzeResult() {
		return analyzeResult;
	}
	
	public List<String> analyzedDSL(){
		List<String> errormessage = new ArrayList<String>();
		
		/**
		 * check whether the parser is done correctly or not
		 */
		if(parser.getErrorMessage().size() >0){
			return parser.getErrorMessage();
		}else{
			/**
			 * check vcsroot availability
			 */
			String id = Logic.getIdFromRoot(this.parser.getXmlcontent().get("vcsroot").toString(), dao);
			
			if(id == ""){
				errormessage.add("Root does not recognized, check your spelling");
				return errormessage;
			}else{
				@SuppressWarnings("unchecked")
				List<String> classnames = (ArrayList<String>) this.parser.getXmlcontent().get("classname");
				@SuppressWarnings("unchecked")
				List<String> codesmells = (ArrayList<String>) this.parser.getXmlcontent().get("codesmell");
				@SuppressWarnings("unchecked")
				List<String> changes = (ArrayList<String>) this.parser.getXmlcontent().get("change");
				int requestedVersion = Integer.parseInt((String) this.parser.getXmlcontent().get("version"));
				
				/**
				 * find the requested version
				 */
				if(requestedVersion != 0){
					if(!this.dao.isVersionExists(id, requestedVersion)){
						errormessage.add("The version you are looking for, is not exist!");
						requestedVersion = 0;
					}
				}
				
				/**
				 * check class name availability in all versions
				 */
				for(String classname : classnames){
					if(!this.dao.isClassExist(id, classname) && !classname.equalsIgnoreCase("all")){
						errormessage.add("The classname "+classname+" is not exist in the repository's history.");
						classnames.set(classnames.indexOf(classname), "all");
					}
				}
				
				if(this.parser.getXmlcontent().get("rootelement").equals("table")){
					this.analyzeResult = dao.getAllInformationWithFilter(id, classnames, codesmells, changes, requestedVersion);
				}else if(this.parser.getXmlcontent().get("rootelement").equals("linegraph")){
					boolean allexist = false;
					for(String classname : classnames){
						if(classname.equalsIgnoreCase("all")){
							allexist = true;
							break;
						}
					}
					
					List<String> allclassnames = new ArrayList<String>();
					
					if(allexist){
						allclassnames = dao.getAllClassName(id);
					}else {
						allclassnames = classnames;
					}
					
					List<List<Object>> allevolution = new ArrayList<List<Object>>();
					Map<String, Object> tempresult = new HashMap<String, Object>();
					
					for(String name : allclassnames){
						Map<String, Object> temp = dao.getCodeSmellEvolutionByFilter(id, name,codesmells, changes);
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> templist = (List<Map<String, Object>>)temp.get("evolution");
						List<Object> evolution = new ArrayList<Object>();
						
						for(Map<String, Object> tdata : templist){
							if(this.parser.getXmlcontent().get("axis").toString().equalsIgnoreCase("Severity Impact")){
								evolution.add(tdata.get("severityImpact"));
							}else if(this.parser.getXmlcontent().get("axis").toString().equalsIgnoreCase("Severity Level")){
								evolution.add(tdata.get("severityLevel"));
							}else if(this.parser.getXmlcontent().get("axis").toString().equalsIgnoreCase("Harmfulness Level")){
								evolution.add(tdata.get("harmfulnessLevel"));
							}else if(this.parser.getXmlcontent().get("axis").toString().equalsIgnoreCase("Changes")){
								evolution.add(tdata.get("totalChanges"));
							}
						}
						allevolution.add(evolution);
					}
					tempresult.put("className", classnames);
					tempresult.put("version", allevolution);
					this.analyzeResult = tempresult;
				}
			}
		}		
		return errormessage;
	}

}
