package com.example.microblinkdemo.userbookrecord.domain;

import com.example.microblinkdemo.book.domain.BookDomain;
import com.example.microblinkdemo.user.domain.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookRecordHistory {

    private UserDomain user;
    private BookDomain book;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
}
