package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleLabelDoSimple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLabelDoMapper {
    List<ArticleLabelDoSimple> selectAllArticleInterest(@Param("articleId") Integer articleId);
}