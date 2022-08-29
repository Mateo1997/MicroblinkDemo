package com.example.microblinkdemo.mrz;

import com.example.microblinkdemo.mrz.domain.MrtdRequest;
import com.example.microblinkdemo.mrz.domain.Mrz;
import com.example.microblinkdemo.mrz.extractor.MrzFirstLine;
import com.example.microblinkdemo.mrz.extractor.MrzSecondLine;
import com.example.microblinkdemo.mrz.extractor.MrzThirdLine;
import com.example.microblinkdemo.retrofit.RetrofitHelper;
import com.example.microblinkdemo.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            final String rawMrzString = retrofitHelper.getRawMrzString(request);
            final String[] mrzArray = splitByNewLine(rawMrzString);

            MrzFirstLine mrzFirstLine = new MrzFirstLine();
            mrzFirstLine.extract(mrzArray[0]);

            MrzSecondLine mrzSecondLine = new MrzSecondLine();
            mrzSecondLine.extract(mrzArray[1]);

            MrzThirdLine mrzThirdLine = new MrzThirdLine();
            mrzThirdLine.extract(mrzArray[2]);

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

    private String zeroValueFillers(String text) {
        return text.replace(MrzConstants.FILLER, MrzConstants.ZERO);
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

    private int calculate(int numValue, int index) {
        return numValue * multipliers.get(modOfThree(index));
    }

    private int modOfThree(int number) {
        return number % 3;
    }

    private String removeFillers(String text) {
        return text.replace(MrzConstants.FILLER, MrzConstants.EMPTY_STRING);
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
}
