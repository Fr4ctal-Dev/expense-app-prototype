package com.example.expenseprototype2.Services.ERS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportContent {
    @SerializedName("reportId")
    @Expose
    private String reportId;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}