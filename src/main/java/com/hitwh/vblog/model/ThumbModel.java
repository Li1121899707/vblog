package com.hitwh.vblog.model;

import javax.validation.constraints.NotNull;

/**
 * @author 11218
 * @description: TODO
 * @date 2019/6/517:09
 */
public class ThumbModel {
    @NotNull(message = "文章Id不能为空")
    public Integer articleId;

    @NotNull(message = "用户名不能为空")
    public Integer userId;

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
}
