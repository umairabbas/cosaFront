package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.DSLRule;

public abstract class Element {
	protected Object content;
	protected DSLRule rules;

	public void setContent(Object content){
		this.content = content;
	}
	
	public abstract Object getContent();

	public DSLRule getRules() {
		return rules;
	}

	public void setRules(DSLRule rules) {
		this.rules = rules;
	}
}
