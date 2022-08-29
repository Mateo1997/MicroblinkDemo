package com.example.microblinkdemo.mrz;

import com.example.microblinkdemo.mrz.domain.*;
import com.example.microblinkdemo.retrofit.RetrofitHelper;
import com.example.microblinkdemo.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MrzService {

    private final MrzRepository mrzRepository;
    private final RetrofitHelper retrofitHelper;
    private static final int MODULUS_OF_TEN = 10;
    private static final List<Integer> multipliers = List.of(7, 3, 1);

    public void parseMrzData(MrtdRequest request) {
        try {
            log.info("REQUEST RECEIVED - parseMrzData;");
            String rawMrzString = retrofitHelper.getRawMrzString(request);

            final String[] mrzArray = splitByNewLine(rawMrzString);
            final MrzFirstLine mrzFirstLine = extractMRZFirstLine(mrzArray[0]);
            final MrzSecondLine mrzSecondLine = extractMRZSecondLine(mrzArray[1]);
            final MrzThirdLine mrzThirdLine = extractMRZThirdLine(mrzArray[2]);

            boolean isMrzValid = isCheckDigitValid(zeroValueFillers(mrzFirstLine.getDocumentNumber()), mrzFirstLine.getDocumentNumberCD()) &&
                    isCheckDigitValid(mrzSecondLine.getDateOfBirth(), mrzSecondLine.getDateOfBirthCD()) &&
                    isCheckDigitValid(mrzSecondLine.getDateOfExpiry(), mrzSecondLine.getDateOfExpiryCD());

            if (isMrzValid) {
                String overallDigits = zeroValueFillers(mrzFirstLine.getDocumentNumber()) + mrzFirstLine.getDocumentNumberCD() + zeroValueFillers(mrzFirstLine.getOptionalData()) +
                        mrzSecondLine.getDateOfBirth() + mrzSecondLine.getDateOfBirthCD() + mrzSecondLine.getDateOfExpiry() +
                        mrzSecondLine.getDateOfExpiryCD() + zeroValueFillers(mrzSecondLine.getOptionalData());
                isMrzValid = isCheckDigitValid(overallDigits, mrzSecondLine.getOverallCD());
            }

            final Mrz mrz = mapMrzData(mrzFirstLine, mrzSecondLine, mrzThirdLine, isMrzValid);
            mrzRepository.save(mrz);
            log.info("REQUEST SUCCESSFULLY PROCESSED - parseMrzData;");
        } catch (Exception e) {
            log.error("REQUEST FAILED - parseMrzData;", e);
            ExceptionHandler.handleException(e);
        }
    }

    private String[] splitByNewLine(String rawMrzString) {
        return rawMrzString.split("\n");
    }

    private MrzFirstLine extractMRZFirstLine(String mrzFirstPart) {
        MrzExtractor extractor = new MrzExtractor(mrzFirstPart);
        final String documentCode = extractPart(extractor, MrzConstants.DOCUMENT_CODE_SIZE);
        final String issuer = extractPart(extractor, MrzConstants.ISSUER_SIZE);
        final String documentNumber = extractPart(extractor, MrzConstants.DOCUMENT_NUMBER_SIZE);
        final String documentNumberCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        final String optionalData = extractPart(extractor, MrzConstants.OPTIONAL_1_SIZE);

        return MrzFirstLine.builder()
                .documentCode(documentCode)
                .issuer(issuer)
                .documentNumber(documentNumber)
                .documentNumberCD(documentNumberCD)
                .optionalData(optionalData)
                .build();
    }

    private MrzSecondLine extractMRZSecondLine(String mrzSecondPart) {
        MrzExtractor extractor = new MrzExtractor(mrzSecondPart);
        final String dateOfBirth = extractPart(extractor, MrzConstants.DATE_OF_BIRTH_SIZE);
        final String dateOfBirthCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        final String gender = extractPart(extractor, MrzConstants.GENDER_SIZE);
        final String dateOfExpiry = extractPart(extractor, MrzConstants.DATE_OF_EXPIRY_SIZE);
        final String dateOfExpiryCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);
        final String nationality = extractPart(extractor, MrzConstants.NATIONALITY_SIZE);
        final String optionalData = extractPart(extractor, MrzConstants.OPTIONAL_2_SIZE);
        final String overallCD = extractPart(extractor, MrzConstants.CHECK_DIGIT_SIZE);

        return MrzSecondLine.builder()
                .dateOfBirth(dateOfBirth)
                .dateOfBirthCD(dateOfBirthCD)
                .gender(gender)
                .dateOfExpiry(dateOfExpiry)
                .dateOfExpiryCD(dateOfExpiryCD)
                .nationality(nationality)
                .optionalData(optionalData)
                .overallCD(overallCD)
                .build();
    }

    private MrzThirdLine extractMRZThirdLine(String mrzThirdPart) {
        final String[] mrzThirdPartArray = mrzThirdPart.split(MrzConstants.DOUBLE_FILLER);
        final String primaryId = getIdentifier(mrzThirdPartArray[0]);
        final String secondaryId = getIdentifier(mrzThirdPartArray[1]);
        return new MrzThirdLine(primaryId, secondaryId);
    }

    private String getIdentifier(String identifier) {
        return String.join(MrzConstants.SPACE, splitBySingleFiller(identifier));
    }

    private List<String> splitBySingleFiller(String identifier) {
        return Arrays.stream(identifier.split(MrzConstants.FILLER)).toList();
    }

    private String zeroValueFillers(String text) {
        return text.replace(MrzConstants.FILLER, MrzConstants.ZERO);
    }

    private String extractPart(MrzExtractor extractor, Integer elementSize) {
        final String extractPart = extractor.getMrzString().substring(0, elementSize);
        extractor.setMrzString(extractor.getMrzString().substring(elementSize));
        return extractPart;
    }

    private boolean isCheckDigitValid(String dataElement, String checkDigit) {
        return Integer.valueOf(checkDigit).equals(remainder(dataElement));
    }

    private Integer remainder(String dataElement) {
        int totalValue = 0;
        for (int i = 0; i < dataElement.length(); i++) {
            if (Character.isLetter(dataElement.charAt(i)))
                totalValue += calculate(convertAlphabeticCharToInt(dataElement.charAt(i)), i);
            else
                totalValue += calculate(convertCharValueToInt(dataElement.charAt(i)), i);
        }
        return totalValue % MODULUS_OF_TEN;
    }

    private int convertCharValueToInt(char number) {
        return number - '0';
    }

    //ASCII code A = 65 -> 65 - 55 = 10
    private int convertAlphabeticCharToInt(char letter) {
        return letter - 55;
    }

    private Integer calculate(Integer numValue, Integer index) {
        return numValue * multipliers.get(modOfThree(index));
    }

    private Integer modOfThree(Integer number) {
        return number % 3;
    }

    private Mrz mapMrzData(MrzFirstLine firstLine, MrzSecondLine secondLine, MrzThirdLine thirdLine, boolean isMrzValid) {
        return Mrz.builder()
                .documentCode(removeFillers(firstLine.getDocumentCode()))
                .issuer(firstLine.getIssuer())
                .documentNumber(removeFillers(firstLine.getDocumentNumber()))
                .optionalData1(removeFillers(firstLine.getOptionalData()))
                .dateOfBirth(secondLine.getDateOfBirth())
                .gender(secondLine.getGender())
                .dateOfExpiry(secondLine.getDateOfExpiry())
                .nationality(secondLine.getNationality())
                .optionalData2(removeFillers(secondLine.getOptionalData()))
                .primaryId(thirdLine.getPrimaryId())
                .secondaryId(thirdLine.getSecondaryId())
                .valid(isMrzValid)
                .build();
    }

    private String removeFillers(String text) {
        return text.replace(MrzConstants.FILLER, MrzConstants.EMPTY_STRING);
    }
}
