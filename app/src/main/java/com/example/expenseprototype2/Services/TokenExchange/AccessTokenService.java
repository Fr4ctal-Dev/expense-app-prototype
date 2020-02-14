package com.example.expenseprototype2.Services.TokenExchange;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccessTokenService {

    @Headers({"content-type: application/json"})
    @POST("/mobileauth/v1/token/get-by-password")
    Single<Response<AccessTokenResponse>> getToken(
            @Body RequestBody requestBody);
}
