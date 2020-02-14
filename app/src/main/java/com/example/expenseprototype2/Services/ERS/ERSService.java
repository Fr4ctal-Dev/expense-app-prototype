package com.example.expenseprototype2.Services.ERS;

import com.example.expenseprototype2.Expense;

import java.util.List;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ERSService {

    @GET("/profile/v1/me")
    Single<Response<UserProfile>> getUserProfile(@Header("Authorization") String header);

    @GET
    Single<Response<Report>> getReport(@Header("Authorization") String header, @Url String url);

    @GET
    Single<Response<List<Expense>>> getExpense(@Header("Authorization") String header, @Url String url);

    @POST
    Single<Response<CreateResponse>> createExpense(@Header("Authorization") String header, @Header("Content-Type") String contentHeader, @Url String url, @Body RequestBody body);

}
