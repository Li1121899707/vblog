package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.model.CollectionModel;
import com.hitwh.vblog.response.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @author 臧博浩
 * @date 2019/6/5 17:37
 */
public interface CollectionService  {

    void addCollection(CollectionModel collectionModel) throws BusinessException;

    void deleteCollection(CollectionModel collectionModel) throws BusinessException;

    Integer queryCollectionNum(CollectionModel collectionModel) throws BusinessException;

    Map<String,Object> queryCollection(Integer start, Integer num, Integer userID) throws BusinessException;

    Boolean queryIfCollect(CollectionModel collectionModel) throws BusinessException;
}
