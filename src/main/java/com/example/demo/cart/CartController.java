package com.example.demo.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {
	
	private final CartService cartService;
	private final ItemRepository itemRepo;
	
	public CartController(CartService cartService, ItemRepository itemRepo) {
		this.cartService = cartService;
		this.itemRepo = itemRepo;
	}
	
	@ModelAttribute("cart")
	public CartService cart() {
		return new CartService();
	}
	
	@GetMapping
	public String showCart(@ModelAttribute("cart") CartService cart, Model model) {
	    model.addAttribute("items", cart.getItems());
	    model.addAttribute("total", cart.getTotalPrice());
	    return "cart/cart";
	}

	
	@PostMapping("/add/{id}")
	public String addToCart(@PathVariable Long id, @ModelAttribute("cart") CartService cart) {
		Item item = itemRepo.findById(id).orElseThrow();
		cart.addItem(new CartItem(item, 1));
		return "redirect:/cart";
	}
	
	 @PostMapping("/update/{id}")
	    public String updateQuantity(
	            @PathVariable Long id,
	            @RequestParam int quantity,
	            @ModelAttribute("cart") CartService cart) {

	        cart.updateQuantity(id, quantity);
	        return "redirect:/cart";
	    }

	    @PostMapping("/remove/{id}")
	    public String removeItem(@PathVariable Long id, @ModelAttribute("cart") CartService cart) {
	        cart.removeItem(id);
	        return "redirect:/cart";
	    }
	}
