package de.rwthaachen.cosa.frontend.dsl.rules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ChangeRule implements DSLRule{

	@Override
	public Boolean isDocumentAvailable(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isElementAvailable(Element element) {
		// TODO Auto-generated method stub
		if (element.getElementsByTagName("change").getLength() != 0) return true;
		return false;
	}

	/**
	 * check change type format
	 * @param change
	 * @return
	 */
	@Override
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("All")
				||comparison.equalsIgnoreCase("Strong Active")
				||comparison.equalsIgnoreCase("Stable Active")
				||comparison.equalsIgnoreCase("Ameliorate Active")
				||comparison.equalsIgnoreCase("Dormant")) return true;
		return false;
	}
	
	/**
	 * check change type available in the database
	 * @param changetype
	 * @param changetypetmp
	 * @return
	 */
	@Override
	public Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp) {
		// TODO Auto-generated method stub
		for(String v : val){
			if(v.equalsIgnoreCase("all")) return true;
			else if(v.equalsIgnoreCase(valcmp)) return true;
		}
		return false;
	}

	
}
