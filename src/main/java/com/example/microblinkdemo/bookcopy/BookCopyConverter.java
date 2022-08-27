package com.example.microblinkdemo.bookcopy;

import com.example.microblinkdemo.book.Book;
import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.bookcopy.domain.BookCopyDomain;
import org.springframework.stereotype.Component;

@Component
public class BookCopyConverter {

    public BookCopyDomain entityToDomain(BookCopy bookCopy) {
        Book book = bookCopy.getBook();
        return BookCopyDomain.builder()
                .bookCopyId(bookCopy.getId())
                .serialNumber(bookCopy.getSerialNumber())
                .bookId(book.getId())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }
}
