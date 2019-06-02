package com.hitwh.vblog.service;

import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.response.BusinessException;

public interface ArticleService {
    void write(ArticleModel articleModel) throws BusinessException;
    void delete(Integer article_id) throws BusinessException;
}
