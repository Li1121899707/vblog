package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ArticleService;
import com.hitwh.vblog.util.MyMd5;
import com.hitwh.vblog.util.TimestampUtil;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    UserDoMapper userDoMapper;
    @Autowired
    ArticleDoMapper articleDoMapper;
    @Autowired
    ValidatorImpl validator;

    @Override
    public void write(ArticleModel articleModel) throws BusinessException {
        if(articleModel == null){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult result = validator.validate(articleModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        UserDo userDo = new UserDo();
        ArticleDo articleDo = new ArticleDo();

        userDo = userDoMapper.selectByPrimaryKey(articleModel.getUid());

        String title = articleModel.getTitle();
        String author = userDo.getAccount();
        String time = String.valueOf(System.currentTimeMillis());
        String virture = MyMd5.md5Encryption(title + author + time);
        articleDo.setVirtualId(virture);//标题+作者+时间 MD5
        articleDo.setTitle(articleModel.getTitle());
        articleDo.setAuthorId(articleModel.getUid());
        articleDo.setType1(articleModel.getType_1());
        if (articleModel.getType_2() != null)
            articleDo.setType2(articleModel.getType_2());
        articleDo.setCover(articleModel.getCover());
        articleDo.setHidden(articleModel.getHidden());
        articleDo.setContent(articleModel.getContent());
        articleDo.setArticleAbstract(articleModel.getArticleAbstract());
        Timestamp timeStamp = TimestampUtil.getNowTime();
        articleDo.setReleaseTime(timeStamp);
        articleDo.setUpdateTime(timeStamp);

        Integer writeResult = 0;

        writeResult  = articleDoMapper.insertSelective(articleDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }
}
