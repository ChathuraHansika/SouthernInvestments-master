package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

public class CustomerDetails {

    private String name;
    private String customerNo;
    private String status;
    private String phoneNo;

    public CustomerDetails(String name, String customerNo, String status, String phoneNo) {
        this.name = name;
        this.customerNo = customerNo;
        this.status = status;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

