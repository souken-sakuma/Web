package com.example.demo.cart;

import com.example.demo.item.Item;

public class CartItem {
	private Item item;
	private int quantity;
	
	public CartItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public Item getItem(){
		return item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTotalPrice() {
		return  item.getPrice() * quantity;  
	
	}

}
