package com.hitwh.vblog.inparam;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:18
 */
public class ReportInParam extends BaseInParam {
    public Integer article_id;
    public String reason;

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
}
