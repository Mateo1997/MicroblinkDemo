package com.example.microblinkdemo.userbookrecord.domain;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class UserBookRecordRequest {

    @NotEmpty
    private List<Integer> bookRecordIds;

    @NotNull
    private Integer userId;

    @NotNull
    private LocalDate borrowDate;
}
