package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleDo;

public interface ArticleDoMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(ArticleDo record);

    int insertSelective(ArticleDo record);

    ArticleDo selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(ArticleDo record);

    int updateByPrimaryKey(ArticleDo record);
}