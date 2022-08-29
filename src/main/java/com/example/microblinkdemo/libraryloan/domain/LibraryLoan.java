package com.example.microblinkdemo.libraryloan.domain;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.util.TimestampEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryLoan extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @ManyToMany
    @JoinTable(name = "library_loan_record",
            joinColumns = @JoinColumn(name = "library_loan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_copy_id", referencedColumnName =  "id")
    )
    private List<BookCopy> bookCopy;
}
