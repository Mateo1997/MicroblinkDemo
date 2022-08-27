package com.example.microblinkdemo.libraryloan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookDueDate {

    private LocalDate dueDate;
}
