package com.android.mlpj.southerninvestments;

public class Customer {

    private int id;
    private int customer_no;
    private String name;
    private String email;
    private String NIC;
    private String contact_no;
    private String status;
    private String addLine1;
    private String addLine2;
    private String city;
    private int salesRep_id;
    private String created_at;
    private String updated_at;

    public Customer(int id, int customer_no, String name, String email, String NIC, String contact_no, String status, String addLine1, String addLine2, String city, int salesRep_id, String created_at, String updated_at) {
        this.id = id;
        this.customer_no = customer_no;
        this.name = name;
        this.email = email;
        this.NIC = NIC;
        this.contact_no = contact_no;
        this.status = status;
        this.addLine1 = addLine1;
        this.addLine2 = addLine2;
        this.city = city;
        this.salesRep_id = salesRep_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(int customer_no) {
        this.customer_no = customer_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddLine1() {
        return addLine1;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    public String getAddLine2() {
        return addLine2;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSalesRep_id() {
        return salesRep_id;
    }

    public void setSalesRep_id(int salesRep_id) {
        this.salesRep_id = salesRep_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
