package com.android.mlpj.southerninvestments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/v2/5b75bd552e0000620053620a")
    Call<LoginResultPOJO> login();

    @GET("Hotel")
    Call<List<CustomerDetails>> getDetails();

    @GET("CabDetails")
    Call<List<DueLoansDetails>> getDueLoans();
}
