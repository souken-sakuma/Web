package com.example.demo.cart;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;

@SpringBootTest
public class CartControllerTest {
	
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
    
    
    @Test
    void カート画面が表示される() throws Exception {
        mockMvc.perform(get("/cart").with(user("test").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(view().name("cart/cart"))
            .andExpect(model().attributeExists("items"))
            .andExpect(model().attributeExists("total"));
    }
    
    
    @Autowired
    ItemRepository itemRepo;
    
    @Test
    void 商品をカートに追加する() throws Exception {

        Item item = new Item();
        item.setName("テスト商品");
        item.setPrice(1000);
        item = itemRepo.save(item);

        mockMvc.perform(get("/cart/add/" + item.getId())
                .with(user("test").roles("USER")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/cart"));
    }

    

}
