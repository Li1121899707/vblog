package com.hitwh.vblog.bean;

public class DemoUserPwdDo {
    private Integer demoUserPwdId;

    private String userPwd;

    private Integer userId;

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
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}