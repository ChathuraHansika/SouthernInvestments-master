package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class LoanDetails {

    @SerializedName("id")
    private int id;

    @SerializedName("loan_no")
    private int loanNo;

    @SerializedName("interest_rate")
    private float interestRate;

    @SerializedName("installment_amount")
    private float installmentAmount;

    @SerializedName("no_of_installments")
    private int noOfInstallments;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("duration")
    private int duration;

    @SerializedName("customer_id")
    private int customerId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public LoanDetails(int id, int loanNo, float interestRate, float installmentAmount, int noOfInstallments, String startDate, String endDate, int duration, int customerId, String createdAt, String updatedAt) {
        this.id = id;
        this.loanNo = loanNo;
        this.interestRate = interestRate;
        this.installmentAmount = installmentAmount;
        this.noOfInstallments = noOfInstallments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(int loanNo) {
        this.loanNo = loanNo;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public float getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(float installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public int getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(int noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCreatedAt() {
        return createdAt;
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
