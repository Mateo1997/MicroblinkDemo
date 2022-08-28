package com.example.microblinkdemo.mrtd.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MRTDRequest {

    @NotBlank
    private String imageSource;
    private Boolean returnFullDocumentImage;
    private Boolean returnFaceImage;
    private Boolean returnSignatureImage;
}
