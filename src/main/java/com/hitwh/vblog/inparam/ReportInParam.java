package com.hitwh.vblog.inparam;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:18
 */
public class ReportInParam extends BaseInParam {
    private Integer article_id;

    private String reason;

    private Integer start;

    private Integer end;

    private Integer handle_result;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Integer getHandle_result() {
        return handle_result;
    }

    public void setHandle_result(Integer handle_result) {
        this.handle_result = handle_result;
    }
}
