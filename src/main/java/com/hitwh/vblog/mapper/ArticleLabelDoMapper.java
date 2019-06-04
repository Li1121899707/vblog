package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ArticleLabelDo;
import com.hitwh.vblog.bean.ArticleLabelDoSimple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLabelDoMapper {
    List<ArticleLabelDoSimple> selectAllArticleInterest(@Param("articleId") Integer articleId);

    int insert(ArticleLabelDo articleLabelDo);

    int deleteByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(ArticleLabelDo record);
}