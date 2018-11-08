package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class PocketMoney {
    @SerializedName("sales_rep_id")
    private String sales_rep_id;

    @SerializedName("amount")
    private String amount;

    @SerializedName("description")
    private String description;

    public PocketMoney(String sales_rep_id, String amount, String description) {
        this.sales_rep_id = sales_rep_id;
        this.amount = amount;
        this.description = description;
    }

    public String getSales_rep_id() {
        return sales_rep_id;
    }

    public void setSales_rep_id(String sales_rep_id) {
        this.sales_rep_id = sales_rep_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
