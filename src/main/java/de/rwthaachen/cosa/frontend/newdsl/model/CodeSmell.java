package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.CodeSmellRule;

public class CodeSmell extends Element{
	public CodeSmell() {
		// TODO Auto-generated constructor stub
		this.rules = new CodeSmellRule();
		this.key = "codesmell";
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
