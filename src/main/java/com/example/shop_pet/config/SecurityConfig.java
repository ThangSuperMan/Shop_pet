package com.example.shop_pet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public UserDetailsService userDetailsService() {
        logger.info("UserDetailsService just triggered!");

        // UserDetails admin = User.withUsername("thangphan")
        // .password(encoder.encode("thang"))
        // .roles("ADMIN")
        // .build();

        // UserDetails user = User.withUsername("ngocphan")
        // .password(encoder.encode("ngoc"))
        // .roles("USER")
        // .build();
        // return new InMemoryUserDetailsManager(admin, user);

        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("SecurityFilterChain just triggered!");

        return http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/books/welcome", "/api/v1/signup").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/books/**")
                .authenticated()
                .and()
                .formLogin()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        logger.info("authenticationProvider just triggered!");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
