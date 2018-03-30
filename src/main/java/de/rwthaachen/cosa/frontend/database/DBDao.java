package de.rwthaachen.cosa.frontend.database;

import java.util.List;
import java.util.Map;

import de.rwthaachen.cosa.frontend.excel.DetectedCodesmellAllVersions;

public interface DBDao {
	public List<Map<String, String>> getIdAndVcsroot();
	public Map<String, Object> getVCSAnalysisDetail(String id, String codesmelltype, String activitytype, int activityLow, int severityLow);
	public Map<String, Object> getCodeSmellEvolution(String id, String className, String codesmellType);
	public Map<String, Object> getCodeSmellEvolutionByFilter(String id, String className, List<String> codesmell, List<String> changetype);
	public Boolean isClassExist(String id, String classname);
	public Boolean isVersionExists(String id, int requestedVersion);
	public Map<String, Object> getAllInformationWithFilter(String id, List<String> classname, List<String> codesmell, List<String> changetype, int version);
	public Map<String, Object> getAllInformationWithFilter2(String id, List<String> classname, List<String> codesmell, List<String> changetype, List<Integer> version);
	public List<String> getAllClassName(String id);
	public List<DetectedCodesmellAllVersions> getAllVersionCodeSmells(String id);
}
