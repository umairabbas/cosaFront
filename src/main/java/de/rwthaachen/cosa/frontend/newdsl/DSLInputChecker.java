package de.rwthaachen.cosa.frontend.newdsl;

import java.util.ArrayList;
import java.util.List;

public class DSLInputChecker {
	private List<String> errorMessage;
	
	public DSLInputChecker(String[] input) {
		// TODO Auto-generated constructor stub
		errorMessage = new ArrayList<String>();
		
		if(input.length != 0){
			String elementtomake = "";
			boolean checkaxis = false;
			boolean detailexists = false;
			int forindex = 0;
			
			for(int i = 0 ; i < input.length; i++){
				if(i == 0){
					//check first element must be make:
					if(!input[i].toLowerCase().startsWith("make=")){
						errorMessage.add("make= must be the first element!");
					}else{
						String[] tempmake = input[i].split("=");
						if(tempmake.length == 2){
							elementtomake = tempmake[1].trim();
						}else{
							errorMessage.add("make= is in invalid format!");
						}
					}
				}else if(i == 1){
					//check second element must be fromlocalrepository:
					if(!input[i].toLowerCase().startsWith("fromlocalrepository=")){
						errorMessage.add("fromlocalrepository= must be the second element!");
					}
				}else{
					//check axis existence if the elementtomake is linegraph
					if(elementtomake.toLowerCase().equals("linegraph")){
						if(input[i].toLowerCase().startsWith("axis=")){
							checkaxis = true;
							break;
						}
					}
					
					//check forindex index
					if(input[i].toLowerCase().startsWith("for=")){
						forindex = i;
					}
					
					if(input[i].toLowerCase().startsWith("-")){
						detailexists = true;
						if(i < forindex){
							errorMessage.add("This statement \'" + input[i]+ "\' cannot be executed!");
						}
					}
				}
			}
			
			if(elementtomake.toLowerCase().equals("linegraph")){
				if(checkaxis == false){
					errorMessage.add("axis= element is needed for linegraph!");
				}
			}
			
			if(detailexists){
				if(forindex == 0){
					errorMessage.add("You have to use for= in order to use detail analysis!");
				}
			}
			
		}else{
			errorMessage.add("Your DSL is empty!");
		}
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}
}
