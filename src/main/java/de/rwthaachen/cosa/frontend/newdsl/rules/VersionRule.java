package de.rwthaachen.cosa.frontend.newdsl.rules;

import java.util.ArrayList;
import java.util.List;

public class VersionRule implements DSLRule{

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
					try{
						Integer.parseInt(temp[0].trim());
					}catch(Exception e){
						errorMessage.add("This version \'"+temp[0].trim()+"\' is in the wrong format!");
					}
				}else{
					for(String t : temp){
						try{
							Integer.parseInt(t.trim());
						}catch(Exception e){
							errorMessage.add("This version \'"+t.trim()+"\' is in the wrong format!");
						}
					}
				}
			}else{
				errorMessage.add("Please check your version parameter!");
			}
		}else{
			errorMessage.add("version= paremeter cannot be empty!");
		}
		return errorMessage;
	}

}
