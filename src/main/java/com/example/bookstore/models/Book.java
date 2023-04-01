package com.example.bookstore.models;

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

    // protected Book(String title, String author, float price) {
    //     this.id = id;
    //     this.title = title;
    //     this.author = author;
    //     this.price = price;
    // }

    @Override
    public String toString() {
        return "book [" + title + ", " + author + ", $" + price + "]";
    }
}
