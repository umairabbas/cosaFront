package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.ClassNameRule;

public class ClassName extends Element{
	public ClassName() {
		// TODO Auto-generated constructor stub
		this.rules = new ClassNameRule();
		this.key = "classname";
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
