package com.example.expenseprototype2.Services.TokenExchange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenInnerResponse {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("expiresIn")
    @Expose
    private Integer expiresIn;
    @SerializedName("geolocation")
    @Expose
    private String geolocation;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("tokenType")
    @Expose
    private String tokenType;
    @SerializedName("idToken")
    @Expose
    private String idToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}