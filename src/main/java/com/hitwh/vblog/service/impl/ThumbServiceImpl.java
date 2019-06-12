package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleDo;
import com.hitwh.vblog.bean.ArticleDynamicDo;
import com.hitwh.vblog.bean.ThumbRecordDo;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.ThumbRecordDoMapper;
import com.hitwh.vblog.model.ThumbModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ThumbService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    @Autowired
    ArticleDoMapper articleDoMapper;

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

        ArticleDo articleDo = null;
        try {
            articleDo = articleDoMapper.selectByPrimaryKey(thumbModel.getArticleId());
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

        if(articleDo == null)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        thumbRecordDo.setThumbTime(new Date(System.currentTimeMillis()));

        int num = thumbRecordDoMapper.selectIfThumb(thumbRecordDo);
        if(num != 0 )
            throw new BusinessException(EnumError.THUMB_EXIST);

        Integer column = 0;
        try {
            column = thumbRecordDoMapper.insertSelective(thumbRecordDo);
        }catch (Exception e){
            System.out.println("插入点赞失败");
            throw new BusinessException(EnumError.THUMB_INSERT_ERROR);
        }

        if(column == null || column == 0)
            throw new BusinessException(EnumError.THUMB_INSERT_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数加一
        Integer addColum = 0;
        try {
            addColum = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);
        }catch (Exception e){
            System.out.println("点赞数加一失败");
            throw new BusinessException(EnumError.THUMB_INSERT_ERROR);
        }

        if(addColum == null || addColum == 0)
            throw new BusinessException(EnumError.THUMB_INSERT_ERROR);
    }

    @Override
    public Integer countThumbNum(Integer articleId) throws BusinessException {
        if(articleId == null || articleId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ArticleDynamicDo articleDynamicDo = null;
        try {
            articleDynamicDo = articleDynamicDoMapper.selectByPrimaryKey(articleId);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

        if(articleDynamicDo == null || articleDynamicDo.getThumbNum() == null )
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        return articleDynamicDo.getThumbNum();
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
        ArticleDo articleDo = articleDoMapper.selectByPrimaryKey(thumbModel.getArticleId());
        if(articleDo == null)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        int num = thumbRecordDoMapper.selectIfThumb(thumbRecordDo);
        if(num == 0 )
            throw new BusinessException(EnumError.THUMB_NOT_EXIST);

        Integer column = 0;
        try {
            column = thumbRecordDoMapper.delete(thumbRecordDo);
        }catch (Exception e){
            System.out.println("点赞数加一失败");
            throw new BusinessException(EnumError.THUMB_DELETE_ERROR);
        }


        if(column == null || column == 0)
            throw new BusinessException(EnumError.THUMB_DELETE_ERROR);

        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        articleDynamicDo.setArticleId(thumbModel.getArticleId());
        articleDynamicDo.setThumbNum(1);

        // 点赞数减一
        Integer addColum = 0;
        try{
            addColum = articleDynamicDoMapper.subtractArticleDynamic(articleDynamicDo);
        }catch (Exception e){
            System.out.println("点赞数减一失败");
            throw new BusinessException(EnumError.THUMB_DELETE_ERROR);
        }


        if(addColum == null || addColum == 0){
            System.out.println("点赞数减一失败");
            throw new BusinessException(EnumError.THUMB_DELETE_ERROR);
        }

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

        int num = thumbRecordDoMapper.selectIfThumb(thumbRecordDo);

        if(num == 0)
            return false;
        else
            return true;
    }


}
