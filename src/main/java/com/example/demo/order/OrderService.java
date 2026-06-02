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

        for (CartItem cartItem : cart.getItems()) {

            Item item = cartItem.getItem();

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(cartItem.getQuantity());

            order.addItem(orderItem);
        }

        return orderRepo.save(order);
    }
    
    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
