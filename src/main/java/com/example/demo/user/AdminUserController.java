package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final UserRepository repo;

    public AdminUserController(UserService userService, UserRepository repo) {
        this.userService = userService;
        this.repo = repo;
    }

    @GetMapping("/new")
    public String newAdminUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/user-new";
    }

    @PostMapping("/new")
    public String createAdminUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("users", repo.findByMemberNumberContaining(keyword));
        return "users/user-list";
    }
}
