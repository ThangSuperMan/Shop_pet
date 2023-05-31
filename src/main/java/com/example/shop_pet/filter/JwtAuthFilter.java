package com.example.shop_pet.filter;

import com.example.shop_pet.config.UserInfoUserDetailsService;
import com.example.shop_pet.utils.JwtUtils;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final int BEGIN_INDEX = 7;

  @Autowired private JwtUtils jwtUtils;

  @Autowired UserInfoUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    System.out.println("authHeader :>> " + authHeader);
    System.out.println("token :>> " + token);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(BEGIN_INDEX);
      System.out.println("token substring :>> " + token);
      username = jwtUtils.extractUsername(token);
      System.out.println("username extracted from token :>> " + username);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtUtils.isTokenValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } 
    }

    // Move to next filter
    filterChain.doFilter(request, response);
  }
}
