package de.rwthaachen.cosa.frontend.dsl.rules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeSmellRule implements DSLRule{

	@Override
	public Boolean isDocumentAvailable(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isElementAvailable(Element element) {
		// TODO Auto-generated method stub
		if (element.getElementsByTagName("codesmell").getLength() != 0) return true;
		return false;
	}
	
	/**
	 * check code smell format
	 * @param codesmell
	 * @return
	 */
	@Override
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("All")
				||comparison.equalsIgnoreCase("God Class")
				||comparison.equalsIgnoreCase("Brain Class")
				||comparison.equalsIgnoreCase("Data Class"))return true;
		return false;
	}
	
	/**
	 * check code smell availability in the database
	 * @param codesmell
	 * @param codesmelltmp
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
