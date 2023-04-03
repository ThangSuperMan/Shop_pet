// package com.example.bookstore.config;

// import java.io.IOException;

// import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import
// org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.web.servlet.RequestToViewNameTranslator;

// import com.example.bookstore.models.UserEntity;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// class JwtAuthFilter extends OncePerRequestFilter {
//     Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

//     UserDetailsService userDetailsService;

//     // public JwtAuthFilter(UserDetailsService userDetailsService) {
//     //     logger.info("User defailt service from bean :>> " + userDetailsService);
//     //     this.userDetailsService = userDetailsService;
//     // }

//     // public JwtAuthFilter() {
//     //     logger.info("One request just came!");
//     // }

//     @Override
//     protected void doFilterInternal(
//             HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain) throws ServletException, IOException {

//         final String authHeader = request.getHeader("token");
//         final String userEmail;
//         final String jwtToken;

//         if (authHeader == null || !authHeader.startsWith("Bearer")) {
//             logger.warn("authHeader :>> " + authHeader);
//             filterChain.doFilter(request, response);
//             return;
//         }

//         int beginIndex = 7;
//         jwtToken = authHeader.substring(beginIndex);
//         userEmail = "something";
//         if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
//         {
//             UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//             System.out.println("userDetails :>> " + userDetails);
//             // UserDetails userDetails = .
//             // final boolean isTokenValid;
//             // if (isTokenValid) {
//             //     UsernamePasswordAuthenticationToken authToken = new
//             UsernamePasswordAuthenticationToken(, null);
//             // }
//         }
//     }
// }
