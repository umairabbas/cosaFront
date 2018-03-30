package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.ArrayList;
import java.util.List;

public class ChangeRule implements DSLRule{
	/**
	 * check change type format
	 * @param change
	 * @return
	 */
	public Boolean isElementInTheCorrectFormat(String comparison) {
		// TODO Auto-generated method stub
		if(comparison.equalsIgnoreCase("")
				||comparison.equalsIgnoreCase("All")
				||comparison.equalsIgnoreCase("Strong Active")
				||comparison.equalsIgnoreCase("Stable Active")
				||comparison.equalsIgnoreCase("Ameliorate Active")
				||comparison.equalsIgnoreCase("Dormant")) return true;
		return false;
	}
	
	/**
	 * check change type available in the database
	 * @param changetype
	 * @param changetypetmp
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
		if(tempinput.length >= 2){
			if(tempinput[1] != null){
				//tempinput[1].replaceAll(" ", "");
				String[] temp = tempinput[1].split(",");
				
				if(temp.length == 1){
					if(!isElementInTheCorrectFormat(temp[0].trim()))
						errorMessage.add("Please input the correct format for change!");
				}else{
					for(String t : temp){
						if(!isElementInTheCorrectFormat(t.trim()))
							errorMessage.add("This change \'" +t+ "\' is in the wrong format!");
					}
				}
			}else{
				errorMessage.add("Please check your change parameter!");
			}
		}else{
			errorMessage.add("Change= paremeter cannot be empty!");
		}
		return errorMessage;
	}

	
}
