/*package com.example.demo.config;

import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartItem;
import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;
import com.example.demo.order.OrderService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(
            UserService userService,
            ItemRepository itemRepository,
            OrderService orderService
    ) {
        return args -> {


            // -------------------------
            // ① ユーザー10件作成
            // -------------------------
            String[][] users = {
                    {"Taro", "25", "USER", "taro1"},
                    {"Hanako", "28", "USER", "hanako1"},
                    {"Jiro", "30", "ADMIN", "jiro1"},
                    {"Mika", "22", "USER", "mika1"},
                    {"Ken", "27", "USER", "ken1"},
                    {"Saki", "33", "USER", "saki1"},
                    {"Ami", "24", "USER", "ami1"},
                    {"Shun", "29", "ADMIN", "shun1"},
                    {"Rina", "31", "USER", "rina1"},
                    {"Kota", "26", "USER", "kota1"}
            };

            for (String[] u : users) {
                User user = new User();
                user.setName(u[0]);
                user.setAge(Integer.parseInt(u[1]));
                user.setRole(u[2]);
                user.setUsername(u[3]);
                user.setPassword("pass");

                userService.registerUser(user);
            }

            // -------------------------
            // ② 商品10件作成
            // -------------------------
            for (int i = 1; i <= 10; i++) {
                Item item = new Item();
                item.setName("商品" + i);
                item.setPrice(100 * i);
                item.setStock(50);
                itemRepository.save(item);
            }

            // -------------------------
            // ③ 各ユーザーに注文を1件作成
            // -------------------------
            Random rand = new Random();
            List<User> allUsers = userService.findAll();
            List<Item> allItems = itemRepository.findAll();

            for (User user : allUsers) {

                // カート作成
                Cart cart = new Cart();

                // ランダムに2〜3商品をカートに入れる
                for (int i = 0; i < 2 + rand.nextInt(2); i++) {
                    Item item = allItems.get(rand.nextInt(allItems.size()));
                    int quantity = 1 + rand.nextInt(3);

                    CartItem cartItem = new CartItem(item, quantity);
                    cart.addItem(cartItem);
                }

                // 注文作成
                orderService.createOrderForUser(cart, user);

            }

            System.out.println("=== Dummy users, items, and orders inserted ===");
        };
    }
}
*/