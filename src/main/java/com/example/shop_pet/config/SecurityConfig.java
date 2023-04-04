package com.example.shop_pet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.example.shop_pet.filter.JwtAuthFilter;

// import com.example.shop_pet.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private AuthenticationProvider authenticationProvider;
  private JwtAuthFilter jwtAuthFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // return http.csrf()
    //     .disable()
    //     .authorizeHttpRequests()
    //     .requestMatchers("")
    //     .permitAll()
    //     .anyRequest()
    //     .authenticated()
    //     .and()
    //     .sessionManagement()
    //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //     .and()
    //     .authenticationProvider(authenticationProvider)
    //     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    //     .logout()
    //     .logoutUrl("/api/v1/logout")
    //     .addLogoutHandler(logoutHandler)
    //     .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    //     .and()
    //     .httpBasic()
    //     .and()
    //     .build();

    return http.csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/admin")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        // .and()
        // .formLogin()
        // .and()
        // .logout()
        // .logoutSuccessUrl("/api/v1/books")
        // .and()
        .httpBasic()
        .and()
        .build();
  }
}
