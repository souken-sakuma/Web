package com.example.demo.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.order.OrderRepository;

@SpringBootTest
@Transactional
public class UserViewControllerTest {

    @Autowired
    WebApplicationContext context;
    
    @Autowired
    OrderRepository orderRepo;
    
    @Autowired
    UserRepository repo;

    MockMvc mockMvc;
    

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        orderRepo.deleteAll();
        repo.deleteAll();
    }

    @Test
    void USERならプロフィールへリダイレクト() throws Exception {

    	String unique = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername("test_user_" + unique);
        user.setPassword("pass_" + unique);
        user.setRole("USER");
        repo.save(user);

        mockMvc.perform(get("/users").with(user(user.getUsername()).roles("ADMIN")))
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

        mockMvc.perform(get("/users").with(user(admin.getUsername()).roles("GUEST")))
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

        mockMvc.perform(get("/users").with(user(guest.getUsername()).roles("GUEST")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users/list"));
    }
    
    
    
    @Test
    void ユーザーの新規登録() throws Exception {
    	
    	mockMvc.perform(post("/users/new")
    			.param("username", "test_user")
    			.param("password", "pass1234")
    			.param("name", "太郎")
    			.param("age", "20")
    			.with(csrf()))
    			.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));
    	
    	List<User> users = repo.findAll();
    	assertEquals(1, users.size());
    	assertEquals("test_user", users.get(0).getUsername());
        }
    
    @Test
    void ユーザーを新規登録したらログイン画面へいく() throws Exception {
    	
    	mockMvc.perform(post("/users/signup")
    			.param("username", "test_user")
    			.param("password", "pass1234")
    			.param("name", "太郎")
    			.param("age", "20")
    			.with(csrf()))
    			.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    	
    	List<User> users = repo.findAll();
    	assertEquals(1, users.size());
    	assertEquals("test_user", users.get(0).getUsername());
    	
    	assertEquals(1, repo.count());
        }
}
