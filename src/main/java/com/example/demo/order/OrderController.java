package com.example.demo.order;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.cart.Cart;

@Controller
@RequestMapping("/orders")
@SessionAttributes("cart")
public class OrderController {
	
	private final OrderRepository orderRepository;

    private final OrderService orderService;
   

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("cart") Cart cart) {
    	Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(cart.getTotalPrice());
        orderRepository.save(order);

        cart.clear();

        return "orders/complete";
    }
    

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders/list";
    }
    
}

