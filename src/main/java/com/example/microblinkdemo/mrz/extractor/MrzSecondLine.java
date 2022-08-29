package com.example.microblinkdemo.mrz.extractor;

import com.example.microblinkdemo.mrz.MrzConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MrzSecondLine extends AbstractMrzLineExtractor {

    private String dateOfBirth;
    private String dateOfBirthCD;
    private String gender;
    private String dateOfExpiry;
    private String dateOfExpiryCD;
    private String nationality;
    private String optionalData;
    private String overallCD;

    @Override
    public void extract(String mrzLine) {
        MrzExtractor extractor = new MrzExtractor(mrzLine);
        this.dateOfBirth = extractPart(extractor, MrzConstants.DATE_OF_BIRTH_SIZE);
        this.dateOfBirthCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        this.gender = extractPart(extractor, MrzConstants.GENDER_SIZE);
        this.dateOfExpiry = extractPart(extractor, MrzConstants.DATE_OF_EXPIRY_SIZE);
        this.dateOfExpiryCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        this.nationality = extractPart(extractor, MrzConstants.NATIONALITY_SIZE);
        this.optionalData = extractPart(extractor, MrzConstants.OPTIONAL_2_SIZE);
        this.overallCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
    }
}
