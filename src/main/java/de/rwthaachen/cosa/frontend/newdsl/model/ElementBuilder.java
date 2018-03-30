package de.rwthaachen.cosa.frontend.newdsl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementBuilder {
	private Element element;
	private String input;
	
	public ElementBuilder(String input) {
		// TODO Auto-generated constructor stub
		this.input = input;
	}
	
	/**
	 * create element
	 * @return
	 */
	public Map<String,Element> getElement(){
		Map<String, Element> returnData = new HashMap<String, Element>();
		
		if(input.toLowerCase().startsWith("make=")){
			this.element = new Make();
			setSingleContent();
		}else if(input.toLowerCase().startsWith("fromlocalrepository=")){
			this.element = new FromLocalRepository();
			setSingleContent();
		}else if(input.toLowerCase().startsWith("-classname=")){
			this.element = new ClassName();
			setArrayContent();
		}else if(input.toLowerCase().startsWith("-version=")){
			this.element = new Version();
			setArrayContent();
//			setSingleContent();
		}else if(input.toLowerCase().startsWith("-codesmell=")){
			this.element = new CodeSmell();
			setArrayContent();
		}else if(input.toLowerCase().startsWith("-change=")){
			this.element = new Change();
			setArrayContent();
		}else if(input.toLowerCase().startsWith("axis=")){
			this.element = new Axis();
			setSingleContent();
		}
		returnData.put(element.getKey(), element);
		return returnData;
	}
	
	/**
	 * validate each input
	 * @return
	 */
	public List<String> getErrorMessage(){
		return this.element.getRules().validation(input);
	}
	
	private void setSingleContent(){
		String[] param = input.split("=");
		if(param.length == 2){
			if(param[1] != null && !param[1].equals(""))
				this.element.setContent(param[1].trim());
		}
	}
	
	private void setArrayContent(){
		String[] param = input.split("=");
		if(param.length == 2){
			if(param[1] != null && !param[1].equals("")){
				String[] allparam = param[1].split(",");
				if(allparam.length == 1)
					this.element.setContent(param[1].trim());
				else{
					List<String> data = new ArrayList<String>();
					for(int i = 0 ; i < allparam.length ; i++){
						data.add(allparam[i].trim());
					}
					this.element.setContent(data);
				}
			}	
		}
	}
}
