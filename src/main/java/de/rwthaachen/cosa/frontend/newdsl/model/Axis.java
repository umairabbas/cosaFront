package de.rwthaachen.cosa.frontend.newdsl.model;

import de.rwthaachen.cosa.frontend.newdsl.rules.AxisRule;

public class Axis extends Element{
	public Axis() {
		// TODO Auto-generated constructor stub
		this.rules = new AxisRule();
		this.key = "axis";
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content.toString();
	}
}
