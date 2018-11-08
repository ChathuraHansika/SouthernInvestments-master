package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class PocketMoneyResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("entry")
    private PocketMoney pocketMoney;

    public PocketMoneyResponse(boolean error, PocketMoney pocketMoney) {
        this.error = error;
        this.pocketMoney = pocketMoney;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public PocketMoney getPocketMoney() {
        return pocketMoney;
    }

    public void setPocketMoney(PocketMoney pocketMoney) {
        this.pocketMoney = pocketMoney;
    }
}
