package com.example.microblinkdemo.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDomain {

    private Integer bookRecordId;
    private Integer bookId;
    private String serialNumber;
    private String title;
    private String author;
    private String publisher;
    private Short year;
}
