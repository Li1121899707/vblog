package com.hitwh.vblog.bean;

public class ArticleAndUserDo {
    private ArticleDo articleDo;
    private ArticleDynamicDo articleDynamicDo;
    private UserDo userDo;
    private LabelDo labelDo;
    private ResourceDo resourceDo;

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

    public LabelDo getLabelDo() {
        return labelDo;
    }

    public void setLabelDo(LabelDo labelDo) {
        this.labelDo = labelDo;
    }

    public ResourceDo getResourceDo() {
        return resourceDo;
    }

    public void setResourceDo(ResourceDo resourceDo) {
        this.resourceDo = resourceDo;
    }
}
