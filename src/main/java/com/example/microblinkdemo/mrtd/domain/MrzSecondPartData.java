package com.example.microblinkdemo.mrtd.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MrzSecondPartData {

    private String dateOfBirth;
    private String dateOfBirthCD;
    private String gender;
    private String dateOfExpiry;
    private String dateOfExpiryCD;
    private String nationality;
    private String optionalData;
    private String overallCD;
}
