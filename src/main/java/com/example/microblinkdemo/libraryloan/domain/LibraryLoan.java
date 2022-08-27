package com.example.microblinkdemo.libraryloan.domain;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookCopyId", nullable = false)
    private BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
