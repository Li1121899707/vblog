package com.hitwh.vblog.bean;

/**
 * @author 臧博浩
 * @date 2019/6/13 12:31
 */
public class ComUserArticleDo {
    private CommentDo commentDo;
    private UserDo userDo;
    private ArticleDo articleDo;

    public CommentDo getCommentDo() {
        return commentDo;
    }

    public void setCommentDo(CommentDo commentDo) {
        this.commentDo = commentDo;
    }

    public UserDo getUserDo() {
        return userDo;
    }

    public void setUserDo(UserDo userDo) {
        this.userDo = userDo;
    }

    public ArticleDo getArticleDo() {
        return articleDo;
    }

    public void setArticleDo(ArticleDo articleDo) {
        this.articleDo = articleDo;
    }
}
