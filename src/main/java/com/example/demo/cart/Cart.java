package com.example.demo.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private List<CartItem> items = new ArrayList<>();
	
	public Cart() {
		
	}
	
	public List<CartItem> getItems(){
		return items;
	}
	
	public void clear() {
		items.clear();
	}
	
	public int getTotalPrice() {
		int total = 0;
		for (CartItem cartItem : items) {
			total += cartItem.getTotalPrice();
		}
		return total;
	}

	
	public void addItem(CartItem item) {
	    items.add(item);
	}

	public void updateQuantity(Long itemId, int quantity) {
	    for (CartItem ci : items) {
	        if (ci.getItem().getId().equals(itemId)) {
	            ci.setQuantity(quantity);
	            return;
	        }
	    }
	}

	public void removeItem(Long itemId) {
	    items.removeIf(ci -> ci.getItem().getId().equals(itemId));
	}

}
