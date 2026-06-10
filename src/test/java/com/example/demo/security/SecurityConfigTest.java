package com.example.demo.security;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    SecurityFilterChain securityFilterChain;

    @Test
    void セキュリティコンフィグが読み込まれる() {
        assertThat(securityFilterChain).isNotNull();
    }
}
