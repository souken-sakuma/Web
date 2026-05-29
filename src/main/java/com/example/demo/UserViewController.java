package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
