package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.RootRule;

public class Root extends Element{
	public Root() {
		// TODO Auto-generated constructor stub
		this.rules = new RootRule();
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
