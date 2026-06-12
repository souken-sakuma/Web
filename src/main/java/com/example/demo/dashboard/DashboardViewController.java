package com.example.demo.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";  // templates/dashboard/dashboard.html
    }
}
