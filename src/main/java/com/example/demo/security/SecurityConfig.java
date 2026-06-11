package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	http
    	.authorizeHttpRequests(auth -> auth
    			.requestMatchers(
    			        "/", "/items", "/item", "/item/**",
    			        "/item-names",
    			        "/signup", "/users/signup", "/users/new",
    			        "/logout", "/encode", "/css/**"
    			    ).permitAll()

    		    // 一般ユーザーも使うプロフィールは「ログインしていればOK」
    		    .requestMatchers("/users/profile", "/users/profile/**").authenticated()

    		    // 管理者だけが使うユーザー管理系
    		    .requestMatchers("/users/list", "/users/new", "/users/edit/**", "/users/delete/**")
    		        .hasRole("ADMIN")

    		    .requestMatchers("/orders/admin-history").hasRole("ADMIN")
    		    .anyRequest().authenticated()
    		)

        .csrf(csrf -> csrf.disable())
        .formLogin(login -> login
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/users", false)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        );

    return http.build();
}
}

