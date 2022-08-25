package com.example.microblinkdemo.user.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
public abstract class AbstractUser {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Past
    private LocalDate dateOfBirth;
}