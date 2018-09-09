package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class Repayment {

    @SerializedName("id")
    private int id;

    @SerializedName("loan_id")
    private int loanId;

    @SerializedName("bank_book_id")
    private int bankBookId;

    @SerializedName("cash_book_id")
    private int cashBookId;

    @SerializedName("amount")
    private float amount;

    @SerializedName("installment_count")
    private int installmentCount;

    @SerializedName("remaining_amount")
    private float remainingAmount;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Repayment(int id, int loanId, int bankBookId, int cashBookId, float amount, int installmentCount, float remainingAmount, String createdAt, String updatedAt) {
        this.id = id;
        this.loanId = loanId;
        this.bankBookId = bankBookId;
        this.cashBookId = cashBookId;
        this.amount = amount;
        this.installmentCount = installmentCount;
        this.remainingAmount = remainingAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBankBookId() {
        return bankBookId;
    }

    public void setBankBookId(int bankBookId) {
        this.bankBookId = bankBookId;
    }

    public int getCashBookId() {
        return cashBookId;
    }

    public void setCashBookId(int cashBookId) {
        this.cashBookId = cashBookId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getInstallmentCount() {
        return installmentCount;
    }

    public void setInstallmentCount(int installmentCount) {
        this.installmentCount = installmentCount;
    }

    public float getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(float remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getCreatedAt() {
        String[] parts = createdAt.split(" ");
        return parts[0];
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
