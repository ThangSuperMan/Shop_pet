package com.example.shop_pet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.shop_pet.services.User.UserService;

@Configuration
public class ApplicationConfig {
    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.selectUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}