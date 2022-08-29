package com.example.microblinkdemo.libraryloanrecords;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryLoanRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libraryLoanId", nullable = false)
    private LibraryLoan libraryLoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookCopyId", nullable = false)
    private BookCopy bookCopy;
}
