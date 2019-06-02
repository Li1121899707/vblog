package com.hitwh.vblog.model;

import javax.validation.constraints.NotBlank;

public class LoginModel {


    private Integer userId;
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @NotBlank(message = "账号不能为空")
    private String account;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
