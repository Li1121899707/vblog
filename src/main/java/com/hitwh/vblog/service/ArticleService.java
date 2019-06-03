package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.response.BusinessException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ArticleService {
    void write(ArticleModel articleModel) throws BusinessException;
    void delete(Integer article_id) throws BusinessException;
    void update(ArticleModel articleModel) throws BusinessException;
    ArticleOutParam queryArticleId(Integer article_id) throws BusinessException;
    Map<String,Object> selectArticleById(@Param("start") Integer start,
                                                @Param("num") Integer num,
                                                @Param("userId")Integer userId);
    Map<String,Object> selectArticleByType(@Param("start") Integer start,
                                         @Param("num") Integer num,
                                         @Param("typeId")Integer typeId);
    Map<String,Object> selectArticleByTitle(@Param("start") Integer start,
                                         @Param("num") Integer num,
                                         @Param("title")String title);
}
