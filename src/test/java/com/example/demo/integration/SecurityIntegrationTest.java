package com.example.demo.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void 未ログインユーザーはログイン画面へリダイレクトされる() {
        RestClient client = RestClient.create();

        var res = client.get()
                .uri("http://localhost:" + port + "/users")
                .retrieve()
                .toEntity(String.class);

        assertThat(res.getStatusCode().is3xxRedirection()).isTrue();
        assertThat(res.getHeaders().getLocation().toString()).contains("/login");
    }
}
