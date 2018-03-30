package de.rwthaachen.cosa.frontend.dsl.rules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AxisRule implements DSLRule{

	@Override
	public Boolean isDocumentAvailable(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isElementAvailable(Element element) {
		// TODO Auto-generated method stub
		if (element.getElementsByTagName("axis").getLength() != 0) return true;
		return false;
	}

	/**
	 * check axis format
	 * @param axis
	 * @return
	 */
	@Override
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("Severity Impact")
				||comparison.equalsIgnoreCase("Severity Level")
				||comparison.equalsIgnoreCase("Harmfulness Level")
				||comparison.equalsIgnoreCase("Changes")) return true;
		return false;
	}

	@Override
	public Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp) {
		// TODO Auto-generated method stub
		return null;
	}
}
