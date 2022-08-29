package com.example.microblinkdemo.libraryloan.domain;

import com.example.microblinkdemo.libraryloanrecords.LibraryLoanRecord;
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
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Setter
    @OneToMany(mappedBy = "libraryLoan", cascade = CascadeType.PERSIST)
    private List<LibraryLoanRecord> libraryLoanRecords;
}
