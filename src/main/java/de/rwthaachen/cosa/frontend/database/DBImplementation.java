package de.rwthaachen.cosa.frontend.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import de.rwthaachen.cosa.frontend.dsl.rules.ChangeRule;
import de.rwthaachen.cosa.frontend.dsl.rules.ClassNameRule;
import de.rwthaachen.cosa.frontend.dsl.rules.CodeSmellRule;
import de.rwthaachen.cosa.frontend.excel.DetectedCodesmellAllVersions;

public class DBImplementation implements DBDao{
	private Connect connection;
	private MongoCollection<org.bson.Document> collection;
	
	private static final float MAX_IMP = 9;
	private static final float MAX_ACT = 9;
	
	public DBImplementation() {
		// TODO Auto-generated constructor stub
		/**
		 * Change the following parameter, if you have different MongoDB configuration
		 */
		connection = new Connect("localhost", 27017, "GithubMining");
		collection = connection.getDb().getCollection("historicalInformation");
	}

	/**
	 * This function is used to get all analyzed VCS and its ID in the database
	 */
	@Override
	public List<Map<String, String>> getIdAndVcsroot() {
		// TODO Auto-generated method stub
		List<Map<String,String>> ltemp = new ArrayList<Map<String,String>>();
		MongoCursor<Document> curr = collection.find().iterator();
		
		while(curr.hasNext()){
			Document doc = curr.next();
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("id", doc.getString("_id"));
			temp.put("vcsroot", doc.getString("vcsRoot"));
			ltemp.add(temp);
		}
		return ltemp;
	}

	/**
	 * This function is used to get VCS analysis data from DB
	 */
	@Override
	public Map<String, Object> getVCSAnalysisDetail(String id, String codesmelltype, String activitytype, int activityLow, int severityLow) {
		// TODO Auto-generated method stub
		//query
		Document query = new Document().append("_id", id);
		
		Map<String, Object> temp = new HashMap<String, Object>();
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		int veryhigh = 0, low = 0, medium = 0, high = 0;
		
		if(curr.hasNext()){
			Document doc = curr.next();
			temp.put("id", doc.getString("_id"));
			temp.put("cvsroot", doc.getString("vcsRoot"));
			temp.put("analyzedDate", doc.get("analyzedDate"));
			
			@SuppressWarnings("unchecked")
			List<Document> version = (List<Document>) doc.get("versions");
			temp.put("totalVersion", version.size());
			
			if(version.get(0) != null){
				temp.put("commitDate", version.get(0).get("commitDate"));
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) version.get(0).get("codesmells");
				List<Map<String, Object>> codesmells = new ArrayList<Map<String,Object>>();
				
				for(Document cs : listcs){
					String csType = (String)cs.get("codesmellType");
					String chType = (String)cs.get("changeType");
					
					if((codesmelltype.equals("all") || codesmelltype.equalsIgnoreCase(csType)) && (activitytype.equals("all") || activitytype.equalsIgnoreCase(chType))){
						Map<String, Object> codesmell = new HashMap<String, Object>();
						codesmell.put("className", cs.get("className"));
						codesmell.put("codesmellType", csType);
						codesmell.put("activityType", chType);
						codesmell.put("programmingLanguage", cs.get("programmingLanguage"));
						codesmell.put("harmfulnessLevel", Math.round(cs.getDouble("harmfulnessLevel")*1000)/1000.0d);
						
						
						float accSeverityLevel = (cs.getDouble("accSeverityLevel").floatValue() == 0f)?0f:cs.getDouble("accSeverityLevel").floatValue();
						float accActivityLevel = (cs.getDouble("accActivityLevel").floatValue() == 0f)?0f:cs.getDouble("accActivityLevel").floatValue();
						
						codesmell.put("activenessLevel", Math.round((100 * accActivityLevel)/(version.size() * MAX_ACT) * 100)/100.0d);
						
						/**
						 * classify its activeness level
						 */
//						float halfPossible = (3 * version.size())/2;
						float thresholdActivity = (MAX_ACT * version.size()) * activityLow /100;
						float thresholdSeverity = (MAX_IMP * version.size()) * severityLow /100;
						
						if(accSeverityLevel > thresholdSeverity && accActivityLevel > thresholdActivity){
							codesmell.put("classificationType", "VeryHigh");
							veryhigh++;
						}else if(accSeverityLevel > thresholdSeverity && accActivityLevel <= thresholdActivity){
							codesmell.put("classificationType", "Medium");
							medium++;
						}else if(accSeverityLevel <= thresholdSeverity && accActivityLevel > thresholdActivity){
							codesmell.put("classificationType", "High");
							high++;
						}else{
							codesmell.put("classificationType", "Low");
							low++;
						}
						
//						/**
//						 * scale to 0 - 10
//						 */
//						accSeverityLevel = 10 * accSeverityLevel/ (3 * version.size());
//						accActivityLevel = 10 * accActivityLevel/ (3 * version.size());
//						codesmell.put("accSeverityLevel", accSeverityLevel);
//						codesmell.put("accActivityLevel", accActivityLevel);
						
						if (codesmells.size() != 0) {
							int index = 0;
							for(index = 0 ; index < codesmells.size(); index++){
								if((Double)codesmell.get("harmfulnessLevel") > (Double)codesmells.get(index).get("harmfulnessLevel")){
									break;
								}
							}
							codesmells.add(index, codesmell);
						} else {
							codesmells.add(codesmell);
						}
					}
				}
				temp.put("codesmells", codesmells);
				temp.put("numOfProblem", listcs.size());
				temp.put("numOfVeryHigh", veryhigh);
				temp.put("numOfLow", low);
				temp.put("numOfHigh", high);
				temp.put("numOfMedium", medium);
			}
		}
		
