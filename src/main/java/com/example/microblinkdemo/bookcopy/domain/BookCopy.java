package com.example.microblinkdemo.bookcopy.domain;

import com.example.microblinkdemo.book.Book;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, updatable = false)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToMany(mappedBy = "bookCopy")
    private List<LibraryLoan> libraryLoan;

    public BookCopy(Integer id) {
        this.id = id;
    }
}
