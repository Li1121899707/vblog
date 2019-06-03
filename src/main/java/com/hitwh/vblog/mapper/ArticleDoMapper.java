package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.ArticleDo;
import com.hitwh.vblog.outparam.ArticleOutParam;

public interface ArticleDoMapper {
    ArticleAndUserDo selectSingleArticle(Integer articleId);

    int deleteByPrimaryKey(Integer articleId);

    int insert(ArticleDo record);

    int insertSelective(ArticleDo record);

    ArticleDo selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(ArticleDo record);

    int updateByPrimaryKey(ArticleDo record);
}