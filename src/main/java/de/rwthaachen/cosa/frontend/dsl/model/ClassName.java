package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.ClassNameRule;

public class ClassName extends Element{
	public ClassName() {
		// TODO Auto-generated constructor stub
		this.rules = new ClassNameRule();
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
