package com.example.demo.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.order.OrderRepository;

@SpringBootTest
public class UserRestControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository repo;
    
    @Autowired
    OrderRepository ordersRepo;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    
    @BeforeEach
    void cleanDatabase() {
    	ordersRepo.deleteAll();
        repo.deleteAll();
    }

    @Test
    void 全ユーザーを取得できる() throws Exception {

        User u = new User();
        u.setUsername("aaa");
        u.setPassword("pass");
        u.setRole("USER");
        repo.save(u);

        mockMvc.perform(get("/api/users").with(user("api_user").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value("aaa"))
            .andExpect(jsonPath("$[0].role").value("USER"));
    }


    @Test
    void ユーザーを作成できる() throws Exception {

        String json = """
            {
                "username": "rest_user",
                "password": "pass",
                "role": "USER"
            }
            """;

        mockMvc.perform(post("/api/users")
                .with(user("api_user").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("rest_user"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

}
