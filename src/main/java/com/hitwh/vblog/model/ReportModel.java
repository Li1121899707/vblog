package com.hitwh.vblog.model;

import java.util.Date;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:18
 */
public class ReportModel {
    private Integer articleId;

    private Integer reporterId;

    private String reason;

    private Integer adminId;

    private Integer handleResult;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(Integer handleResult) {
        this.handleResult = handleResult;
    }
}
