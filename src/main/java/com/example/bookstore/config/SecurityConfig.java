package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable().authorizeHttpRequests()
        .requestMatchers( "/api/v1/admin")
        .authenticated()
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .and()
        .logout().logoutSuccessUrl("/api/v1/books")
        .and()
        .httpBasic()
        .and()
        .build();
        // return http.csrf()
        //         .disable()
        //         .authorizeHttpRequests()
        //         .requestMatchers("/api/v1/admin/login").permitAll()
                // .and()
                // .authorizeHttpRequests()
                // .requestMatchers("/api/v1/**")
                // .authenticated()
                // .and()
                // .formLogin()
                // .and()
                // .httpBasic()
                // .and()
                // .build();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
}
