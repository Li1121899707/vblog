package com.hitwh.vblog.bean;

public class ArticleAndUserDo {
    private ArticleDo articleDo;
    private ArticleDynamicDo articleDynamicDo;
    private UserDo userDo;

    public ArticleDo getArticleDo() {
        return articleDo;
    }

    public void setArticleDo(ArticleDo articleDo) {
        this.articleDo = articleDo;
    }

    public ArticleDynamicDo getArticleDynamicDo() {
        return articleDynamicDo;
    }

    public void setArticleDynamicDo(ArticleDynamicDo articleDynamicDo) {
        this.articleDynamicDo = articleDynamicDo;
    }

    public UserDo getUserDo() {
        return userDo;
    }

    public void setUserDo(UserDo userDo) {
        this.userDo = userDo;
    }
}
