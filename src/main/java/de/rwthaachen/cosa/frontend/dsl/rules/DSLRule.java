package de.rwthaachen.cosa.frontend.dsl.rules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface DSLRule {
	/**
	 * check if the document exists or not
	 * @param doc
	 * @return
	 */
	abstract Boolean isDocumentAvailable(Document doc);
	
	/**
	 * check if the element exists or not
	 * @param element
	 * @return
	 */
	abstract Boolean isElementAvailable(Element element);
	
	/**
	 * check whether it is in the correct format or not
	 * @param comparison
	 * @return
	 */
	abstract Boolean isElementInTheCorrectFormat(String comparison);
	
	abstract Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp);
}
