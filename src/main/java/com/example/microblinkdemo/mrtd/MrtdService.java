package com.example.microblinkdemo.mrtd;

import com.example.microblinkdemo.mrtd.domain.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MrtdService {

    private final MrtdRepository mrtdRepository;
    private final MrzProperties mrzProperties;
    private final int modulusOfTen = 10;
    private final List<Integer> multipliers = List.of(7, 3, 1);

    public void process(MRTDRequest request) {
        String rawMrzString = "IDCYPCR12345673<<<<<<<<<<<<<<<\n9003185M2503181CYP<<<<<<<<<<<4\nSPECIMEN<<SAMPLE<<<<<<<<<<<<<<\n";
        boolean isMrzValid;

        String key = mrzProperties.getApiKey() + ":" + mrzProperties.getApiSecret();
        String encodedKey = Base64.encodeBase64String(key.getBytes(StandardCharsets.UTF_8));
        String authorization = "Baerer " + encodedKey;

        final String[] mrzArray = splitByNewLine(rawMrzString);
        final MrzFirstPartData firstPartData = extractMRZFirstPart(mrzArray[0]);
        final MrzSecondPartData secondPartData = extractMRZSecondPart(mrzArray[1]);
        final MrzThirdPartData thirdPartData = extractMRZThirdPart(mrzArray[2]);

        isMrzValid = isCheckDigitValid(firstPartData.getDocumentNumber(), firstPartData.getDocumentNumberCD()) &&
                isCheckDigitValid(secondPartData.getDateOfBirth(), secondPartData.getDateOfBirthCD()) &&
                isCheckDigitValid(secondPartData.getDateOfExpiry(), secondPartData.getDateOfExpiryCD());

        if (isMrzValid) {
            String overallCheck = zeroValueFillers(firstPartData.getDocumentNumber()) + firstPartData.getDocumentNumberCD() + zeroValueFillers(firstPartData.getOptionalData()) +
                    secondPartData.getDateOfBirth() + secondPartData.getDateOfBirthCD() + secondPartData.getDateOfExpiry() +
                    secondPartData.getDateOfExpiryCD() + zeroValueFillers(secondPartData.getOptionalData());
            isMrzValid = isCheckDigitValid(overallCheck, secondPartData.getOverallCD());
        }

        final MrzData mrzData = mapMrzData(firstPartData, secondPartData, thirdPartData, isMrzValid);
//        mrtdRepository.save(mrzData);
    }

    private String[] splitByNewLine(String rawMrzString) {
        return rawMrzString.split("\n");
    }

    private MrzFirstPartData extractMRZFirstPart(String mrzFirstPart) {
        MrzExtractor extractor = new MrzExtractor(mrzFirstPart);
//        final String documentCode = extractPart(mrzFirstPart, 0, mrzProperties.getDocumentCodeSize());
//        final String issuer = extractPart(mrzFirstPart, 2,  mrzProperties.getIssuerSize());
//        final String documentNumber = extractPart(mrzFirstPart, 5,  mrzProperties.getDocumentNumberSize());
//        final String documentNumberCD = extractPart(mrzFirstPart, 14,  mrzProperties.getCheckDigitSize());
//        final String optionalData = extractPart(mrzFirstPart, 15, null);

        final String documentCode = extractPart(extractor,  mrzProperties.getDocumentCodeSize());
        final String issuer = extractPart(extractor,  mrzProperties.getIssuerSize());
        final String documentNumber = extractPart(extractor,  mrzProperties.getDocumentNumberSize());
        final String documentNumberCD = extractPart(extractor,  mrzProperties.getCheckDigitSize());
        final String optionalData = extractPart(extractor, 15);


        return MrzFirstPartData.builder()
                .documentCode(documentCode)
                .issuer(issuer)
                .documentNumber(documentNumber)
                .documentNumberCD(documentNumberCD)
                .optionalData(optionalData)
                .build();
    }

    private MrzSecondPartData extractMRZSecondPart(String mrzSecondPart) {
        MrzExtractor extractor = new MrzExtractor(mrzSecondPart);
//        final String dateOfBirth = extractPart(mrzSecondPart, 0,  mrzProperties.getDateOfBirthSize());
//        final String dateOfBirthCD = extractPart(mrzSecondPart, 6, mrzProperties.getCheckDigitSize());
//        final String gender = extractPart(mrzSecondPart, 7, mrzProperties.getGenderSize());
//        final String dateOfExpiry = extractPart(mrzSecondPart, 8, mrzProperties.getDateOfExpirySize());
//        final String dateOfExpiryCD = extractPart(mrzSecondPart, 14, mrzProperties.getCheckDigitSize());
//        final String nationality = extractPart(mrzSecondPart, 15, mrzProperties.getNationalitySize());
//        final String optionalData = extractPart(mrzSecondPart, 18, 11);
//        final String overallCD = extractPart(mrzSecondPart, 29, mrzProperties.getCheckDigitSize());

        final String dateOfBirth = extractPart(extractor,  mrzProperties.getDateOfBirthSize());
        final String dateOfBirthCD = extractPart(extractor, mrzProperties.getCheckDigitSize());
        final String gender = extractPart(extractor, mrzProperties.getGenderSize());
        final String dateOfExpiry = extractPart(extractor, mrzProperties.getDateOfExpirySize());
        final String dateOfExpiryCD = extractPart(extractor, mrzProperties.getCheckDigitSize());
        final String nationality = extractPart(extractor, mrzProperties.getNationalitySize());
        final String optionalData = extractPart(extractor, 11);
        final String overallCD = extractPart(extractor, mrzProperties.getCheckDigitSize());

        return MrzSecondPartData.builder()
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

    private MrzThirdPartData extractMRZThirdPart(String mrzThirdPart) {
        final String[] thirdPartArray = mrzThirdPart.split(MrzCharacters.DOUBLE_FILLER);
        final String primaryId = getIdentifier(thirdPartArray[0]);
        final String secondaryId = getIdentifier(thirdPartArray[1]);
        return new MrzThirdPartData(primaryId, secondaryId);
    }

    private String getIdentifier(String identifier) {
        return String.join(MrzCharacters.SPACE, splitBySingleFiller(identifier));
    }

    private List<String> splitBySingleFiller(String identifier) {
        return Arrays.stream(identifier.split(MrzCharacters.FILLER)).toList();
    }

    private MrzData mapMrzData(MrzFirstPartData firstPart, MrzSecondPartData secondPart, MrzThirdPartData thirdPart, boolean isMrzValid) {
        return MrzData.builder()
                .documentCode(removeFillers(firstPart.getDocumentCode()))
                .issuer(firstPart.getIssuer())
                .documentNumber(removeFillers(firstPart.getDocumentNumber()))
                .optionalData1(removeFillers(firstPart.getOptionalData()))
                .dateOfBirth(secondPart.getDateOfBirth())
                .gender(secondPart.getGender())
                .dateOfExpiry(secondPart.getDateOfExpiry())
                .nationality(secondPart.getNationality())
                .optionalData2(removeFillers(secondPart.getOptionalData()))
                .primaryId(thirdPart.getPrimaryId())
                .secondaryId(thirdPart.getSecondaryId())
                .valid(isMrzValid)
                .build();
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
                totalValue += calculate(convertAlphabeticAsciiValToInt(dataElement.charAt(i)), i);
            else
                totalValue += calculate(convertCharValueToInt(dataElement.charAt(i)), i);
        }
        return totalValue % modulusOfTen;
    }

    private int convertCharValueToInt(char number) {
        return number - '0';
    }

    //ASCII code A = 65 -> 65 - 55 = 10
    private int convertAlphabeticAsciiValToInt(char letter) {
        return letter - 55;
    }

    private Integer calculate(Integer numValue, Integer index) {
        return numValue * multipliers.get(modOfThree(index));
    }

    private Integer modOfThree(Integer number) {
        return number % 3;
    }

    private String removeFillers(String text) {
        return text.replace(MrzCharacters.FILLER, MrzCharacters.EMPTY_STRING);
    }

    private String zeroValueFillers(String text) {
        return text.replace(MrzCharacters.FILLER, MrzCharacters.ZERO);
    }

//    private String extractPart(String rawMrzString, Integer prevIndex, Integer elementSize) {
//        return rawMrzString.substring(prevIndex, prevIndex + elementSize);
//    }
}
