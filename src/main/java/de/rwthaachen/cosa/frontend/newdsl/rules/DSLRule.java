package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.List;


public interface DSLRule {
	/**
	 * validate the request
	 * @param input
	 * @return
	 */
	abstract List<String> validation(String input);
}
