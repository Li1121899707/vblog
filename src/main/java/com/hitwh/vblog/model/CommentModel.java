package com.hitwh.vblog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommentModel {

    private Integer commentId;
    @NotNull(message = "文章ID不能为空")
    private Integer articleId;
    @NotNull(message = "评论者ID不能为空")
    private Integer userId;
    @NotBlank(message = "评论不能为空")
    private String comment;
    private Date commentTime;
    private Integer parentCommentId;

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
