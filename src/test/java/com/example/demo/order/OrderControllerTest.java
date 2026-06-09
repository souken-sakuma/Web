package com.example.demo.order;

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
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;


@SpringBootTest
public class OrderControllerTest {

    @Autowired
    WebApplicationContext context;
    
    MockMvc mockMvc;

    @Autowired
    UserRepository repo;

    @Autowired
    OrderRepository orderRepository;  // ← これが正しい
    
    @Autowired
    ItemRepository itemRepository;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void cleanDatabase() {
        orderRepository.deleteAll();
        repo.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void 注文履歴の詳細が表示される() throws Exception {

        // ① ユーザー作成
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("pass");
        user.setRole("USER");
        repo.save(user);

        // ② 注文作成
        Order o = new Order();
        o.setUser(user);

        // ③ Item（商品）を作成
        Item itemEntity = new Item();
        itemEntity.setName("商品A");
        itemEntity.setPrice(1000);
        itemRepository.save(itemEntity);

        // ④ OrderItem を作成
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(itemEntity);
        orderItem.setPrice(1000);
        orderItem.setQuantity(1);

        // ⑤ Order に追加
        o.addItem(orderItem);

        // ⑥ 保存
        orderRepository.save(o);

        // ⑦ アクセス
        mockMvc.perform(get("/orders/detail/" + o.getId())
                .with(user("test_user").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(view().name("orders/detail"))
            .andExpect(model().attributeExists("order"));
    }



}
