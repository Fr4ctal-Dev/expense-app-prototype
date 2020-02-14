package com.example.expenseprototype2.Services.TokenExchange;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccessTokenClient {

    private static Retrofit accessTokenRetrofit = null;

    public static Retrofit getAccessTokenRetrofit(String baseUrl) {
        if (accessTokenRetrofit == null) {
            accessTokenRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return accessTokenRetrofit;
    }
}
