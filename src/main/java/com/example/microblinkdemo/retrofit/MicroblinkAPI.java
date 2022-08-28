package com.example.microblinkdemo.retrofit;

import com.example.microblinkdemo.mrz.api.MrtdResponseExternal;
import com.example.microblinkdemo.mrz.domain.MrtdRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MicroblinkAPI {

    @POST("mrtd")
    Call<MrtdResponseExternal> getMrzData(@Body MrtdRequest request);
}
