package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.VersionRule;

public class Version extends Element{
	public Version() {
		// TODO Auto-generated constructor stub
		this.rules = new VersionRule();
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}
}
