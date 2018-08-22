package com.android.mlpj.southerninvestments;



public class DueLoansDetails {
    private String name;
    private String NIC;
    private String remaining_amount;
    private String amount;
    private String installment_count;
    private String no_of_installments;

    public DueLoansDetails(String name, String NIC, String remaining_amount, String amount, String installment_count, String no_of_installments) {
        this.name = name;
        this.NIC = NIC;
        this.remaining_amount = remaining_amount;
        this.amount = amount;
        this.installment_count = installment_count;
        this.no_of_installments = no_of_installments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(String remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInstallment_count() {
        return installment_count;
    }

    public void setInstallment_count(String installment_count) {
        this.installment_count = installment_count;
    }

    public String getNo_of_installments() {
        return no_of_installments;
    }

    public void setNo_of_installments(String no_of_installments) {
        this.no_of_installments = no_of_installments;
    }
}
