package com.example.microblinkdemo.mrz.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MrzFirstLine {

    private String documentCode;
    private String issuer;
    private String documentNumber;
    private String documentNumberCD;
    private String optionalData;
}
