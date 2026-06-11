package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartItem;
import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;



@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final ItemRepository itemRepo;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepo, ItemRepository itemRepo, UserRepository userRepository) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.userRepository = userRepository;
    }

    // ★ DataLoader 用：ユーザーを直接指定して注文を作る
    @Transactional
    public Order createOrderForUser(Cart cart, User user) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);

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
            orderItem.setQuantity(quantity);

            int subtotal = item.getPrice() * quantity;
            orderItem.setPrice(subtotal);

            order.addItem(orderItem);
            total += subtotal;
        }

        order.setTotalPrice(total);
        return orderRepo.save(order);
    }

    // ★ 通常の注文（ログイン中）
    @Transactional
    public Order createOrder(Cart cart) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("ログインしていません");
        }

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("ユーザーが見つかりません: " + username));

        return createOrderForUser(cart, user);
    }

    // ★ 一般ユーザー用：自分の注文履歴
    public List<Order> getOrderHistory(User user) {
        return orderRepo.findByUserOrderByOrderDateDesc(user);
    }

    // ★ 管理者用：全ユーザーの注文履歴
    public List<Order> getAllOrderHistory() {
        return orderRepo.findAllByOrderByOrderDateDesc();
    }

    public long count() {
        return orderRepo.count();
    }
}


