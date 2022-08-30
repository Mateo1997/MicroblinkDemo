package com.example.microblinkdemo.retrofit;

import com.example.microblinkdemo.exception.MicroblinkException;
import com.example.microblinkdemo.exception.ServiceUnavailableException;
import com.example.microblinkdemo.mrz.api.MrtdResponseExternal;
import com.example.microblinkdemo.mrz.domain.MrtdRequest;
import com.example.microblinkdemo.retrofit.model.MicroblinkErrorResponse;
import com.example.microblinkdemo.util.ResponseConstants;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

import static com.example.microblinkdemo.util.ResponseConstants.ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE;

@Component
@RequiredArgsConstructor
public class RetrofitHelper {

    private final MicroblinkAPI microblinkAPI;
    private static final Gson gson = new Gson();

    public String getRawMrzString(MrtdRequest request) {
        Response<MrtdResponseExternal> response;

        try {
            response = microblinkAPI.getMrzData(request).execute();
        } catch (Exception e) {
            throw new ServiceUnavailableException(ResponseConstants.ERROR_MICROBLINK_SERVICE_UNAVAILABLE, e);
        }

        if (response.isSuccessful() && response.body() != null) {
            return response.body()
                    .getResult()
                    .getMrzData()
                    .getRawMrzString();
        }

        throw microblinkException(response);
    }

    private <T> RuntimeException microblinkException(Response<T> response) {
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
