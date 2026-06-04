package com.example.demo.order;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.cart.Cart;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Controller
@RequestMapping("/orders")
@SessionAttributes("cart")
public class OrderController {
	
	private final OrderRepository orderRepository;
    private final OrderService orderService;    
    private final UserRepository userRepository;
   

    public OrderController(OrderRepository orderRepository, OrderService orderService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("cart") Cart cart, Model model) {
    	Order order = orderService.createOrder(cart);

        // ★ カートを空にする
        cart.clear();

        // 完了画面に注文情報を渡す
        model.addAttribute("order", order);

        return "orders/complete";
    }
    

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders/list";
    }
    
    @GetMapping("/history")
    public String history(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("ユーザーが見つかりません"));

        List<Order> orders = orderService.getOrderHistory(user);

        model.addAttribute("orders", orders);

        return "orders/history";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-history")
    public String adminHistory(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders/admin-history";
    }


    
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("注文が見つかりません：" + id));

        model.addAttribute("order", order);

        return "orders/detail";
    }
}
