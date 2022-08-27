package com.example.microblinkdemo.book;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.util.TimestampEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@Table(name = "\"Book\"")
@Entity
@Getter
@NoArgsConstructor
public class Book extends TimestampEntity {

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
