package com.example.microblinkdemo.libraryloan.domain;

import com.example.microblinkdemo.user.domain.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryLoanHistory {

    private String number;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private UserDomain user;
}
