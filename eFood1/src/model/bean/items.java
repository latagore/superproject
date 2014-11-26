package model.bean;

public class items {
	String name;
	int quantity;
	double price;
	String id;
	
	public items(String name, int quantity, double price, String id){
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
