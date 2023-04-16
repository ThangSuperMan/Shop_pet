// package com.example.shop_pet.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig {
//   private final String GET = "GET";
//   private final String POST = "POST";
//   private final String PUT = "PUT";
//   private final String DELETE = "DELETE";

//   @Bean
//   public WebMvcConfigurer corsConfigurer() {
//     return new WebMvcConfigurer() {
//       @Override
//       public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//             .allowedMethods(GET, POST, PUT, DELETE)
//             .allowedHeaders("*")
//             .allowedOriginPatterns("*")
//             .allowCredentials(true);
//       }
//     };
//   }
// }
