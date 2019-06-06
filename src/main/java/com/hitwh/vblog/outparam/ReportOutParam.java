package com.hitwh.vblog.outparam;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:19
 */
public class ReportOutParam {
    private Integer article_id;

    private String virtual_id;

    private String article_name;

    private Integer reporter_id;

    private String report_nickname;

    private String reason;

    private Integer admin_id;

    private Long report_time;

    private Long handle_time;

    private Integer handle_result;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getVirtual_id() {
        return virtual_id;
    }

    public void setVirtual_id(String virtual_id) {
        this.virtual_id = virtual_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public Integer getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(Integer reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getReport_nickname() {
        return report_nickname;
    }

    public void setReport_nickname(String report_nickname) {
        this.report_nickname = report_nickname;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public Long getReport_time() {
        return report_time;
    }

    public void setReport_time(Long report_time) {
        this.report_time = report_time;
    }

    public Long getHandle_time() {
        return handle_time;
    }

    public void setHandle_time(Long handle_time) {
        this.handle_time = handle_time;
    }

    public Integer getHandle_result() {
        return handle_result;
    }

    public void setHandle_result(Integer handle_result) {
        this.handle_result = handle_result;
    }
}
