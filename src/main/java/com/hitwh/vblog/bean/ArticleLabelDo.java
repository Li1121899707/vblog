package com.hitwh.vblog.bean;

import java.util.Date;

public class ArticleLabelDo {
    private Integer articleId;

    private Integer labelId;

    private Date labelAddTime;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Date getLabelAddTime() {
        return labelAddTime;
    }

    public void setLabelAddTime(Date labelAddTime) {
        this.labelAddTime = labelAddTime;
    }
}
