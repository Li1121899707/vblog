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

    private String nickname;

    private String avatar_lg;

    private String avatar_md;

    private String avatar_sm;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