		return temp;
	}

	/**
	 * This function is used for line graph (for code smell evolution)
	 */
	@Override
	public Map<String, Object> getCodeSmellEvolution(String id, String className, String codesmellType) {
		// TODO Auto-generated method stub
		Document query = new Document().append("_id", id);
		
		Map<String, Object> temp = new HashMap<String, Object>();
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			List<Map<String,Object>> levolve = new ArrayList<Map<String, Object>>();
			
			for(int i = versions.size()-1 ; i >=0 ;i--){
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) versions.get(i).get("codesmells");
				Map<String, Object> evolve = new HashMap<String, Object>();
				
				for(Document cs : listcs){
					if(cs.getString("className").equals(className) && cs.getString("codesmellType").equals(codesmellType)){
						evolve.put("className", cs.getString("className"));
						evolve.put("severityImpact", cs.get("severityImpact"));
						evolve.put("harmfulnessLevel", cs.get("harmfulnessLevel"));
						evolve.put("activityDegree", cs.get("activityDegree"));
						evolve.put("severityLevel", cs.get("severityLevel"));
						evolve.put("totalChanges", ((Integer)cs.get("numOfInsert") + (Integer)cs.get("numOfDelete")));
						break;
					}
				}
				
				if(evolve.get("className") == null || evolve.get("className").equals("")){
					evolve.put("className", className);
					evolve.put("severityImpact", 0);
					evolve.put("harmfulnessLevel", 0);
					evolve.put("activityDegree", 0);
					evolve.put("severityLevel", 0);
					evolve.put("totalChanges", 0);
				}
				
				evolve.put("commitDate", versions.get(i).get("commitDate"));
				
				levolve.add(evolve);
			}
			temp.put("evolution", levolve);
		}
		return temp;
	}

	/**
	 * this function is to check whether the class is exist or not
	 */
	@Override
	public Boolean isClassExist(String id, String classname) {
		// TODO Auto-generated method stub
		Document query = new Document().append("_id", id);
		
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			
			for(int i = 0; i < versions.size(); i++){
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) versions.get(i).get("codesmells");
				
				for(Document cs : listcs){
					if(cs.getString("className").equals(classname)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * this function is used to check whether the version is exist or not
	 */
	@Override
	public Boolean isVersionExists(String id, int requestedVersion) {
		// TODO Auto-generated method stub
		Document query = new Document().append("_id", id);
		
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			if(requestedVersion > 0 && requestedVersion <= versions.size()) return true;
		}
		return false;
	}

	/**
	 * This function is used by DSL, it aims to get the information based on filters (by name, code smell type and change type
	 */
	@Override
	public Map<String, Object> getAllInformationWithFilter(String id, List<String> classname, List<String> codesmell,
			List<String> changetype, int version) {
		// TODO Auto-generated method stub
		ClassNameRule classrule = new ClassNameRule();
		CodeSmellRule codesmellrule = new CodeSmellRule();
		ChangeRule changerule = new ChangeRule();
		
		Map<String, Object> generaldata = new HashMap<String, Object>();
		Document query = new Document().append("_id", id);
		MongoCursor<Document> curr = collection.find(query).iterator();
		List<Map<String, Object>> versions = new ArrayList<Map<String, Object>>();
		
		generaldata.put("id", id);
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> lsversion = (List<Document>) doc.get("versions");
			
			/**
			 * find all version
			 */
			if(version == 0){
				
				for(int i = lsversion.size() -1 ; i >= 0 ; i--){
					Map<String, Object> tempversion = new HashMap<String, Object>();
					tempversion.put("commitdate", lsversion.get(i).get("commitDate"));
					
					@SuppressWarnings("unchecked")
					List<Document> listcs = (List<Document>) lsversion.get(i).get("codesmells");
					List<Map<String, Object>> codesmells = new ArrayList<Map<String, Object>>();
					
					for(int j = 0 ; j < listcs.size(); j++){
						String tempCodesmell = listcs.get(j).getString("codesmellType");
						String tempClassname = listcs.get(j).getString("className");
						String tempChangetype = listcs.get(j).getString("changeType");
						
						if(codesmellrule.isContentSatisfyTheElementContent(codesmell, tempCodesmell) 
								&& classrule.isContentSatisfyTheElementContent(classname, tempClassname) 
								&& changerule.isContentSatisfyTheElementContent(changetype, tempChangetype)){
							Map<String, Object> tempcodesmell = new HashMap<String, Object>();
							tempcodesmell.put("className", tempClassname);
							tempcodesmell.put("codesmellType", tempCodesmell);
							tempcodesmell.put("severityImpact", listcs.get(j).get("severityImpact"));
							tempcodesmell.put("severitylevel", listcs.get(j).get("severityLevel"));
							tempcodesmell.put("harmfulnessLevel", listcs.get(j).get("harmfulnessLevel"));
							tempcodesmell.put("changeType", listcs.get(j).get("changeType"));
							tempcodesmell.put("activityDegree", listcs.get(j).get("activityDegree"));
							tempcodesmell.put("numberOfChange", (Integer)listcs.get(j).get("numOfInsert") + (Integer)listcs.get(j).get("numOfInsert"));
							codesmells.add(tempcodesmell);
						}
					}
					tempversion.put("codesmells", codesmells);
					versions.add(tempversion);
				}
			} else{
				Map<String, Object> tempversion = new HashMap<String, Object>();
				int index = lsversion.size() - version;
				tempversion.put("commitdate", lsversion.get(index).get("commitDate"));
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) lsversion.get(index).get("codesmells");
				
				List<Map<String, Object>> codesmells = new ArrayList<Map<String, Object>>();
				
				for(int j = 0 ; j < listcs.size(); j++){
					String tempCodesmell = listcs.get(j).getString("codesmellType");
					String tempClassname = listcs.get(j).getString("className");
					String tempChangetype = listcs.get(j).getString("changeType");
					
					if(codesmellrule.isContentSatisfyTheElementContent(codesmell, tempCodesmell) 
							&& classrule.isContentSatisfyTheElementContent(classname, tempClassname) 
							&& changerule.isContentSatisfyTheElementContent(changetype, tempChangetype)){
						Map<String, Object> tempcodesmell = new HashMap<String, Object>();
						tempcodesmell.put("className", tempClassname);
						tempcodesmell.put("codesmellType", tempCodesmell);
						tempcodesmell.put("severityImpact", listcs.get(j).get("severityImpact"));
						tempcodesmell.put("severitylevel", listcs.get(j).get("severityLevel"));
						tempcodesmell.put("harmfulnessLevel", listcs.get(j).get("harmfulnessLevel"));
						tempcodesmell.put("changeType", listcs.get(j).get("changeType"));
						tempcodesmell.put("activityDegree", listcs.get(j).get("activityDegree"));
						tempcodesmell.put("numberOfChange", (Integer)listcs.get(j).get("numOfInsert") + (Integer)listcs.get(j).get("numOfInsert"));
						codesmells.add(tempcodesmell);
					}
				}
				tempversion.put("codesmells", codesmells);
				versions.add(tempversion);
			}
		}
		generaldata.put("versions", versions);
		return generaldata;
	}

	/**
	 * This function is used to get all class names
	 */
	@Override
	public List<String> getAllClassName(String id) {
		// TODO Auto-generated method stub
		List<String> classnames = new ArrayList<String>();
		Document query = new Document().append("_id", id);
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			
			for(int i = 0; i < versions.size(); i++){
				@SuppressWarnings("unchecked")
				List<Document> listcs = (ArrayList<Document>) versions.get(i).get("codesmells");
				
				for(int j = 0 ; j < listcs.size(); j++){
					if(classnames.size() == 0){
						classnames.add(listcs.get(j).getString("className"));
					}else{
						for(int k = 0 ; k < classnames.size(); k++){
							if(!classnames.get(k).equals(listcs.get(j).getString("className"))){
								classnames.add(listcs.get(j).getString("className"));
								break;
							}
						}
					}
				}
			}
		}
		System.out.println(classnames);
		return classnames;
	}

	/**
	 * This function is used by Old DSL Linegraph
	 * it is used to get all information based on the filters (classname, codesmell, changetype)
	 */
	@Override
	public Map<String, Object> getCodeSmellEvolutionByFilter(String id, String className, List<String> codesmell,
			List<String> changetype) {
		// TODO Auto-generated method stub
		Document query = new Document().append("_id", id);
		CodeSmellRule codesmellrule = new CodeSmellRule();
		ChangeRule changerule = new ChangeRule();
		
		Map<String, Object> temp = new HashMap<String, Object>();
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			List<Map<String,Object>> levolve = new ArrayList<Map<String, Object>>();
			
			for(int i = versions.size()-1 ; i >=0 ;i--){
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) versions.get(i).get("codesmells");
				Map<String, Object> evolve = new HashMap<String, Object>();
				
				for(Document cs : listcs){
					String tempCodesmell = cs.getString("codesmellType");
					String tempChangetype = cs.getString("changeType");
					
					if(cs.getString("className").equals(className)
							&& codesmellrule.isContentSatisfyTheElementContent(codesmell, tempCodesmell)
							&& changerule.isContentSatisfyTheElementContent(changetype, tempChangetype)){
						evolve.put("className", cs.getString("className"));
						evolve.put("severityImpact", cs.get("severityImpact"));
						evolve.put("harmfulnessLevel", cs.get("harmfulnessLevel"));
						evolve.put("activityDegree", cs.get("activityDegree"));
						evolve.put("severityLevel", cs.get("severityLevel"));
						evolve.put("totalChanges", ((Integer)cs.get("numOfInsert") + (Integer)cs.get("numOfDelete")));
					}
				}
				
				if(evolve.get("className") == null || evolve.get("className").equals("")){
					evolve.put("className", className);
					evolve.put("severityImpact", 0);
					evolve.put("harmfulnessLevel", 0);
					evolve.put("activityDegree", 0);
					evolve.put("severityLevel", 0);
					evolve.put("totalChanges",0);
				}
				
				evolve.put("commitDate", versions.get(i).get("commitDate"));
				
				levolve.add(evolve);
			}
			temp.put("evolution", levolve);
		}
		return temp;
	}

	/**
	 * This function is used by New DSL
	 * It aims to get all information based on the filters (classname, codesmell and change type)
	 */
	@Override
	public Map<String, Object> getAllInformationWithFilter2(String id, List<String> classname, List<String> codesmell,
			List<String> changetype, List<Integer> version) {
		// TODO Auto-generated method stub
		ClassNameRule classrule = new ClassNameRule();
		CodeSmellRule codesmellrule = new CodeSmellRule();
		ChangeRule changerule = new ChangeRule();
		
		Map<String, Object> generaldata = new HashMap<String, Object>();
		Document query = new Document().append("_id", id);
		MongoCursor<Document> curr = collection.find(query).iterator();
		List<Map<String, Object>> versions = new ArrayList<Map<String, Object>>();
		
		generaldata.put("id", id);
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> lsversion = (List<Document>) doc.get("versions");
			
			/**
			 * find all version
			 */
			if(version.size() == 0){
				
				for(int i = lsversion.size() -1 ; i >= 0 ; i--){
					Map<String, Object> tempversion = new HashMap<String, Object>();
					tempversion.put("commitdate", lsversion.get(i).get("commitDate"));
					
					@SuppressWarnings("unchecked")
					List<Document> listcs = (List<Document>) lsversion.get(i).get("codesmells");
					List<Map<String, Object>> codesmells = new ArrayList<Map<String, Object>>();
					
					for(int j = 0 ; j < listcs.size(); j++){
						String tempCodesmell = listcs.get(j).getString("codesmellType");
						String tempClassname = listcs.get(j).getString("className");
						String tempChangetype = listcs.get(j).getString("changeType");
						
						if(codesmellrule.isContentSatisfyTheElementContent(codesmell, tempCodesmell) 
								&& classrule.isContentSatisfyTheElementContent(classname, tempClassname) 
								&& changerule.isContentSatisfyTheElementContent(changetype, tempChangetype)){
							Map<String, Object> tempcodesmell = new HashMap<String, Object>();
							tempcodesmell.put("className", tempClassname);
							tempcodesmell.put("codesmellType", tempCodesmell);
							tempcodesmell.put("severityImpact", listcs.get(j).get("severityImpact"));
							tempcodesmell.put("severityLevel", listcs.get(j).get("severityLevel"));
							tempcodesmell.put("harmfulnessLevel", listcs.get(j).get("harmfulnessLevel"));
							tempcodesmell.put("changeType", listcs.get(j).get("changeType"));
							tempcodesmell.put("activityDegree", listcs.get(j).get("activityDegree"));
							tempcodesmell.put("numberOfChange", (Integer)listcs.get(j).get("numOfInsert") + (Integer)listcs.get(j).get("numOfInsert"));
							codesmells.add(tempcodesmell);
						}
					}
					tempversion.put("codesmells", codesmells);
					versions.add(tempversion);
				}
			} else{
				Collections.sort(version);
				for(int i = version.size() -1 ; i >= 0 ; i--){
					Map<String, Object> tempversion = new HashMap<String, Object>();
					tempversion.put("commitdate", lsversion.get(version.get(i) -1).get("commitDate"));
					
					@SuppressWarnings("unchecked")
					List<Document> listcs = (List<Document>) lsversion.get(version.get(i) -1).get("codesmells");
					List<Map<String, Object>> codesmells = new ArrayList<Map<String, Object>>();
					
					for(int j = 0 ; j < listcs.size(); j++){
						String tempCodesmell = listcs.get(j).getString("codesmellType");
						String tempClassname = listcs.get(j).getString("className");
						String tempChangetype = listcs.get(j).getString("changeType");
						
						if(codesmellrule.isContentSatisfyTheElementContent(codesmell, tempCodesmell) 
								&& classrule.isContentSatisfyTheElementContent(classname, tempClassname) 
								&& changerule.isContentSatisfyTheElementContent(changetype, tempChangetype)){
							Map<String, Object> tempcodesmell = new HashMap<String, Object>();
							tempcodesmell.put("className", tempClassname);
							tempcodesmell.put("codesmellType", tempCodesmell);
							tempcodesmell.put("severityImpact", listcs.get(j).get("severityImpact"));
							tempcodesmell.put("severityLevel", listcs.get(j).get("severityLevel"));
							tempcodesmell.put("harmfulnessLevel", listcs.get(j).get("harmfulnessLevel"));
							tempcodesmell.put("changeType", listcs.get(j).get("changeType"));
							tempcodesmell.put("activityDegree", listcs.get(j).get("activityDegree"));
							tempcodesmell.put("numberOfChange", (Integer)listcs.get(j).get("numOfInsert") + (Integer)listcs.get(j).get("numOfInsert"));
							codesmells.add(tempcodesmell);
						}
					}
					tempversion.put("codesmells", codesmells);
					versions.add(tempversion);
				}
			}
		}
		generaldata.put("versions", versions);
		return generaldata;
	}

	@Override
	public List<DetectedCodesmellAllVersions> getAllVersionCodeSmells(String id) {
		// TODO Auto-generated method stub
		List<DetectedCodesmellAllVersions> list = new ArrayList<DetectedCodesmellAllVersions>();
		Document query = new Document().append("_id", id);
		MongoCursor<Document> curr = collection.find(query).iterator();
		
		if(curr.hasNext()){
			Document doc = curr.next();
			@SuppressWarnings("unchecked")
			List<Document> versions = (List<Document>) doc.get("versions");
			for(int i = versions.size()-1 ; i >=0 ;i--){
				@SuppressWarnings("unchecked")
				List<Document> listcs = (List<Document>) versions.get(i).get("codesmells");
				for(Document cs : listcs){
					int version = versions.size() - i;
					String className = cs.getString("className");
					String codesmellType = cs.getString("codesmellType");
					String programmingLanguage = cs.getString("programmingLanguage");
					String activityType = cs.getString("changeType");
					float impact = cs.getDouble("severityLevel").floatValue();
					float activeness = cs.getDouble("activityDegree").floatValue();
					float ACT = cs.getDouble("accActivityLevel").floatValue();
					float IMP = cs.getDouble("accSeverityLevel").floatValue();
					float harmfulness = cs.getDouble("harmfulnessLevel").floatValue();
					String harmfulnessLevel = "";
					
					float halfPossibleACT = (MAX_ACT * version) * 50 /100;
					float halfPossibleIMP = (MAX_IMP * version) * 50 /100;
					
					if(IMP > halfPossibleIMP && ACT > halfPossibleACT){
						harmfulnessLevel = "Very High";
					}else if(IMP > halfPossibleIMP && ACT <= halfPossibleACT){
						harmfulnessLevel = "Medium";
					}else if(IMP <= halfPossibleIMP && ACT > halfPossibleACT){
						harmfulnessLevel = "High";
					}else{
						harmfulnessLevel = "Low";
					}
					list.add(new DetectedCodesmellAllVersions(className, codesmellType, programmingLanguage, activityType, impact, activeness, ACT, IMP, harmfulnessLevel, harmfulness, version));
				}
			}
		}
		return list;
	}
	
}
