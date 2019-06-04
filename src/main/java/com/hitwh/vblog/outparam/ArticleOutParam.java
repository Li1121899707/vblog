package com.hitwh.vblog.outparam;

import com.hitwh.vblog.bean.ArticleLabelDoSimple;

import java.util.List;

public class ArticleOutParam {
    private Integer article_id;

    private String virtual_id;

    private String title;

    private Integer author_id;

    private String author_nickname;

    private String cover;

    private String content;

    private String articleAbstract;

    private long release_time;

    private Integer thumb;

    private Integer reading;

    private List<ArticleLabelDoSimple> labels;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getVirtual_id() {
        return virtual_id;
    }

    public void setVirtual_id(String virtual_id) {
        this.virtual_id = virtual_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public long getRelease_time() {
        return release_time;
    }

    public void setRelease_time(long release_time) {
        this.release_time = release_time;
    }

    public Integer getThumb() {
        return thumb;
    }

    public void setThumb(Integer thumb) {
        this.thumb = thumb;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public List<ArticleLabelDoSimple> getLabels() {
        return labels;
    }

    public void setLabels(List<ArticleLabelDoSimple> labels) {
        this.labels = labels;
    }
}
