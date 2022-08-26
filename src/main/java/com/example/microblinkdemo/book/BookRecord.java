package com.example.microblinkdemo.book;

import com.example.microblinkdemo.userbookrecord.UserBookRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@Table(name = "\"BookRecord\"")
@Entity
@Getter
@NoArgsConstructor
public class BookRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private Book book;

    @OneToMany(mappedBy = "bookRecord")
    private List<UserBookRecord> userBookRecords;

    public BookRecord(Integer id) {
        this.id = id;
    }
}
