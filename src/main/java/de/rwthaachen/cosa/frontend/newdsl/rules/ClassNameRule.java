package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.List;

public class ClassNameRule implements DSLRule{
	
	/**
	 * 
	 * @param classname
	 * @param classnametmp
	 * @return
	 */
	public Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp) {
		// TODO Auto-generated method stub
		for(String v : val){
			if(v.equalsIgnoreCase("all")) return true;
			else if(v.equalsIgnoreCase(valcmp)) return true;
		}
		return false;
	}

	@Override
	public List<String> validation(String input) {
		// TODO Auto-generated method stub
		return null;
	}	

}
