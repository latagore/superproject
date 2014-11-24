package model;

import java.sql.SQLException;
import java.util.Map;

import model.bean.CartBean;
import model.bean.ItemBean;

/**
 * Models the cart in the store
 *
 */
public class Cart {
	
	/**
	 * Validates the given item quantities in the cart
	 * @param itemQuantity the item quantities
	 * @throws InvalidInputException when a item 
	 */
	public Map<String, String> process(Map<String, String> itemQuantity) throws InvalidInputException {
		// TODO Auto-generated method stub
		return itemQuantity;
	}
	
	public CartBean createCart(Map<String,String> itemQuantities, Item im) throws SQLException, NumberFormatException{
		CartBean c = new CartBean();
		for (String itemNumber : itemQuantities.keySet()){
			ItemBean i = im.getItem(itemNumber);
			int quantity = Integer.parseInt(itemQuantities.get(itemNumber));
			c.addToCart(i, quantity);
		}
		return c;
	}

}
