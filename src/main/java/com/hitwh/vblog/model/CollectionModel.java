package com.hitwh.vblog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 臧博浩
 * @date 2019/6/5 17:29
 */
public class CollectionModel {

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @NotNull(message = "文章ID不能为空")
    private Integer articleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
