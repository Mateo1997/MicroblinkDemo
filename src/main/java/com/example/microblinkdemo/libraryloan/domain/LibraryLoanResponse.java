package com.example.microblinkdemo.libraryloan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LibraryLoanResponse {

    private String loanNumber;
    private LocalDate dueDate;
}
