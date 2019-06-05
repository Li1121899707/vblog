package com.hitwh.vblog.inparam;

import org.apache.ibatis.annotations.Param;

public class ConcernInParam {
    private String key;
    private Integer uid;
    private Integer target_id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Integer target_id) {
        this.target_id = target_id;
    }
}
