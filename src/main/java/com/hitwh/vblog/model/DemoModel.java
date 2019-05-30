package com.hitwh.vblog.model;

import java.util.Date;

// 领域模型
public class DemoModel {
    private Integer userId;

    private String userName;

    private Date userRegistTime;

    private String userDescription;

    private Integer demoUserPwdId;

    private String userPwd;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserRegistTime() {
        return userRegistTime;
    }

    public void setUserRegistTime(Date userRegistTime) {
        this.userRegistTime = userRegistTime;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Integer getDemoUserPwdId() {
        return demoUserPwdId;
    }

    public void setDemoUserPwdId(Integer demoUserPwdId) {
        this.demoUserPwdId = demoUserPwdId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
