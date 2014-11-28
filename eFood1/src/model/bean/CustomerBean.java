package model.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="customer")
public class CustomerBean {
	@XmlElement
	String name;
	@XmlAttribute
	String account;
	public CustomerBean(){
		
	}
	public CustomerBean(String name, String account){
		this.name = name;
		this.account = account;
	}
	
}
