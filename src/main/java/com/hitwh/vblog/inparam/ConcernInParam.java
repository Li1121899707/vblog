package com.hitwh.vblog.inparam;

import org.apache.ibatis.annotations.Param;

public class ConcernInParam extends BaseInParam {
    private Integer target_id;

    private Integer start;

    private Integer end;


    public Integer getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Integer target_id) {
        this.target_id = target_id;
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
}
