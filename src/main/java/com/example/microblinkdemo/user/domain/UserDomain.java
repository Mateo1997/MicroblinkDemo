package com.example.microblinkdemo.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserDomain {

    private Integer id;
    private String serialNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
