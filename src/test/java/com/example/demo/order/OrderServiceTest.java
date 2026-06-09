package com.example.demo.order;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartItem;
import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
    UserRepository userRepo;

    @Mock
    ItemRepository itemRepo;

    @Mock
    OrderRepository orderRepo;

    @InjectMocks
    OrderService service;

    @Test
    void 注文が作成される() {
    	
    	SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("taro", null)
        );
    	
        User user = new User();
        user.setUsername("taro");
        when(userRepo.findByUsername("taro")).thenReturn(Optional.of(user));
        
        
        

        Item item = new Item();
        item.setId(1L);
        item.setPrice(1000);
        item.setStock(10);

        CartItem cartItem = new CartItem(item, 2);
        
        Cart cart = new Cart();
        cart.addItem(cartItem);
        
        when(orderRepo.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));
        
        Order order = service.createOrder(cart);
        
        verify(orderRepo).save(any(Order.class));
        verify(itemRepo).save(item); 

        assert order.getTotalPrice() == 4000;
        assert item.getStock() == 8;
    }
}
