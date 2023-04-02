package com.example.shop_pet.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.shop_pet.dto.AuthRequest;
import com.example.shop_pet.models.User;
import com.example.shop_pet.services.User.UserService;
import com.example.shop_pet.utils.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping()
    public String renderAdminPage() {
        return "Admin page";
    }

    // USER
    @GetMapping("/login")
    public String userLoginPage() {
        return "<strong>login page</strong>";
    }

    // ADMIN
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "<strong>login page</strong>";
    }

    public Boolean isSamePassword(String password, String confirmPassword) {
        if (password == confirmPassword) {
            return true;
        }
        return false;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        logger.info("Signup AuthController is running...");
        if (!userService.isUsernameExist(user.getUsername())) {
            String message = "Username: " + user.getUsername() + " exists, please choose another one!";
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!isSamePassword(user.getPassword(), user.getConfirmPassword())) {
            String message = "Password and confirm password does not match, make sure you typed it correctly!";
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int result = userService.insertUser(user);
        if (result > 0) {
            logger.info("Insert account successfully");
            String message = "Signup successfully, now you can login, enjoy :)";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        String message = "Username: " + user.getUsername() + " exists, please choose another one!";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public String loginAndGetLogin(@RequestBody AuthRequest authRequest) {
        logger.info("loginAndGetLogin AuthController is running...");
        System.out.println("authRequest :>> " + authRequest);
        return jwtUtils.generateToken(authRequest.getUsername());
    }

    @GetMapping("/**")
    public RedirectView notFound() {
        // Authenticated -> Go to the dashboard (if admin) or if user go to the homepage
        // No authenticated -> Have to login first (redirect to login page)
        return new RedirectView("/api/v1/admin/login");
    }
}