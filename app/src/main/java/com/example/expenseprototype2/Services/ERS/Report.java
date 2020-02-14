package com.example.expenseprototype2.Services.ERS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report {

    @SerializedName("content")
    @Expose
    private List<ReportContent> content = null;

    public List<ReportContent> getContent() {
        return content;
    }

    public void setContent(List<ReportContent> content) {
        this.content = content;
    }

}