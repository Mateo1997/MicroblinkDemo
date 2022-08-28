package com.example.microblinkdemo.util;

import com.example.microblinkdemo.exception.MicroblinkException;
import com.example.microblinkdemo.retrofit.model.MicroblinkErrorResponse;
import com.google.gson.Gson;
import retrofit2.Response;

import java.io.IOException;

import static com.example.microblinkdemo.util.ResponseConstants.ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE;

public class Helper {

    private Helper() {
        throw new IllegalStateException("Helper class");
    }

    private static final Gson gson = new Gson();

    public static <T> RuntimeException microblinkException(Response<T> response) {
        if (response != null && response.errorBody() != null) {
            String errorBody;
            try {
                errorBody = response.errorBody().string();
            } catch (IOException e) {
                return new MicroblinkException(ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE, e);
            }
            MicroblinkErrorResponse e = gson.fromJson(errorBody, MicroblinkErrorResponse.class);
            return new MicroblinkException(e.getCode());
        }
        return new MicroblinkException(ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE);
    }
}
