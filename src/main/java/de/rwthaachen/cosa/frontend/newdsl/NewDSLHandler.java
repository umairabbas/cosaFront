package de.rwthaachen.cosa.frontend.newdsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.rwthaachen.cosa.frontend.database.DBDao;
import de.rwthaachen.cosa.frontend.logic.Logic;

public class NewDSLHandler {
	private DBDao dao;
	private String[] input;
	private DSLParser parser;
	private Map<String, Object> analyzeResult;
	private List<String> errorMessage;
	
	public NewDSLHandler(String[] input, DBDao dao) {
		// TODO Auto-generated constructor stub
		/**
		 * initialization
		 */
		this.input = input;
		this.analyzeResult = new HashMap<String, Object>();
		this.errorMessage = new ArrayList<String>();
		this.dao = dao;
		
		//check input
		DSLInputChecker dslinputchecker = new DSLInputChecker(input);
		this.errorMessage = dslinputchecker.getErrorMessage();
		
		//mapping
		if(errorMessage.size() == 0){//if there is no error
			this.parser = new DSLParser(input);
		}
	}

	public String[] getInput() {
		return input;
	}

	public DSLParser getParser() {
		return parser;
	}

	public Map<String, Object> getAnalyzeResult() {
		return analyzeResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> analyzedDSL(){
		//check if input is correct
		if(errorMessage.size() == 0){
			List<String> err = new ArrayList<String>();
			
			/**
			 * check whether the parser is done correctly or not
			 */
			if(parser.getErrorMessage().size() > 0){
				return parser.getErrorMessage();
			}else{
				/**
				 * check vcsroot availability
				 */
				
				String id = Logic.getIdFromRoot(this.parser.getContent().get("fromlocalrepository").toString(), dao);
				if(id == ""){
					err.add("Root does not recognized, check your spelling");
					return err;
				}else{
					List<String> classnames = new ArrayList<String>();
					List<String> codesmells = new ArrayList<String>();
					List<String> changes = new ArrayList<String>();
					List<String> versions = new ArrayList<String>();
					
					try{
						if(this.parser.getContent().get("classname") != null){
							if(this.parser.getContent().get("classname").getClass().getName().equals("java.lang.String")){
								classnames = new ArrayList<String>();
								classnames.add(this.parser.getContent().get("classname").toString());
							}else{
								classnames = (ArrayList<String>)this.parser.getContent().get("classname");
							}
							
						}else{
							classnames = new ArrayList<String>();
							classnames.add("all");
						}
					}catch(Exception e){
						classnames = new ArrayList<String>();
						classnames.add("all");
						e.printStackTrace();
					}
					
					try{
						if(this.parser.getContent().get("codesmell") != null){
							if(this.parser.getContent().get("codesmell").getClass().getName().equals("java.lang.String")){
								codesmells = new ArrayList<String>();
								codesmells.add(this.parser.getContent().get("codesmell").toString());
							}else{
								codesmells = (ArrayList<String>)this.parser.getContent().get("codesmell");
							}
						}else{
							codesmells = new ArrayList<String>();
							codesmells.add("all");
						}
					}catch(Exception e){
						codesmells = new ArrayList<String>();
						codesmells.add("all");
					}
					
					try{
						if(this.parser.getContent().get("change") != null){
							if(this.parser.getContent().get("change").getClass().getName().equals("java.lang.String")){
								changes = new ArrayList<String>();
								changes.add(this.parser.getContent().get("change").toString());
							}else{
								changes = (ArrayList<String>)this.parser.getContent().get("change");
							}
						}else{
							changes = new ArrayList<String>();
							changes.add("all");
						}
					}catch(Exception e){
						changes = new ArrayList<String>();
						changes.add("all");
					}
					
					try{
						if(this.parser.getContent().get("version") != null){
							if(this.parser.getContent().get("version").getClass().getName().equals("java.lang.String")){
								versions = new ArrayList<String>();
								versions.add(this.parser.getContent().get("version").toString());
							}else{
								versions = (ArrayList<String>)this.parser.getContent().get("version");
							}
						}else{
							versions = new ArrayList<String>();
							versions.add("all");
						}
					}catch(Exception e){
						versions = new ArrayList<String>();
						versions.add("all");
						e.printStackTrace();
					}
					
					/**
					 * find the requested version
					 */
					List<Integer> finalVersions = new ArrayList<Integer>();
					
					for(int i = 0 ; i < versions.size(); i++){
						if(!versions.get(i).equals("all")){
							int requestedVersion;
							try{
								requestedVersion = Integer.parseInt(versions.get(i).toString());
							}catch(Exception e){
								requestedVersion = 0;
								e.printStackTrace();
							}
							
							if(!this.dao.isVersionExists(id, requestedVersion)){
								err.add("Version "+requestedVersion+" is not exist!");
							}else{
								finalVersions.add(requestedVersion);
							}
						}
					}
					/**
					 * check class name availability in all versions
					 */
					for(String classname : classnames){
						if(!this.dao.isClassExist(id, classname) && !classname.equalsIgnoreCase("all")){
							err.add("The classname "+classname+" is not exist in the repository's history.");
							classnames.set(classnames.indexOf(classname), "all");
						}
					}
					
					if(this.parser.getContent().get("make").toString().equals("table")){
//						this.analyzeResult = dao.getAllInformationWithFilter(id, classnames, codesmells, changes, requestedVersion);
						this.analyzeResult = dao.getAllInformationWithFilter2(id, classnames, codesmells, changes, finalVersions);
					}else if(this.parser.getContent().get("make").toString().equals("linegraph")){
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
							List<Map<String, Object>> templist = (List<Map<String, Object>>)temp.get("evolution");
							List<Object> evolution = new ArrayList<Object>();
							
							for(Map<String, Object> tdata : templist){
								if(this.parser.getContent().get("axis").toString().equalsIgnoreCase("Severity Impact")){
									evolution.add(tdata.get("severityImpact"));
								}else if(this.parser.getContent().get("axis").toString().equalsIgnoreCase("Severity Level")){
									evolution.add(tdata.get("severityLevel"));
								}else if(this.parser.getContent().get("axis").toString().equalsIgnoreCase("Harmfulness Level")){
									evolution.add(tdata.get("harmfulnessLevel"));
								}else if(this.parser.getContent().get("axis").toString().equalsIgnoreCase("Changes")){
									evolution.add(tdata.get("totalChanges"));
								}
							}
							allevolution.add(evolution);
						}
						tempresult.put("className", allclassnames);
						tempresult.put("version", allevolution);
						this.analyzeResult = tempresult;
					}
				}
			}
			return err;
		}else{
			return this.errorMessage;
		}
	}

}
