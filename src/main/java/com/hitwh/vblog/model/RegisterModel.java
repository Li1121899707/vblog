package com.hitwh.vblog.model;

import com.hitwh.vblog.inparam.LabelInParam;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

public class RegisterModel {
    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String pwd;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String avatarLg;

    private String avatarMd;

    private String avatarSm;

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
