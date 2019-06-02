package com.hitwh.vblog.bean;

import java.util.Date;

public class UserDo {
    private Integer userId;

    private String account;

    private String pwd;

    private String nickname;

    private String email;

    private String phone;

    private Integer img;

    private String signature;

    private Integer interest1;

    private Integer interest2;

    private Integer interest3;

    private Integer identity;

    private Integer ban;

    private Date registerTime;

    private Integer salt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Integer getInterest1() {
        return interest1;
    }

    public void setInterest1(Integer interest1) {
        this.interest1 = interest1;
    }

    public Integer getInterest2() {
        return interest2;
    }

    public void setInterest2(Integer interest2) {
        this.interest2 = interest2;
    }

    public Integer getInterest3() {
        return interest3;
    }

    public void setInterest3(Integer interest3) {
        this.interest3 = interest3;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getSalt() {
        return salt;
    }

    public void setSalt(Integer salt) {
        this.salt = salt;
    }
}