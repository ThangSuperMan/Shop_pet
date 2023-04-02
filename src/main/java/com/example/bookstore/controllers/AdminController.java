package com.example.bookstore.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bookstore.dto.AuthRequest;
import com.example.bookstore.services.Jwt.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final JwtService jwtService;

    public AdminController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping()
    public String renderAdminPage() {
        return "Admin page";
    }

    @GetMapping("/login") 
    public String adminLoginPage() {
        return "<strong>Admin login page</strong>";
    }

    @PostMapping("/login")
    public String loginAndGetLogin(@RequestBody AuthRequest authRequest) {
        logger.info("loginAndGetLogin controller is running...");
        System.out.println("authRequest :>> " + authRequest);
        logger.info("username from body form: " + authRequest.getUsername());
        return jwtService.generateToken(authRequest.getUsername());
    }

    @GetMapping("/**")
    public RedirectView notFound() {
        return new RedirectView("/api/v1/admin/login");
        // return new ResponseEntity<>("Not found page", HttpStatus.NOT_FOUND);
    }
}