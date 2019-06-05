package com.hitwh.vblog.inparam;

import org.apache.ibatis.annotations.Param;

public class ConcernInParam {
    private String key;
    private Integer user_id;
    private Integer target_id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Integer target_id) {
        this.target_id = target_id;
    }
}
