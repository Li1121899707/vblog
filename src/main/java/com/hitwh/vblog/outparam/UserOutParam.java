package com.hitwh.vblog.outparam;

import com.hitwh.vblog.bean.UserInterestDoOut;

import java.util.ArrayList;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/920:34
 */
public class UserOutParam {
    private Integer user_id;

    private String account;

    private String nickname;

    private String email;

    private String phone;

    private String avatar_lg;

    private String avatar_md;

    private String avatar_sm;

    private String signature;

    private ArrayList<UserInterestDoOut> interest;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getAvatar_lg() {
        return avatar_lg;
    }

    public void setAvatar_lg(String avatar_lg) {
        this.avatar_lg = avatar_lg;
    }

    public String getAvatar_md() {
        return avatar_md;
    }

    public void setAvatar_md(String avatar_md) {
        this.avatar_md = avatar_md;
    }

    public String getAvatar_sm() {
        return avatar_sm;
    }

    public void setAvatar_sm(String avatar_sm) {
        this.avatar_sm = avatar_sm;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ArrayList<UserInterestDoOut> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<UserInterestDoOut> interest) {
        this.interest = interest;
    }
}
