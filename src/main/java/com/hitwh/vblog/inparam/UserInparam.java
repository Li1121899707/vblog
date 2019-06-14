package com.hitwh.vblog.inparam;

import java.util.ArrayList;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/920:33
 */
public class UserInparam extends BaseInParam {
    private String account;

    private String phone;

    private String email;

    private Integer start;

    private Integer end;

    private Integer label_id;

    private String pwd;

    private String username;

    private String avatar_lg;

    private String avatar_md;

    private String avatar_sm;

    private String signature;

    private ArrayList<Integer> interest;

    private Integer ban_uid;

    private String nickname;

    private Integer ban;

    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getBan_uid() {
        return ban_uid;
    }

    public void setBan_uid(Integer ban_uid) {
        this.ban_uid = ban_uid;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Integer label_id) {
        this.label_id = label_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
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

    public ArrayList<Integer> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<Integer> interest) {
        this.interest = interest;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
