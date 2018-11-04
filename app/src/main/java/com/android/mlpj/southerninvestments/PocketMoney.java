package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class PocketMoney {
    @SerializedName("sales_rep_id")
    private int sales_rep_id;

    @SerializedName("amount")
    private float amount;

    @SerializedName("description")
    private String description;

    public PocketMoney(int sales_rep_id, float amount, String description) {
        this.sales_rep_id = sales_rep_id;
        this.amount = amount;
        this.description = description;
    }

    public int getSales_rep_id() {
        return sales_rep_id;
    }

    public void setSales_rep_id(int sales_rep_id) {
        this.sales_rep_id = sales_rep_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
