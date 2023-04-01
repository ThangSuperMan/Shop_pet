package com.example.bookstore.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String renderAdminPage() {
        return "Admin page";
    }
}