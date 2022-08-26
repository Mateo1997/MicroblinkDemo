package com.example.microblinkdemo.userbookrecord.domain;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserBookRecordRequest {

    @NotEmpty
    private List<Integer> bookRecordIds;

    @NotNull
    private Integer userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @NotNull
    private LocalDateTime borrowTime;
}
