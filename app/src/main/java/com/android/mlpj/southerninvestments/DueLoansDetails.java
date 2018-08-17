package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class DueLoansDetails {
    @SerializedName("userName")
    private String Name;
    @SerializedName("email")
    private String NICNumber;


    private String Payment_Amount;
    private String Elapse_Time;

    public DueLoansDetails(String name, String NICNumber, String payment_Amount, String elapse_Time) {
        Name = name;
        this.NICNumber = NICNumber;
        Payment_Amount = payment_Amount;
        Elapse_Time = elapse_Time;
    }

    public String getName() {
        return Name;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public String getPayment_Amount() {
        return Payment_Amount;
    }

    public String getElapse_Time() {
        return Elapse_Time;
    }
}
