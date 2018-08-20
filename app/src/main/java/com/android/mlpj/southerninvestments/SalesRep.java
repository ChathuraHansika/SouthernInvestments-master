package com.android.mlpj.southerninvestments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesRep {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("profile_pic")
    private String profilePic;

    @SerializedName("NIC")
    private String nicNo;

    @SerializedName("addLine1")
    private String addLine1;

    @SerializedName("addLine2")
    private String addLine2;

    @SerializedName("city")
    private String city;

    @SerializedName("commission_id")
    private int commissionId;

    @SerializedName("calendar_id")
    private int calendarId;

    @SerializedName("role_id")
    private int roleId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;


    public SalesRep(int id, String name, String email, String profilePic, String nicNo, String addLine1, String addLine2, String city, int commissionId, int calendarId, int roleId, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.nicNo = nicNo;
        this.addLine1 = addLine1;
        this.addLine2 = addLine2;
        this.city = city;
        this.commissionId = commissionId;
        this.calendarId = calendarId;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
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

    public int getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(int commissionId) {
        this.commissionId = commissionId;
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
