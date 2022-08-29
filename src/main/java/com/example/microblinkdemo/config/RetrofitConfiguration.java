package com.example.microblinkdemo.config;

import com.example.microblinkdemo.mrz.properties.MrtdProperties;
import com.example.microblinkdemo.retrofit.MicroblinkAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RetrofitConfiguration {

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::info).setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient baseOkHttpClient = new OkHttpClient();

    private static final GsonConverterFactory gsonFactory = GsonConverterFactory.create();

    private final MrtdProperties mrtdProperties;

    @Bean
    public MicroblinkAPI getMicroblinkAPI() {
        OkHttpClient.Builder httpClient = baseOkHttpClient.newBuilder();

        httpClient.interceptors().clear();
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        httpClient.networkInterceptors().add(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.header(HttpHeaders.AUTHORIZATION, mrtdProperties.getAuthorizationToken());
            return chain.proceed(requestBuilder.build());
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mrtdProperties.getUrl())
                .addConverterFactory(gsonFactory)
                .client(httpClient.build())
                .build();

        return retrofit.create(MicroblinkAPI.class);
    }
}
