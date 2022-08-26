package com.example.microblinkdemo.userbookrecord.domain;

import com.example.microblinkdemo.book.domain.BookRecord;
import com.example.microblinkdemo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "UserBookRecord")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookRecordId", nullable = false)
    private BookRecord bookRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
