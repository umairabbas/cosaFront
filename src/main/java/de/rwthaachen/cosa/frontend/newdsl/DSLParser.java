package de.rwthaachen.cosa.frontend.newdsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rwthaachen.cosa.frontend.logic.Logic;
import de.rwthaachen.cosa.frontend.newdsl.model.*;

public class DSLParser {
	private Map<String, Object> content;
	private List<String> errorMessage;
	private List<Map<String, Element>> tempParserResult;
	
	public DSLParser(String[] input) {
		// TODO Auto-generated constructor stub
		this.content = new HashMap<String, Object>();
		this.errorMessage = new ArrayList<String>();
		this.tempParserResult = new ArrayList<Map<String, Element>>();
		
		//parse the input
		for(String tempinput : input){
			if(Logic.isElementExecutable(tempinput)){
				ElementBuilder eb = new ElementBuilder(tempinput);
				this.tempParserResult.add(eb.getElement());
				if(eb.getErrorMessage() != null && eb.getErrorMessage().size() != 0){
					this.errorMessage.addAll(eb.getErrorMessage());
					System.out.println(this.errorMessage);
				}
			}
		}
		
		if(errorMessage.size() == 0){
			setContent();
		}
	}
	
	public void setContent(){
		for(Map<String, Element> res : tempParserResult){
			String key = res.keySet().iterator().next();
			content.put(key, res.get(key).getContent());
		}
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public Map<String, Object> getContent() {
		return content;
	}
}
