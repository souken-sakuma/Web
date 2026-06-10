package com.example.demo.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginSuccessIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository repo;

    @BeforeEach
    void setup() {
        repo.deleteAll(); // テスト前にDBをクリーンにする

        // ログイン用ユーザーを事前に作成
        User u = new User();
        u.setUsername("loginUser");
        u.setPassword("pass1234");
        u.setRole("USER");
        userService.registerUser(u); 
    }
    
    @Test
    void ログイン成功後_保護ページにアクセスできる() {

        var client = org.springframework.web.client.RestClient.create();

        // ログインフォーム送信
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "loginUser");
        form.add("password", "pass1234");

        var loginRes = client.post()
                .uri("http://localhost:" + port + "/login")
                .body(form)
                .retrieve()
                .toEntity(String.class);

        // ログイン成功 → リダイレクト
        assertThat(loginRes.getStatusCode().is3xxRedirection()).isTrue();

        // セッションID（JSESSIONID）が発行されているか
        var cookies = loginRes.getHeaders().get("Set-Cookie");
        assertThat(cookies).isNotEmpty();

        String sessionId = cookies.stream()
                .filter(c -> c.contains("JSESSIONID"))
                .findFirst()
                .orElseThrow();

        // セッション付きで保護ページへアクセス
        var protectedRes = client.get()
                .uri("http://localhost:" + port + "/users/list")
                .header("Cookie", sessionId)
                .retrieve()
                .toEntity(String.class);

        // 200 OK → ログイン成功している証拠
        assertThat(protectedRes.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
