package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.MakeRule;

public class Make extends Element{
	public Make() {
		// TODO Auto-generated constructor stub
		this.rules = new  MakeRule();
		this.key = "make";
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

}
