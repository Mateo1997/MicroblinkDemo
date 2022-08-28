package com.example.microblinkdemo.mrtd.domain;

import lombok.Data;

@Data
public class MrzExtractor {

    private String mrzString;
    private String extractValue;

    public MrzExtractor(String mrzString) {
        this.mrzString = mrzString;
    }
}
