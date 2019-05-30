package com.hitwh.vblog.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

// 领域模型， 用于事务处理，包含了参数的验证，拼接分散在不同数据库表中的字段
public class DemoModel {

    private Integer userId;

    @NotBlank(message = "姓名不能为空")
    private String userName;

    private Date userRegistTime;

    //@Min(value=0, message="年龄必须大于0")
    //@Max(value=0, message="年龄必须小于150")

    @NotNull(message = "描述不可以为null")
    private String userDescription;

    @NotBlank(message = "密码不能为空")
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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
