package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.VersionRule;

public class Version extends Element{
	public Version() {
		// TODO Auto-generated constructor stub
		this.rules = new VersionRule();
		this.key = "version";
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}
}
