package com.hitwh.vblog.bean;

import java.util.Date;

public class ThumbRecordDo {
    private Integer articleId;

    private Integer thumberId;

    private Date thumbTime;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getThumberId() {
        return thumberId;
    }

    public void setThumberId(Integer thumberId) {
        this.thumberId = thumberId;
    }

    public Date getThumbTime() {
        return thumbTime;
    }

    public void setThumbTime(Date thumbTime) {
        this.thumbTime = thumbTime;
    }
}