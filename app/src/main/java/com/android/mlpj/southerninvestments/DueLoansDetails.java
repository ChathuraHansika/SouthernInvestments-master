package com.android.mlpj.southerninvestments;



public class DueLoansDetails {
    private String Id;
    private String name;
    private String NIC;
    private Float remaining_amount;
    private Float amount;
    private String installment_count;
    private String no_of_installments;



    public DueLoansDetails(String Id, String name, String NIC, Float remaining_amount, Float amount, String installment_count, String no_of_installments) {
        this.Id = Id;
        this.name = name;
        this.NIC = NIC;
        this.remaining_amount = remaining_amount;
        this.amount = amount;
        this.installment_count = installment_count;
        this.no_of_installments = no_of_installments;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public Float getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(Float remaining_amount) {
        this.remaining_amount = remaining_amount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
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
