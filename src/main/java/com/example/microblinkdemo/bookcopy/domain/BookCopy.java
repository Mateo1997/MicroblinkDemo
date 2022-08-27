package com.example.microblinkdemo.bookcopy.domain;

import com.example.microblinkdemo.book.Book;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.util.TimestampEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BookCopy extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, updatable = false)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @OneToMany(mappedBy = "bookCopy")
    private List<LibraryLoan> libraryLoans;

    public BookCopy(Integer id) {
        this.id = id;
    }
}
