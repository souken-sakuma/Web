package com.example.demo.user;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class UserRepositoryTest {
	
	@Autowired
    UserRepository userRepo;

	@Test
    void ユーザー名で検索できる() {
        User user = new User();
        user.setUsername("taro");
        user.setPassword("pass");
        userRepo.save(user);

        Optional<User> result = userRepo.findByUsername("taro");

        assert result.isPresent();
        assert result.get().getUsername().equals("taro");
    }

}
