package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResultPOJO {

    @SerializedName("error")
    private boolean error;

    @SerializedName("user")
    private SalesRep user;

    @SerializedName("customers")
    private List<Customer> customers;

    @SerializedName("loans")
    private List<LoanDetails> loans;

    @SerializedName("repayments")
    private List<Repayment> repayments;

    public LoginResultPOJO(boolean error, SalesRep user, List<Customer> customers, List<LoanDetails> loans, List<Repayment> repayments) {
        this.error = error;
        this.user = user;
        this.customers = customers;
        this.loans = loans;
        this.repayments = repayments;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public SalesRep getUser() {
        return user;
    }

    public void setUser(SalesRep user) {
        this.user = user;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<LoanDetails> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDetails> loans) {
        this.loans = loans;
    }

    public List<Repayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<Repayment> repayments) {
        this.repayments = repayments;
    }
}
