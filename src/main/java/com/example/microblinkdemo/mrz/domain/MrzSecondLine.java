package com.example.microblinkdemo.mrz.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MrzSecondLine {

    private String dateOfBirth;
    private String dateOfBirthCD;
    private String gender;
    private String dateOfExpiry;
    private String dateOfExpiryCD;
    private String nationality;
    private String optionalData;
    private String overallCD;
}
