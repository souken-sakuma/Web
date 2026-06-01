package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserViewController {
	
	private final UserRepository repo;

    public UserViewController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", repo.findAll());
        return "user-list";
    }
    
    @GetMapping("/new")
    public String newUserForm(Model model) {
    	model.addAttribute("user", new User());
    	return "user-form";
    }
    
    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
    	repo.save(user);
    	return "redirect:/users/list";
    }
}
