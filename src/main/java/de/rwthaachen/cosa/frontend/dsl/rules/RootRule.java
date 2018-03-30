package de.rwthaachen.cosa.frontend.dsl.rules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RootRule implements DSLRule{
	@Override
	public Boolean isDocumentAvailable(Document doc) {
		// TODO Auto-generated method stub
		if ((doc.getElementsByTagName("table").getLength() != 0) 
				|| (doc.getElementsByTagName("linegraph").getLength() != 0)) return true;
		return false;
	}

	@Override
	public Boolean isElementAvailable(Element doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp) {
		// TODO Auto-generated method stub
		return null;
	}

}
