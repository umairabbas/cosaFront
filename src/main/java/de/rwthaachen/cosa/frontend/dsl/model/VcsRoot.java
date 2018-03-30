package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.VcsRootRule;

public class VcsRoot extends Element{
	public VcsRoot() {
		// TODO Auto-generated constructor stub
		this.rules = new VcsRootRule();
	}
	
	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
