package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.ChangeRule;

public class Change extends Element{
	
	public Change() {
		// TODO Auto-generated constructor stub
		this.rules = new ChangeRule();
		this.key = "change";
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
