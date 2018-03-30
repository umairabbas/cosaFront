package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.ArrayList;
import java.util.List;

public class MakeRule implements DSLRule{
	public Boolean isDocumentAvailable(String temp) {
		// TODO Auto-generated method stub
		if ((temp.toLowerCase().equals("table")) 
				|| (temp.toLowerCase().equals("linegraph"))) return true;
		return false;
	}

	@Override
	public List<String> validation(String input) {
		// TODO Auto-generated method stub
		List<String> errorMessage = new ArrayList<String>();
		
		String[] tempinput = input.split("=");
		if(tempinput.length >= 2){
			if(tempinput[1] != null){
				if(!isDocumentAvailable(tempinput[1].trim()))
					errorMessage.add("Please input the correct format for make!");
			}else{
				errorMessage.add("Please check your make parameter!");
			}
		}else{
			errorMessage.add("Make parameter cannot be empty!");
		}
		return errorMessage;
	}

}
