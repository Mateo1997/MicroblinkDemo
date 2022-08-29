package com.example.microblinkdemo.mrz.properties;

import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
@Getter
public class MrtdProperties {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String apiSecret;

    public String getAuthorizationToken() {
        String key = apiKey + ":" + apiSecret;
        return "Bearer " + Base64.encodeBase64String(key.getBytes(StandardCharsets.UTF_8));
    }

    public String getUrl() {
        return baseUrl + resourcePath;
    }
}
