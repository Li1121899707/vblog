package com.hitwh.vblog.inparam;

public class ArticleInParam extends BaseInParam {
    private String key;

    private Integer uid;

    private String title;

    private Integer type_1;

    private Integer type_2;

    private Integer cover;

    private String content;

    private Integer hidden;

    private String articleAbstract;

    private Integer article_id;

    private Integer start;

    private Integer end;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType_1() {
        return type_1;
    }

    public void setType_1(Integer type_1) {
        this.type_1 = type_1;
    }

    public Integer getType_2() {
        return type_2;
    }

    public void setType_2(Integer type_2) {
        this.type_2 = type_2;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
