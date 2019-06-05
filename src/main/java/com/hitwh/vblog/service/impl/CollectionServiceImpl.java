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
@Service
public class CollectionServiceImpl implements CollectionService {

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

    public void addCollection(CollectionModel collectionModel) throws BusinessException {

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

        collectionRecordDoMapper.insertSelective(collectionRecordDo);
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
