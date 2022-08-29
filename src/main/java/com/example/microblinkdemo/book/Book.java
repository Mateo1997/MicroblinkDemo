package com.example.microblinkdemo.book;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "\"book\"")
@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Short year;

    @OneToMany(mappedBy = "book")
    private List<BookCopy> bookCopies;

    public Book(Integer id) {
        this.id = id;
    }
}
