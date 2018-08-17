package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class LoginResultPOJO {

    @SerializedName("error")
    private boolean error;

    @SerializedName("user")
    private SalesRep user;

    public LoginResultPOJO(boolean error, SalesRep user) {
        this.error = error;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public SalesRep getUser() {
        return user;
    }
}
