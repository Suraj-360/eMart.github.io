package com.suraj.emart.models;

public class UserModel {
    int id;
    String email,password,mobile,userName,userAuthId;

    public UserModel(int id, String email, String password, String mobile, String userName, String userAuthId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userName = userName;
        this.userAuthId = userAuthId;
    }

    public UserModel(String email, String password, String mobile, String userName, String userAuthId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userName = userName;
        this.userAuthId = userAuthId;
    }

    public UserModel(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
    }
}
