package de.rwthaachen.cosa.frontend.dsl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import de.rwthaachen.cosa.frontend.dsl.model.Axis;
import de.rwthaachen.cosa.frontend.dsl.model.Change;
import de.rwthaachen.cosa.frontend.dsl.model.ClassName;
import de.rwthaachen.cosa.frontend.dsl.model.CodeSmell;
import de.rwthaachen.cosa.frontend.dsl.model.Element;
import de.rwthaachen.cosa.frontend.dsl.model.Root;
import de.rwthaachen.cosa.frontend.dsl.model.VcsRoot;
import de.rwthaachen.cosa.frontend.dsl.model.Version;

public class DSLParser {
	private Map<String, Element> xmlparserresult;
	private Map<String, Object> xmlcontent;
	private List<String> errorMessage;
	
	public DSLParser(String xml) {
		// TODO Auto-generated constructor stub
		this.xmlparserresult = new HashMap<String, Element>();
		this.xmlcontent = new HashMap<String, Object>();
		this.errorMessage = new ArrayList<String>();
		
		// parse the xml
		try{
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document doc = dBuilder.parse(is);
		
		//set root element
		Root root = new Root();
		if(root.getRules().isDocumentAvailable(doc)){
			if (doc.getElementsByTagName("table").getLength() != 0) {
				root.setContent("table");
			} else if (doc.getElementsByTagName("linegraph").getLength() != 0) {
				root.setContent("linegraph");
			}
			this.xmlparserresult.put("rootelement", root);
			
			NodeList nList = doc.getElementsByTagName(root.getContent().toString());

			// parse xml content (get the first item)
			Node node = nList.item(0);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				org.w3c.dom.Element element = (org.w3c.dom.Element) node;
				/**
				 * vcsroot
				 */
				VcsRoot vcsroot = new VcsRoot();
				if(vcsroot.getRules().isElementAvailable(element)){
					vcsroot.setContent(element.getElementsByTagName("vcsroot").item(0)
							.getTextContent());
					this.xmlparserresult.put("vcsroot", vcsroot);
					
					setElementAndItsContentArray(new ClassName(), "classname", element);
					setElementAndItsContentArray(new CodeSmell(), "codesmell", element);
					setElementAndItsContentArray(new Change(), "change", element);
					setElementAndItsContent(new Axis(), "axis", element);
					setElementAndItsContent(new Version(), "version", element);
					
					/**
					 * check format
					 */
					checkFormat("codesmell",this.xmlparserresult.get("codesmell"));
					checkFormat("change", this.xmlparserresult.get("change"));
					checkFormat("axis", this.xmlparserresult.get("axis"));
					
					/**
					 * check axis is mandatory if the rootelement is linegraph
					 */
					if(root.getContent().toString().equals("linegraph")){
						if(this.xmlparserresult.get("axis").getContent().toString().equals("")){
							this.errorMessage.add("Axis is mandatory for linegraph!");
						}
					}
					
					setXMLcontent();
				}else{
					this.errorMessage.add("VCS Root is mandatory!");
				}
			}
		}else{
			this.errorMessage.add("Root Element must be either Table or Linegraph!");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setElementAndItsContent(Element tag, String tagtype,org.w3c.dom.Element element){
		if(tag.getRules().isElementAvailable(element)){
			tag.setContent(element.getElementsByTagName(tagtype).item(0)
					.getTextContent());
		}else{
			if(tagtype.equals("version"))
				tag.setContent("0");
			else
				tag.setContent("");
		}
		this.xmlparserresult.put(tagtype, tag);
	}
	
	public void setElementAndItsContentArray(Element tag, String tagtype,org.w3c.dom.Element element){
		List<String> lists = new ArrayList<String>();
		if(tag.getRules().isElementAvailable(element)){
			for(int i = 0 ; i < element.getElementsByTagName(tagtype).getLength(); i++){
				lists.add(element.getElementsByTagName(tagtype).item(i).getTextContent());
			}
		}else{
			lists.add("all");
		}
		tag.setContent(lists);
		this.xmlparserresult.put(tagtype, tag);
	}
	
	public void checkFormat(String tagtype, Element element){
		if(tagtype.equals("axis")){
			if(!element.getRules().isElementInTheCorrectFormat(element.getContent().toString())){
				element.setContent("wrongformat");
				this.errorMessage.add("Your axis format is wrong!");
			}
		}else{
			@SuppressWarnings("unchecked")
			List<String> contents = (ArrayList<String>) element.getContent();
			
			for(String content : contents){
				if(!element.getRules().isElementInTheCorrectFormat(content)){
					contents.set(contents.indexOf(content), "wrongformat");
					this.errorMessage.add(content + " is an invalid format!");
				}
			}
			element.setContent(contents);
		}
		this.xmlparserresult.replace(tagtype, element);
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public Map<String, Object> getXmlcontent() {
		return xmlcontent;
	}
	
	public void setXMLcontent(){
		this.xmlcontent.put("rootelement", this.xmlparserresult.get("rootelement").getContent().toString());
		this.xmlcontent.put("vcsroot", this.xmlparserresult.get("vcsroot").getContent().toString());
		this.xmlcontent.put("classname", this.xmlparserresult.get("classname").getContent());
		this.xmlcontent.put("codesmell", this.xmlparserresult.get("codesmell").getContent());
		this.xmlcontent.put("change", this.xmlparserresult.get("change").getContent());
		this.xmlcontent.put("axis", this.xmlparserresult.get("axis").getContent());
		this.xmlcontent.put("version", this.xmlparserresult.get("version").getContent());
	}
}
