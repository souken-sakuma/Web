package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Pwnew {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/encode")
    public String encode() {
        return passwordEncoder.encode("admin123");
    }
}
