package com.example.microblinkdemo.libraryloan.domain;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class LibraryLoanRequest {

    @NotEmpty
    private List<Integer> bookCopyIds;

    @NotNull
    private Integer userId;

    @NotNull
    private LocalDate borrowDate;
}
