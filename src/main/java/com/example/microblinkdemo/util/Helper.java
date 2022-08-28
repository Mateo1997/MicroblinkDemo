package com.example.microblinkdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Helper {

    private Helper() {
        throw new IllegalStateException("Helper class");
    }

    public static String asJsonString(final Object obj) {
        try {
            if (obj != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                return mapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
