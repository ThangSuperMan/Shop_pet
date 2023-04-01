// package com.example.bookstore.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.DefaultSecurityFilterChain;
// import org.springframework.security.web.SecurityFilterChain;

// // @Configuration
// @EnableWebSecurity
// public class SecurityConfig implements UserDetailsService {

//     // @Bean
//     // public InMemoryUserDetailsManager userDetailsService() {
//     // UserDetails user1 = User.withUsername("user1")
//     // .password(passwordEncoder().encode("user1password"))
//     // .roles("USER")
//     // .build();

//     // UserDetails user2 = User.withUsername("user2")
//     // .password(passwordEncoder().encode("user2password"))
//     // .roles("USER")
//     // .build();

//     // UserDetails admin = User.withUsername("admin")
//     // .password(passwordEncoder().encode("user2password"))
//     // .roles("USER")
//     // .build();

//     // return new InMemoryUserDetailsManager(user1, user2, admin);
//     // }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         return new User("thang", "1", null);
//     }

//     @Bean
//     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .csrf()
//                 .disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers(HttpMethod.GET)
//                 .permitAll()
//                 .anyRequest()
//                 .authenticated()
//                 .and()
//                 .httpBasic();
//         return http.build();
//     }

//     // @Bean
//     // public PasswordEncoder passwordEncoder() {
//     // return new BCryptPasswordEncoder();
//     // }
// }
