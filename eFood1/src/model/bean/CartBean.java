package model.bean;

import java.util.HashMap;
import java.util.Map;

public class CartBean {
	Map<ItemBean, Integer> c;
	
	public CartBean(){
		this.c = new HashMap<ItemBean, Integer>();
	}
	
	public void addToCart(ItemBean i, int quantity){
		this.c.put(i, quantity);
	}
	public void removefromCart(ItemBean i){
		this.c.remove(i);
	}
	public Map<ItemBean, Integer> getMap(){
		return c;
	}
	
}
