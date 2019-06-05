package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleDynamicDo;

public interface ArticleDynamicDoMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(ArticleDynamicDo record);

    int insertSelective(ArticleDynamicDo record);

    ArticleDynamicDo selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(ArticleDynamicDo record);

    int updateByPrimaryKey(ArticleDynamicDo record);

    int addArticleDynamic(ArticleDynamicDo record);

    int subtractArticleDynamic(ArticleDynamicDo record);
}