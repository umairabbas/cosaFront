package de.rwthaachen.cosa.frontend.logic;

import java.util.List;
import java.util.Map;

import de.rwthaachen.cosa.frontend.database.DBDao;

public class Logic {
	public static String getIdFromRoot(String vcsroot, DBDao dao){
		List<Map<String, String>> idAndRoot = dao.getIdAndVcsroot();
		
		for(Map<String, String> check : idAndRoot){
			if(check.get("vcsroot").equalsIgnoreCase(vcsroot))return check.get("id");
		}
		
		return "";
	}
	
	public static Boolean isItLinegraph(String xmlrootelement){
		if(xmlrootelement.equals("linegraph"))return true;
		return false;
	}
	
	public static Boolean isElementExecutable(String input){
		if(input.startsWith("make")
				|| input.startsWith("fromlocalrepository")
				|| input.startsWith("-classname")
				|| input.startsWith("-version")
				|| input.startsWith("-codesmell")
				|| input.startsWith("-change")
				|| input.startsWith("axis"))
			return true;
		return false;
	}
}

