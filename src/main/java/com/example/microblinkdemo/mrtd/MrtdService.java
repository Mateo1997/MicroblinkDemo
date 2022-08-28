package com.example.microblinkdemo.mrtd;

import com.example.microblinkdemo.exception.InternalServerError;
import com.example.microblinkdemo.mrtd.api.MrtdResponseExternal;
import com.example.microblinkdemo.mrtd.domain.*;
import com.example.microblinkdemo.util.Helper;
import com.example.microblinkdemo.util.ResponseConstants;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MrtdService {

    private final MrtdRepository mrtdRepository;
    private final MrzProperties mrzProperties;
    private final int modulusOfTen = 10;
    private final List<Integer> multipliers = List.of(7, 3, 1);

    public void process(MRTDRequest request) {
//        String rawMrzString = "IDCYPCR12345673<<<<<<<<<<<<<<<\n9003185M2503181CYP<<<<<<<<<<<4\nSPECIMEN<<SAMPLE<<<<<<<<<<<<<<\n";
        try {
            log.info("REQUEST RECEIVED - process");
            String rawMrzString = getRawMrzString(request);

            boolean isMrzValid;
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

            final PersonIdentity personIdentity = mapMrzData(firstPartData, secondPartData, thirdPartData, isMrzValid);
            mrtdRepository.save(personIdentity);
        } catch (Exception e) {
            log.error("REQUEST FAILED - process()", e);
            throw new InternalServerError(ResponseConstants.ERROR_INTERNAL_SERVER_ERROR);
        }
    }

    private String[] splitByNewLine(String rawMrzString) {
        return rawMrzString.split("\n");
    }

    private MrzFirstPartData extractMRZFirstPart(String mrzFirstPart) {
        MrzExtractor extractor = new MrzExtractor(mrzFirstPart);
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

    private PersonIdentity mapMrzData(MrzFirstPartData firstPart, MrzSecondPartData secondPart, MrzThirdPartData thirdPart, boolean isMrzValid) {
        return PersonIdentity.builder()
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

    private String getRawMrzString(MRTDRequest request) throws IOException {
        final Gson gson = new Gson();
        String authorization = mrzProperties.getAuthorizationToken();
        String url = mrzProperties.getMrtdUrl();
        String requestJson = Helper.asJsonString(request);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        con.setRequestProperty(HttpHeaders.ACCEPT, "application/json");
        con.setRequestProperty(HttpHeaders.AUTHORIZATION, authorization);
        con.setDoInput(true);
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = requestJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        MrtdResponseExternal responseExternal = gson.fromJson(response.toString(), MrtdResponseExternal.class);
        return responseExternal.getResult().getMrzData().getRawMrzString();
    }
}
