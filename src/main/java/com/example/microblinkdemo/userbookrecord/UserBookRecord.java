package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.book.BookRecord;
import com.example.microblinkdemo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime borrowTime;

    private LocalDateTime returnTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookRecordId", nullable = false)
    private BookRecord bookRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
