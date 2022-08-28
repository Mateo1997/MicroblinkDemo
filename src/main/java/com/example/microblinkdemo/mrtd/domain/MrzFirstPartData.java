package com.example.microblinkdemo.mrtd.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MrzFirstPartData {

    private String documentCode;
    private String issuer;
    private String documentNumber;
    private String documentNumberCD;
    private String optionalData;
}
