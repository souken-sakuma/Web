package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserViewController {
	
	private final UserRepository repo;
	private final UserService userService;

    public UserViewController(UserRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }
    
    @GetMapping("/signup")
    public String singupForm(Model model) {
    	model.addAttribute("user", new User());
    	return "user/signup";
    }
    
    @PostMapping("/signup")
    public String singup(@ModelAttribute User user) {
    	userService.registerUser(user);
    	return "redirect:/login";
    }

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", repo.findAll());
        return "user/user-list";
    }
    
    
    
    
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", repo.findAll());
        return "user/user-list";
    }
    
    @GetMapping("/new")
    public String newUserForm(Model model) {
    	model.addAttribute("user", new User());
    	return "user/user-form";
    }
    
    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
    	repo.save(user);
    	return "redirect:/users/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
    	repo.deleteById(id);
    	return "redirect:/users/list";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    	User user = repo.findById(id).orElseThrow();
    	model.addAttribute("user", user);
    	return "user/user-edit-form";
    }
    
    @PostMapping("/edit")
    public String update(@ModelAttribute User user) {
    	repo.save(user);
    	return "redirect:/users/list";
    }
}
