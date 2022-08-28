package com.example.microblinkdemo.mrz.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MrzExtractor {

    @Setter
    private String mrzString;
    private String extractValue;

    public MrzExtractor(String mrzString) {
        this.mrzString = mrzString;
    }
}
