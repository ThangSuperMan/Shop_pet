package com.example.shop_pet.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    private int id;
    private String title;
    private String author;
    private float price;
    private String createdAt;

    @Override
    public String toString() {
        return "book [" + title + ", " + author + ", $" + price + ", created_at " + createdAt + "]";
    }
}
