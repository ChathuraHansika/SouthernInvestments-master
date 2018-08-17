package com.android.mlpj.southerninvestments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/v2/5b76f8d83000004c00848d96")
    Call<LoginResultPOJO> login();

    @GET("Hotel")
    Call<List<CustomerDetails>> getDetails();

    @GET("CabDetails")
    Call<List<DueLoansDetails>> getDueLoans();
}
