package com.example.expenseprototype2.Services;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.expenseprototype2.Expense;
import com.example.expenseprototype2.ExpenseSyncState;
import com.example.expenseprototype2.Services.ERS.CreateResponse;
import com.example.expenseprototype2.Services.ERS.ERSClient;
import com.example.expenseprototype2.Services.ERS.ERSService;
import com.example.expenseprototype2.Services.ERS.Report;
import com.example.expenseprototype2.Services.ERS.UserProfile;
import com.example.expenseprototype2.Services.TokenExchange.AccessTokenClient;
import com.example.expenseprototype2.Services.TokenExchange.AccessTokenResponse;
import com.example.expenseprototype2.Services.TokenExchange.AccessTokenService;

import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;


public class ApiUtils {

    private static String TOKENEX_BASE_URL = "https://us.api.concursolutions.com/";
    private static String ERS_BASE_URL = "https://us.api.concursolutions.com/";


    MutableLiveData<String> JWT_TOKEN = new MutableLiveData<>();
    MutableLiveData<String> PROFILE_ID = new MutableLiveData<>();
    MutableLiveData<String> REPORT_ID = new MutableLiveData<>();
    MutableLiveData<List<Expense>> EXPENSES = new MutableLiveData<>();
    MutableLiveData<Integer> CREATE_RESPONSE_CODE = new MutableLiveData<>();

    private static AccessTokenService getAccessTokenService() {
        return AccessTokenClient.getAccessTokenRetrofit(TOKENEX_BASE_URL).create(AccessTokenService.class);
    }

    public static ERSService getERSService() {
        return ERSClient.getERSRetrofit(ERS_BASE_URL).create(ERSService.class);
    }

    public LiveData<Integer> postExpense(String userId, String password, final Expense expense) {
        final String[] header = {""};
        final String[] url = {""};
        final LiveData<String> reportLiveData = this.setReportId(userId, password);
        reportLiveData.observeForever(new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(String s) {
                header[0] = "Bearer " + JWT_TOKEN.getValue();
                url[0] = "/expensereports/v4/users/" + PROFILE_ID.getValue() + "/context/TRAVELER/reports/" + REPORT_ID.getValue() + "/expenses/";
                String body = "{" +
                        "\"exchangeRate\": {" +
                        "\"value\": 1.00000000000000," +
                        "\"operation\": \"MULTIPLY\"" +
                        "}," +
                        "\"expenseType\": {" +
                        "\"id\": \"" + expense.getType() + "\"" + //TODO change hardcoded type to one of available ones in https://us.api.concursolutions.com/expenseconfig/v4/expensetypes
                        "}," +
                        "\"transactionAmount\": {" +
                        "\"value\": " + expense.getAmount() + "," +
                        "\"currencyCode\": \"" + "EUR" + "\"" +
                        "}," +
                        "\"transactionDate\": \"" + java.time.LocalDate.now().toString() + "\"," +
                        "  \"expenseSource\": \"OTHER\"," +
                        "\"businessPurpose\": \"" + expense.getReason() + "\"" +
                        "}";

                RequestBody requestBody = RequestBody.create(MediaType.parse(body), body);
                final Single<Response<CreateResponse>> createERS = getERSService().createExpense(header[0], "application/json", url[0], requestBody);
                createERS.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<CreateResponse>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("APISubscription", "Connected to Creation Service");
                            }

                            @Override
                            public void onSuccess(Response<CreateResponse> createResponseResponse) {
                                CREATE_RESPONSE_CODE.setValue(createResponseResponse.code());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                reportLiveData.removeObserver(this);
            }
        });
        return CREATE_RESPONSE_CODE;
    }

    public LiveData<List<Expense>> setExpenses(String userId, String password) {
        final String[] header = {""};
        final String[] url = {""};
        final LiveData<String> reportLiveData = this.setReportId(userId, password);
        reportLiveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                header[0] = "Bearer " + JWT_TOKEN.getValue();
                url[0] = "/expensereports/v4/users/" + PROFILE_ID.getValue() +
                        "/context/TRAVELER/reports/" + REPORT_ID.getValue() + "/expenses";

                Single<Response<List<Expense>>> expenseERS = getERSService().getExpense(header[0], url[0]);
                expenseERS.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<List<Expense>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("APISubscription", "Connected to Expense Service");
                            }

                            @Override
                            public void onSuccess(Response<List<Expense>> expenseResponse) {
                                for (Expense i :
                                        expenseResponse.body()) {
                                    double amount = i.getAmountObj().getAmount();
                                    String currency = i.getAmountObj().getCurrency();
                                    String type = i.getTypeObj().getType();

                                    i.setAmount(amount);
                                    i.setCurrency(currency);
                                    i.setType(type);
                                    i.setState(ExpenseSyncState.SYNCED);
                                    i.setId(UUID.randomUUID().toString());
                                }

                                EXPENSES.setValue(expenseResponse.body());

                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
                reportLiveData.removeObserver(this);
            }
        });
        return EXPENSES;
    }

    public LiveData<String> setReportId(String userId, String password) {
        final String[] header = {""};
        final String[] url = {""};
        final LiveData<String> profileLiveData = this.setProfileId(userId, password);
        profileLiveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                header[0] = "Bearer " + JWT_TOKEN.getValue();
                url[0] = "/expensereports/v4/users/" + PROFILE_ID.getValue() + "/context/TRAVELER/reports/";

                final Single<Response<Report>> reportERS = getERSService().getReport(header[0], url[0]);
                reportERS.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<Report>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("APISubscription", "Connected to Report Service");
                            }

                            @Override
                            public void onSuccess(Response<Report> reportResponse) {
                                REPORT_ID.setValue(reportResponse.body().getContent().get(0).getReportId());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                profileLiveData.removeObserver(this);
            }
        });
        return REPORT_ID;
    }

    public LiveData<String> setProfileId(String userId, String password) {
        final String[] header = {""};
        final LiveData<String> tokenLiveData = this.setJwtToken(userId, password);
        tokenLiveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                header[0] = "Bearer " + JWT_TOKEN.getValue();
                final Single<Response<UserProfile>> profileERS = getERSService().getUserProfile(header[0]);
                profileERS.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<UserProfile>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("APISubscription", "Connected to User Profile Service");
                            }

                            @Override
                            public void onSuccess(Response<UserProfile> userProfileResponse) {
                                PROFILE_ID.setValue(userProfileResponse.body().getId());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

                tokenLiveData.removeObserver(this);
            }
        });
        return PROFILE_ID;

    }

    public LiveData<String> setJwtToken(String userId, String password) {
        String requestJson = "{ \"username\" : \"" + userId + "\"," +
                "\"password\" : \"" + password + "\"}";
        RequestBody requestBody = RequestBody.create(MediaType.parse(requestJson), requestJson);
        final Single<Response<AccessTokenResponse>> testSingle = getAccessTokenService().getToken(requestBody);
        testSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AccessTokenResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("APISubscription", "Connected to TokenExchange");
                    }

                    @Override
                    public void onSuccess(Response<AccessTokenResponse> accessTokenResponse) {
                        JWT_TOKEN.setValue(accessTokenResponse.body().getAccessTokenInnerResponse().getAccessToken());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ApiError", e.toString());
                    }
                });
        return JWT_TOKEN;

    }

}
