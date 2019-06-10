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
 * @author liysuzy
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
    public void insertThumbRecord(ThumbModel thumbModel) throws BusinessException {
        if(thumbModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(thumbModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ThumbRecordDo thumbRecordDo = new ThumbRecordDo();
        thumbRecordDo.setArticleId(thumbModel.getArticleId());
        thumbRecordDo.setThumberId(thumbModel.getUserId());
        thumbRecordDo.setThumbTime(new Date(System.currentTimeMillis()));

        Integer column = thumbRecordDoMapper.insertSelective(thumbRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数加一
        Integer addColum = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);

        if(addColum == null || addColum == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Integer countThumbNum(Integer articleId) throws BusinessException {
        if(articleId == null || articleId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Integer num = thumbRecordDoMapper.countThumbNum(articleId);

        return num;
    }


    @Override
    public void deleteThumbRecord(ThumbModel thumbModel) throws BusinessException {
        if(thumbModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(thumbModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ThumbRecordDo thumbRecordDo = new ThumbRecordDo();
        thumbRecordDo.setArticleId(thumbModel.getArticleId());
        thumbRecordDo.setThumberId(thumbModel.getUserId());

        Integer column = thumbRecordDoMapper.delete(thumbRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数减一
        Integer addColum = articleDynamicDoMapper.subtractArticleDynamic(articleDynamicDo);

        if(addColum == null || addColum == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);

    }

    @Override
    public Boolean queryIfThumb(ThumbModel thumbModel) throws BusinessException {
        if(thumbModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(thumbModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ThumbRecordDo thumbRecordDo = new ThumbRecordDo();
        thumbRecordDo.setArticleId(thumbModel.getArticleId());
        thumbRecordDo.setThumberId(thumbModel.getUserId());

        ThumbRecordDo recordDo = thumbRecordDoMapper.selectIfThumb(thumbRecordDo);

        if(recordDo == null)
            return false;
        else
            return true;
    }
}
