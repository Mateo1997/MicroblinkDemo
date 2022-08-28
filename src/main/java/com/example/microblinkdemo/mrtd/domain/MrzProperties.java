package com.example.microblinkdemo.mrtd.domain;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
@Data
public class MrzProperties {

    @Value("${base.url}")
    private String baseUrl;
    @Value("${mrtd.resource}")
    private String mrtdResource;
    @Value("${api.key}")
    private String apiKey;
    @Value("${api.secret}")
    private String apiSecret;
    @Value("${mrz.documentcode.size}")
    private Integer documentCodeSize;
    @Value("${mrz.issuer.size}")
    private Integer issuerSize;
    @Value("${mrz.documentnumber.size}")
    private Integer documentNumberSize;
    @Value("${mrz.dateofbirth.size}")
    private Integer dateOfBirthSize;
    @Value("${mrz.gender.size}")
    private Integer genderSize;
    @Value("${mrz.dateofexpiry.size}")
    private Integer dateOfExpirySize;
    @Value("${mrz.nationality.size}")
    private Integer nationalitySize;
    @Value("${mrz.checkdigit.size}")
    private Integer checkDigitSize;

    public String getAuthorizationToken() {
        String key = apiKey + ":" + apiSecret;
        String encodedKey = Base64.encodeBase64String(key.getBytes(StandardCharsets.UTF_8));
        return  "Bearer " + encodedKey;
    }

    public String getMrtdUrl() {
        return baseUrl + mrtdResource;
    }
}
