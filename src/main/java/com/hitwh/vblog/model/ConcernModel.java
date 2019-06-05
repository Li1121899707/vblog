package com.hitwh.vblog.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ConcernModel {
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    @NotNull(message = "目标用户ID不能为空")
    private Integer targetId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

}
