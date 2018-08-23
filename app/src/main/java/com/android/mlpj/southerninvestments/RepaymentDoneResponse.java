package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class RepaymentDoneResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("repayments")
    private Repayment repayments;

    public RepaymentDoneResponse(boolean error, Repayment repayments) {
        this.error = error;
        this.repayments = repayments;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Repayment getRepayments() {
        return repayments;
    }

    public void setRepayments(Repayment repayments) {
        this.repayments = repayments;
    }
}
