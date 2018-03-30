package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.ChangeRule;

public class Change extends Element{
	
	public Change() {
		// TODO Auto-generated constructor stub
		this.rules = new ChangeRule();
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
