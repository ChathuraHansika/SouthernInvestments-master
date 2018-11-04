package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class PocketMoney {
    @SerializedName("sales_rep_id")
    private int sales_rep_id;

    @SerializedName("amount")
    private float amount;

    @SerializedName("description")
    private String description;
}
