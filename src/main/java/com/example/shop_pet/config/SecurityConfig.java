package com.example.shop_pet.config;

import com.example.shop_pet.filter.JwtAuthFilter;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
// @EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired JwtAuthFilter jwtAuthFilter;

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
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    configuration.setAllowCredentials(false);
    // the below three lines will add the relevant CORS response headers
    configuration.addAllowedOrigin("*");
    // configuration.addAllowedOriginPattern("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    logger.info("SecurityFilterChain just triggered!");

    return http.csrf()
        .disable()
        .cors()
        .and()
        .authorizeHttpRequests()
        .requestMatchers(
            // "/api/v1/admin", "/api/v1/products/**", "/api/v1/authenticate", "/api/v1/signup")
            "/api/v1/products/**", "/api/v1/authenticate", "/api/v1/signup")
        .permitAll()
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/admin")
        .authenticated()
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api-docs/**", "/swagger-ui/**")
        .permitAll()
        // .and()
        // .authorizeHttpRequests()
        // .requestMatchers("/books/**")
        // .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
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

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
