package com.android.mlpj.southerninvestments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResultPOJO> login(@Field("email") String email,
                                @Field("password") String body);

    @POST("api/customerRepayments")
    @FormUrlEncoded
    Call<RepaymentDoneResponse> makeRepayment(@Field("loan_id") int loan_id,
                                @Field("cash_amount") float cash_amount,
                                @Field("bank_amount") float bank_amount,
                                @Field("cheque_no") String cheque_no);

    @POST("api/editCustomerRepayments")
    @FormUrlEncoded
    Call<RepaymentDoneResponse> editRepayment(@Field("repayment_id") int repayment_id,
                                              @Field("cash_amount") float cash_amount,
                                              @Field("repayment_date") String repayment_date,
                                              @Field("bank_amount") float bank_amount,
                                              @Field("cheque_no") String cheque_no);

    @POST("api/createPocketMoney")
    //@FormUrlEncoded
    Call<PocketMoney> addPocketMoney (@Body PocketMoney pocketMoney);

    @GET("Hotel")
    Call<List<CustomerDetails>> getDetails();

    @GET("CabDetails")
    Call<List<DueLoansDetails>> getDueLoans();
}
