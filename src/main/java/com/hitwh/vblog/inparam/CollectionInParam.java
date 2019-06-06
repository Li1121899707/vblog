package com.hitwh.vblog.inparam;

/**
 * @author 臧博浩
 * @date 2019/6/5 17:25
 */
public class CollectionInParam extends BaseInParam{



    private Integer article_id;

    private Integer start;

    private Integer end;

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

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }
}
