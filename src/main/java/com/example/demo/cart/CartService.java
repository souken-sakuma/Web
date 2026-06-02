package com.example.demo.cart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private Map<Long, CartItem> cart = new HashMap<>();

    public void addItem(CartItem item) {
        Long id = item.getItem().getId();

        if (cart.containsKey(id)) {
            cart.get(id).setQuantity(cart.get(id).getQuantity() + item.getQuantity());
        } else {
            cart.put(id, item);
        }
    }

    public Collection<CartItem> getItems() {
        return cart.values();
    }

    public void updateQuantity(Long id, int quantity) {
        if (cart.containsKey(id)) {
            cart.get(id).setQuantity(quantity);
        }
    }

    public void removeItem(Long id) {
        cart.remove(id);
    }

    public int getTotalPrice() {
        return cart.values().stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }
    
    public void clear() {
        cart.clear();
    }

}
