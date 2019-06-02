package com.hitwh.vblog.bean;

public class ArticleDynamicDo {
    private Integer articleId;

    private String virtualId;

    private Integer readingNum;

    private Integer thumbNum;

    private Integer collectionNum;

    private Integer commentNum;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId == null ? null : virtualId.trim();
    }

    public Integer getReadingNum() {
        return readingNum;
    }

    public void setReadingNum(Integer readingNum) {
        this.readingNum = readingNum;
    }

    public Integer getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(Integer thumbNum) {
        this.thumbNum = thumbNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}