/*package com.example.demo.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class DashboardController {
	
	@Autowired
    private SalesService service;

    @GetMapping("/sales/monthly")
    @ResponseBody
    public List<MonthlySales> monthlySales() {
        return service.getMonthlySales();
    }

    @GetMapping("/dashboard")
    public String vies() {
        return "dashboard/dashboard";
    }
}
*/
