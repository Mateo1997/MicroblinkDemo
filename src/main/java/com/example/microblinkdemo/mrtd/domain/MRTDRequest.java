package com.example.microblinkdemo.mrtd.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MRTDRequest {

    @NotBlank
    private String imageSource;
    private Boolean returnFullDocumentImage;
    private Boolean returnFaceImage;
    private Boolean returnSignatureImage;
}
