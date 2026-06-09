package com.example.demo.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UserViewControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    UserRepository repo;

    @Test
    void USERならプロフィールへリダイレクト() throws Exception {

    	String unique = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername("test_user_" + unique);
        user.setPassword("pass_" + unique);
        user.setRole("USER");
        repo.save(user);

        mockMvc.perform(get("/users").with(user("test_user").roles("USER")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users/profile"));
    }

    @Test
    void ADMINなら管理者履歴へリダイレクト() throws Exception {

        User admin = new User();
        admin.setUsername("test_admin");
        admin.setPassword("pass");
        admin.setRole("ADMIN");
        repo.save(admin);

        mockMvc.perform(get("/users").with(user("test_admin").roles("ADMIN")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/orders/admin-history"));
    }

    @Test
    void その他のロールならユーザー一覧へリダイレクト() throws Exception {

        User guest = new User();
        guest.setUsername("test_guest");
        guest.setPassword("pass");
        guest.setRole("GUEST");
        repo.save(guest);

        mockMvc.perform(get("/users").with(user("test_guest").roles("GUEST")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users/list"));
    }
}
