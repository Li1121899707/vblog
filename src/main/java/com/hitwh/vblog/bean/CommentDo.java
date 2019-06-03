package com.hitwh.vblog.bean;

import java.util.Date;

public class CommentDo {
    private Integer commentId;

    private Integer articleId;

    private Integer userId;

    private String comment;

    private Date commentTime;

    private Integer parentCommentId;

    private Integer commentHide;

    public Integer getCommentHide() {
        return commentHide;
    }

    public void setCommentHide(Integer commentHide) {
        this.commentHide = commentHide;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}