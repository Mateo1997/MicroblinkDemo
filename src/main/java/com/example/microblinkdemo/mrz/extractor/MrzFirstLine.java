package com.example.microblinkdemo.mrz.extractor;

import com.example.microblinkdemo.mrz.MrzConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MrzFirstLine extends AbstractMrzLineExtractor {

    private String documentCode;
    private String issuer;
    private String documentNumber;
    private String documentNumberCD;
    private String optionalData;

    @Override
    public void extract(String mrzLine) {
        MrzExtractor extractor = new MrzExtractor(mrzLine);
        this.documentCode = extractPart(extractor, MrzConstants.DOCUMENT_CODE_SIZE);
        this.issuer = extractPart(extractor, MrzConstants.ISSUER_SIZE);
        this.documentNumber = extractPart(extractor, MrzConstants.DOCUMENT_NUMBER_SIZE);
        this. documentNumberCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        this.optionalData = extractPart(extractor, MrzConstants.OPTIONAL_1_SIZE);
    }
}
