package com.example.demo.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.user.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationIntegrationTest {
	
	@BeforeEach
	void setup() {
	    repo.deleteAll();
	}


    @LocalServerPort
    int port;

    @Autowired
    UserRepository repo;

    @Test
    void ユーザー登録が成功しDBに保存される() {

        var client = org.springframework.web.client.RestClient.create();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "integrationUser");
        form.add("password", "pass1234");

        var res = client.post()
                .uri("http://localhost:" + port + "/users/new")
                .body(form)
                .retrieve()
                .toEntity(String.class);

        // リダイレクト確認
        assertThat(res.getStatusCode().is3xxRedirection()).isTrue();

        // DB に保存されているか
        var user = repo.findByUsername("integrationUser");
        assertThat(user).isPresent();

        // username が一致するか
        assertThat(user.get().getUsername()).isEqualTo("integrationUser");

        // パスワードがハッシュ化されているか
        assertThat(user.get().getPassword()).doesNotContain("pass1234");
    }
}
