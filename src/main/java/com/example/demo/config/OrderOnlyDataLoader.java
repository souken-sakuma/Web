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
public class OrderOnlyDataLoader {

    @Bean
    public CommandLineRunner loadOrders(
            UserService userService,
            ItemRepository itemRepository,
            OrderService orderService
    ) {
        return args -> {

            List<User> allUsers = userService.findAll();
            List<Item> allItems = itemRepository.findAll();
            Random rand = new Random();

            for (User user : allUsers) {

                // 1ユーザーあたり 3〜6件の注文
                int orderCount = 3 + rand.nextInt(4);

                for (int k = 0; k < orderCount; k++) {

                    Cart cart = new Cart();

                    // 1注文あたり 1〜5商品
                    int itemCount = 1 + rand.nextInt(5);

                    for (int j = 0; j < itemCount; j++) {
                        Item item = allItems.get(rand.nextInt(allItems.size()));
                        int quantity = 1 + rand.nextInt(5);
                        cart.addItem(new CartItem(item, quantity));
                    }

                    orderService.createOrderForUser(cart, user);
                }
            }

            System.out.println("=== Orders added for existing users ===");
        };
    }
}
*/