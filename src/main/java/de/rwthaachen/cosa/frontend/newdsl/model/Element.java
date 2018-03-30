package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.DSLRule;

public abstract class Element {
	protected Object content;
	protected DSLRule rules;
	protected String key;

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
	
	public String getKey(){
		return this.key;
	}
}
