package com.example.microblinkdemo.mrz.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MrtdRequest {

    @NotBlank
    private String imageSource;
    private Boolean returnFullDocumentImage;
    private Boolean returnFaceImage;
    private Boolean returnSignatureImage;
}
