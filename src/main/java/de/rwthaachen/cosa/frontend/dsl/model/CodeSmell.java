package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.CodeSmellRule;

public class CodeSmell extends Element{
	public CodeSmell() {
		// TODO Auto-generated constructor stub
		this.rules = new CodeSmellRule();
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
