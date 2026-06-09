package com.example.demo.item;

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

@SpringBootTest
public class ItemControllerTest {
	
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
    void 商品一覧が表示される() throws Exception {
        mockMvc.perform(get("/items").with(user("test").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(view().name("items/list"))
            .andExpect(model().attributeExists("items"));
    }
    
    
    @Test
    void 商品の新規作成画面が表示される() throws Exception {
        mockMvc.perform(get("/items/new").with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(view().name("items/form"))
            .andExpect(model().attributeExists("item"));
    }

    
    @Autowired
    ItemRepository itemRepo;

    @Test
    void 商品の編集画面が表示される() throws Exception {

        // テスト用の商品を作成
        Item item = new Item();
        item.setName("テスト商品");
        item.setPrice(1000);
        item = itemRepo.save(item);

        mockMvc.perform(get("/items/edit/" + item.getId())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(view().name("items/form"))
            .andExpect(model().attributeExists("item"));
    }

    

}
