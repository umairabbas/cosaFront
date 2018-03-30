package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.FromLocalRepositoryRule;

public class FromLocalRepository extends Element{
	public FromLocalRepository() {
		// TODO Auto-generated constructor stub
		this.rules = new FromLocalRepositoryRule();
		this.key = "fromlocalrepository";
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
