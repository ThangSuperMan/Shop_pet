package com.example.bookstore.models;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    private int id;
    private String username;
    private String password;
    private String email;

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
