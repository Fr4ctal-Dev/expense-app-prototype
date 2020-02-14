package com.example.expenseprototype2.Services.ERS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateResponse {

    @SerializedName("uri")
    @Expose
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
