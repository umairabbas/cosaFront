package de.rwthaachen.cosa.frontend.dsl.model;

import de.rwthaachen.cosa.frontend.dsl.rules.AxisRule;

public class Axis extends Element{
	public Axis() {
		// TODO Auto-generated constructor stub
		this.rules = new AxisRule();
	}

	@Override
	public
	Object getContent() {
		// TODO Auto-generated method stub
		return this.content.toString();
	}
}
