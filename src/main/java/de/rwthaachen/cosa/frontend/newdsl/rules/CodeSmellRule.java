package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.ArrayList;
import java.util.List;

public class CodeSmellRule implements DSLRule{	
	/**
	 * check code smell format
	 * @param codesmell
	 * @return
	 */
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("All")
				||comparison.equalsIgnoreCase("God Class")
				||comparison.equalsIgnoreCase("Brain Class")
				||comparison.equalsIgnoreCase("Data Class"))return true;
		return false;
	}
	
	/**
	 * check code smell availability in the database
	 * @param codesmell
	 * @param codesmelltmp
	 * @return
	 */
	public Boolean isContentSatisfyTheElementContent(List<String> val, String valcmp) {
		// TODO Auto-generated method stub
		for(String v : val){
			if(v.equalsIgnoreCase("all")) return true;
			else if(v.equalsIgnoreCase(valcmp)) return true;
		}
		return false;
	}

	@Override
	public List<String> validation(String input) {
		// TODO Auto-generated method stub
		List<String> errorMessage = new ArrayList<String>();
		
		String[] tempinput = input.split("=");
		if(tempinput.length == 2){
			if(tempinput[1] != null){
				//tempinput[1].replaceAll(" ", "");
				String[] temp = tempinput[1].split(",");
				
				if(temp.length == 1){
					if(!isElementInTheCorrectFormat(temp[0].trim()))
						errorMessage.add("Please input the correct format for codesmell!");
				}else{
					for(String t : temp){
						if(!isElementInTheCorrectFormat(t.trim()))
							errorMessage.add("This codesmell \'" +t+ "\' is in the wrong format!");
					}
				}
			}else{
				errorMessage.add("Please check your codesmell parameter!");
			}
		}else{
			errorMessage.add("codesmell: paremeter cannot be empty!");
		}
		return errorMessage;
	}

}
