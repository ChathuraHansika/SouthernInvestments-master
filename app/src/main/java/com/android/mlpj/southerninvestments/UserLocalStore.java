package com.android.mlpj.southerninvestments;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public  final static String spName = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(spName,0);

    }

    public void setUserDetails(SalesRep user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("id", user.getId());
        spEditor.putString("name", user.getName());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("profilePic", user.getProfilePic());
        spEditor.putString("nicNo", user.getNicNo());
        spEditor.putString("addLine1", user.getAddLine1());
        spEditor.putString("addLine2", user.getAddLine2());
        spEditor.putString("city", user.getCity());
        spEditor.putInt("commissionId", user.getCommissionId());
        spEditor.putInt("calendarId", user.getCalendarId());
        spEditor.putInt("roleId", user.getRoleId());
        spEditor.putString("createdAt", user.getCreatedAt());
        spEditor.putString("updatedAt", user.getUpdatedAt());


        spEditor.commit();
    }

    public SalesRep getUserDetails(){
        int id = userLocalDatabase.getInt("id",-1);
        String name = userLocalDatabase.getString("name","");
        String email = userLocalDatabase.getString("email","");
        String profilePic = userLocalDatabase.getString("profilePic","");
        String nicNo = userLocalDatabase.getString("nicNo","");
        String addLine1 = userLocalDatabase.getString("addLine1","");
        String addLine2 = userLocalDatabase.getString("addLine2","");
        String city = userLocalDatabase.getString("city","");
        int commissionId = userLocalDatabase.getInt("commissionId",-1);
        int calendarId = userLocalDatabase.getInt("calendarId",-1);
        int roleId = userLocalDatabase.getInt("roleId",-1);
        String createdAt = userLocalDatabase.getString("createdAt","");
        String updatedAt = userLocalDatabase.getString("updatedAt","");


        SalesRep user =new SalesRep(id, name, email, profilePic, nicNo, addLine1, addLine2, city, commissionId, calendarId, roleId, createdAt, updatedAt);
        return  user;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false))
            return true;
        else
            return false;
    }

    public void clearUser(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();

    }
}
