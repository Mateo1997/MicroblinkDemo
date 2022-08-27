package com.example.microblinkdemo.bookcopy.domain;

import com.example.microblinkdemo.book.BookDomain;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookCopyDomain extends BookDomain {

    private Integer bookCopyId;
    private String serialNumber;
}
