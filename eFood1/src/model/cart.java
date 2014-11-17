package model;

import java.util.ArrayList;
import java.util.List;

public class cart {
	List<items> c;
	
	public cart(){
		this.c = new ArrayList<items>();
	}
	public void addtoCart(items i){
		this.c.add(i);
	}
	public void removefromCart(items i){
		this.c.remove(i);
	}
	
}
