package com.hitwh.vblog.inparam;

import com.hitwh.vblog.model.LabelModel;

import java.util.ArrayList;

public class RegisterInParam extends BaseInParam {
    private String account;

    private String pwd;

    private String username;

    private String email;

    private String phone;

    private String avatar_lg;

    private String avatar_md;

    private String avatar_sm;

    private ArrayList<Integer> interest;

    public ArrayList<Integer> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<Integer> interest) {
        this.interest = interest;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
