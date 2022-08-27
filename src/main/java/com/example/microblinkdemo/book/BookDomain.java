package com.example.microblinkdemo.book;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookDomain {

    private Integer bookId;
    private String title;
    private String author;
    private String publisher;
    private Short year;
}
