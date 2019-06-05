package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.ArticleDynamicDo;
import com.hitwh.vblog.bean.CollectionRecordDo;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.CollectionRecordDoMapper;
import com.hitwh.vblog.model.CollectionModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.CollectionService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 臧博浩
 * @date 2019/6/5 17:37
 */

/**
 * 收藏Service
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    private   ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();

    @Autowired
    ValidatorImpl validator;

    @Autowired
    CollectionRecordDoMapper collectionRecordDoMapper;

    @Autowired
    ArticleDynamicDoMapper articleDynamicDoMapper;

    @Autowired
    ArticleDoMapper articleDoMapper;

    @Autowired
    ArticleServiceImpl articleService;

//添加收藏
    public void addCollection(CollectionModel collectionModel) throws BusinessException {
        //判断传参是否正确
        if(collectionModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(collectionModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CollectionRecordDo collectionRecordDo = new CollectionRecordDo();
        collectionRecordDo.setArticleId(collectionModel.getArticleId());
        collectionRecordDo.setCollectorId(collectionModel.getUserId());
        collectionRecordDo.setCollectTime(new Date(System.currentTimeMillis()));
        //添加收藏信息
        collectionRecordDoMapper.insertSelective(collectionRecordDo);
        //给文章添加收藏数加1
        articleDynamicDo.setArticleId(collectionModel.getArticleId());
        articleDynamicDo.setCollectionNum(1);
        //判断收藏数加1是否成功
        Integer insert = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);
        if(insert != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }

    @Override
    public void deleteCollection(CollectionModel collectionModel) throws BusinessException {

        if(collectionModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(collectionModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CollectionRecordDo collectionRecordDo = new CollectionRecordDo();
        collectionRecordDo.setArticleId(collectionModel.getArticleId());
        collectionRecordDo.setCollectorId(collectionModel.getUserId());

        collectionRecordDoMapper.delete(collectionRecordDo);

        articleDynamicDo.setArticleId(collectionModel.getArticleId());
        articleDynamicDo.setCollectionNum(1);

        Integer insert = articleDynamicDoMapper.subtractArticleDynamic(articleDynamicDo);
        if(insert != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }

    @Override
    public Integer queryCollectionNum(CollectionModel collectionModel) throws BusinessException {

        if(collectionModel == null || collectionModel.getArticleId() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ArticleDynamicDo FromTable =  articleDynamicDoMapper.selectByPrimaryKey(
                collectionModel.getArticleId());

        return FromTable.getCollectionNum();
    }

    @Override
    public Map<String,Object> queryCollection(Integer start, Integer num, Integer userID) throws BusinessException {

        Map<String,Object> map = new HashMap<>();

        if(start < 0 || num <= 0 || userID <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        List<ArticleAndUserDo> articleAndUserDo =
                articleDoMapper.queryCollectionArticleByUser(start,num,userID);


        int sum = collectionRecordDoMapper.selectNumByUserId(userID);

        map.put("list",articleService.convertToArticleOutParams(articleAndUserDo));
        map.put("sum",sum);

        return map;
    }

    @Override
    public Boolean queryIfCollect(CollectionModel collectionModel) throws BusinessException {

        if(collectionModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(collectionModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CollectionRecordDo collectionRecordDo = collectionRecordDoMapper.selectIfCollected(collectionModel.getUserId(),
                collectionModel.getArticleId());

        if(collectionRecordDo == null)
            return false;
        else
            return true;
    }
}
