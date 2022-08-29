package com.example.microblinkdemo.retrofit;

import com.example.microblinkdemo.exception.ServiceUnavailableException;
import com.example.microblinkdemo.mrz.api.MrtdResponseExternal;
import com.example.microblinkdemo.mrz.domain.MrtdRequest;
import com.example.microblinkdemo.util.Helper;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retrofit2.Response;

@Component
@RequiredArgsConstructor
public class RetrofitHelper {

    private final MicroblinkAPI microblinkAPI;

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

        throw Helper.microblinkException(response);
    }
}
