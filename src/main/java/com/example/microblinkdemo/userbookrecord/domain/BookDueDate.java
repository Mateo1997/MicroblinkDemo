package com.example.microblinkdemo.userbookrecord.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookDueDate {

    private LocalDate dueDate;
}
