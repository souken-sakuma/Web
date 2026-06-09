package com.example.demo.user;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
    UserRepository repo;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserService service;

    @Test
    void ユーザー登録が成功する() {
        User form = new User();
        form.setUsername("taro");
        form.setPassword("pass1234");

        when(encoder.encode("pass1234")).thenReturn("hashed");

        service.registerUser(form);

        verify(encoder).encode("pass1234");
        verify(repo).save(any(User.class));
    }
}
