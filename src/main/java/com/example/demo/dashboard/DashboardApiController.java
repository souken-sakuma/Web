package com.example.demo.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardApiController {

    @Autowired
    private SalesService service;

    @GetMapping("/sales/monthly")
    public List<MonthlySales> monthlySales() {
        return service.getMonthlySales();
    }
}
