package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleDynamicDo;
import com.hitwh.vblog.bean.ThumbRecordDo;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.ThumbRecordDoMapper;
import com.hitwh.vblog.model.ThumbModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ThumbService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 11218
 * @description: 点赞Service
 * @date 2019/6/517:05
 */
@Service
public class ThumbServiceImpl implements ThumbService {
    @Autowired
    ThumbRecordDoMapper thumbRecordDoMapper;
    @Autowired
    ValidatorImpl validator;
    @Autowired
    ArticleDynamicDoMapper articleDynamicDoMapper;

    @Override
    public void insertThumbRecord(ThumbModel thumbRecordModel) throws BusinessException {
        if(thumbRecordModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(thumbRecordModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ThumbRecordDo thumbRecordDo = new ThumbRecordDo();
        thumbRecordDo.setArticleId(thumbRecordModel.getArticleId());
        thumbRecordDo.setThumberId(thumbRecordModel.getUserId());
        thumbRecordDo.setThumbTime(new Date(System.currentTimeMillis()));

        Integer column = thumbRecordDoMapper.insertSelective(thumbRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbRecordModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数加一
        Integer addColum = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);

        if(addColum == null || addColum == 0)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }

    @Override
    public Integer countThumbNum(Integer articleId) throws BusinessException {
        if(articleId == null || articleId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        Integer num = thumbRecordDoMapper.countThumbNum(articleId);

        return num;
    }


    @Override
    public void deleteThumbRecord(ThumbModel thumbRecordModel) throws BusinessException {
        if(thumbRecordModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(thumbRecordModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ThumbRecordDo thumbRecordDo = new ThumbRecordDo();
        thumbRecordDo.setArticleId(thumbRecordModel.getArticleId());
        thumbRecordDo.setThumberId(thumbRecordModel.getUserId());

        Integer column = thumbRecordDoMapper.delete(thumbRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.DATABASE_DELETE_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbRecordModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数减一
        Integer addColum = articleDynamicDoMapper.subtractArticleDynamic(articleDynamicDo);

        if(addColum == null || addColum == 0)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }



}
