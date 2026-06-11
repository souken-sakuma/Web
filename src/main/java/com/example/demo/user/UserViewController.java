package com.example.demo.user;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    	return "users/signup";
    }
    
    @PostMapping("/signup")
    public String singup(@ModelAttribute User user) {
    	userService.registerUser(user);
    	return "redirect:/login";
    }

    @GetMapping("")
    public String users(Principal principal) {

        User user = repo.findByUsername(principal.getName())
                .orElseThrow();

        // 一般ユーザー
        if (user.getRole().equals("USER") || user.getRole().equals("ROLE_USER")) {
            return "redirect:/users/profile";
        }

        // 管理者
        if (user.getRole().equals("ADMIN") || user.getRole().equals("ROLE_ADMIN")) {
        	return "redirect:/users/list";
        }

        return "redirect:/users/list";
    }

    
    @GetMapping("/new")
    public String newUserForm(Model model) {
    	model.addAttribute("user", new User());
    	return "users/user-form";
    }
    
    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
    	userService.registerUser(user);
    	//repo.save(user);//
    	return "redirect:/users/list";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    	User user = repo.findById(id).orElseThrow();
    	model.addAttribute("user", user);
    	return "users/user-edit-form";
    }
    
    @PostMapping("/edit")
    public String update(@ModelAttribute User user) {
    	repo.save(user);
    	return "redirect:/users/list";
    }
    
    @GetMapping("/list")
    public String list(Model model, Principal principal) {
    	
    	User user = repo.findByUsername(principal.getName())
    			.orElseThrow();
    	
    	// 一般ユーザーはプロフィールへ
        if (user.getRole().equals("USER") || user.getRole().equals("ROLE_USER")) {
            return "redirect:/users/profile";
        }

        // 管理者はユーザー一覧ページを表示
        model.addAttribute("users", repo.findAll());
        return "users/user-list";   // ★ これが正しい
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", repo.findById(id).orElseThrow());
        return "users/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
    	repo.deleteById(id);
        return "redirect:/users/list";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        return repo.findByUsername(principal.getName())
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/profile";
                })
                .orElse("redirect:/login");
    }
    
    @PostMapping("/profile/edit")
    public String updateProfile(User form, Principal principal, RedirectAttributes ra) {
    	User user = repo.findByUsername(principal.getName())
                .orElseThrow();

        user.setName(form.getName());
        user.setAge(form.getAge());
        user.setUsername(form.getUsername());

        repo.save(user);
        
        ra.addFlashAttribute("success", "プロフィールを更新しました。");

        return "redirect:/users/profile";
    }
}