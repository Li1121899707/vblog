package com.hitwh.vblog.outparam;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/1119:02
 */
public class LoginOutParam {

    private Integer uid;

    private Integer allowance;

    private String token;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAllowance() {
        return allowance;
    }

    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
