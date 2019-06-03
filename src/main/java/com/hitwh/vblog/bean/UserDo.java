package com.hitwh.vblog.bean;

import java.util.Date;

public class UserDo {
    private Integer userId;

    private String account;

    private String pwd;

    private String nickname;

    private String email;

    private String phone;

    private String signature;

    private Integer interest1;

    private Integer interest2;

    private Integer interest3;

    private Integer allowance;

    private Integer ban;

    private Date registerTime;

    private Integer salt;

    private String avatarLg;

    private String avatarMd;

    private String avatarSm;

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
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public Integer getAllowance() {
        return allowance;
    }

    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
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

    public String getAvatarLg() {
        return avatarLg;
    }

    public void setAvatarLg(String avatarLg) {
        this.avatarLg = avatarLg;
    }

    public String getAvatarMd() {
        return avatarMd;
    }

    public void setAvatarMd(String avatarMd) {
        this.avatarMd = avatarMd;
    }

    public String getAvatarSm() {
        return avatarSm;
    }

    public void setAvatarSm(String avatarSm) {
        this.avatarSm = avatarSm;
    }
}