package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.response.BusinessException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ArticleService {
    void write(ArticleModel articleModel) throws BusinessException;
    void delete(Integer article_id, Integer uid) throws BusinessException;
    void update(ArticleModel articleModel) throws BusinessException;
    ArticleOutParam queryRandomArticle() throws BusinessException;
    ArticleOutParam queryArticleId(Integer article_id) throws BusinessException;
    Map<String,Object> selectArticleByAuthorId(Integer start, Integer end, Integer userId) throws BusinessException;
    Map<String,Object> selectArticleByType(Integer start, Integer end, Integer typeId) throws BusinessException;
    Map<String,Object> selectArticleByTitle(Integer start, Integer end, String title) throws BusinessException;

    Map<String,Object> selectAllArticle(Integer start, Integer end) throws BusinessException;

    Map<String, Object> recommend(Integer num);

    Integer getArticleId(String virtualId) throws BusinessException;

    void ifHidden(Integer articleIntegerId) throws BusinessException;
}
