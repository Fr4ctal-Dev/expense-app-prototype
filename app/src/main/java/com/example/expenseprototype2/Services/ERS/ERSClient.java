package com.example.expenseprototype2.Services.ERS;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ERSClient {
    private static Retrofit ERSRetrofit = null;

    public static Retrofit getERSRetrofit(String baseURL) {
        if (ERSRetrofit == null) {
            ERSRetrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return ERSRetrofit;
    }
}
