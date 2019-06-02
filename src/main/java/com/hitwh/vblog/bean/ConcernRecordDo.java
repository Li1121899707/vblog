package com.hitwh.vblog.bean;

import java.util.Date;

public class ConcernRecordDo {
    private Integer targetId;

    private Integer followerId;

    private Date concernTime;

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Date getConcernTime() {
        return concernTime;
    }

    public void setConcernTime(Date concernTime) {
        this.concernTime = concernTime;
    }
}