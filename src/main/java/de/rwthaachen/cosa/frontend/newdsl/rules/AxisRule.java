package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.ArrayList;
import java.util.List;

public class AxisRule implements DSLRule{
	/**
	 * check axis format
	 * @param axis
	 * @return
	 */
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("Severity Impact")
				||comparison.equalsIgnoreCase("Severity Level")
				||comparison.equalsIgnoreCase("Harmfulness Level")
				||comparison.equalsIgnoreCase("Changes")) return true;
		return false;
	}

	@Override
	public List<String> validation(String input) {
		// TODO Auto-generated method stub
		List<String> errorMessage = new ArrayList<String>();
		
		String[] tempinput = input.split("=");
		if(tempinput.length == 2){
			if(tempinput[1] != null){
				if(!isElementInTheCorrectFormat(tempinput[1].trim()))
					errorMessage.add("Please input the correct format for axis!");
			}else{
				errorMessage.add("Please check your axis parameter!");
			}
		}else{
			errorMessage.add("Axis parameter cannot be empty!");
		}
		return errorMessage;
	}
}
