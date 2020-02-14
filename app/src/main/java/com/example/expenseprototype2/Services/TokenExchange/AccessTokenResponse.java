package com.example.expenseprototype2.Services.TokenExchange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

    @SerializedName("token")
    @Expose
    private AccessTokenInnerResponse accessTokenInnerResponse;

    public AccessTokenInnerResponse getAccessTokenInnerResponse() {
        return accessTokenInnerResponse;
    }


}