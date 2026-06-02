package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartItem;
import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;


@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final ItemRepository itemRepo;

    public OrderService(OrderRepository orderRepo, ItemRepository itemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public Order createOrder(Cart cart) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        
        int total = 0;

        for (CartItem cartItem : cart.getItems()) {

            Item item = cartItem.getItem();
            int quantity = cartItem.getQuantity();
            
            if (item.getStock() < quantity) {
                throw new IllegalStateException("在庫が不足しています: " + item.getName());
            }
            item.setStock(item.getStock() - quantity);
            itemRepo.save(item);
            
            
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(cartItem.getQuantity());

            int subtotal = item.getPrice() * quantity; // 小計
            orderItem.setPrice(subtotal);

            order.addItem(orderItem);
            
            total += subtotal;

        }

        order.setTotalPrice(total);
        
        return orderRepo.save(order);
    }
    
    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
