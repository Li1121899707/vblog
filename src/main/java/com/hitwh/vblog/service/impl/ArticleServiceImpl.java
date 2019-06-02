package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.ArticleDo;
import com.hitwh.vblog.bean.ArticleDynamicDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
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
    ArticleDynamicDoMapper articleDynamicDoMapper;
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
        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();

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

        articleDynamicDo.setArticleId(articleDo.getArticleId());
        articleDynamicDo.setVirtualId(virture);

        writeResult = articleDynamicDoMapper.insertSelective(articleDynamicDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }

    @Override
    public void delete(Integer article_id) throws BusinessException {
        if (article_id == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        Integer deleteResult = 0;
        deleteResult = articleDoMapper.deleteByPrimaryKey(article_id);
        if (deleteResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }

    @Override
    public void update(ArticleModel articleModel) throws BusinessException {
        if (articleModel.getArticle_id() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        ArticleDo articleDoTest = new ArticleDo();
        ArticleDo articleDo = new ArticleDo();
        articleDoTest = articleDoMapper.selectByPrimaryKey(articleModel.getArticle_id());
        if (articleDoTest == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        ValidationResult result = validator.validate(articleModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        articleDo.setArticleId(articleModel.getArticle_id());
        articleDo.setVirtualId(null);
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
        articleDo.setUpdateTime(timeStamp);

        Integer writeResult = 0;

        writeResult  = articleDoMapper.updateByPrimaryKeySelective(articleDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }

    @Override
    public ArticleOutParam queryArticleId(Integer article_id) throws BusinessException {
        ArticleAndUserDo articleAndUserDo = new ArticleAndUserDo();
        articleAndUserDo = articleDoMapper.selectSingleArticle(article_id);
        if (articleAndUserDo == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        ArticleOutParam articleOutParam = new ArticleOutParam();
        articleOutParam.setArticle_id(articleAndUserDo.getArticleDo().getVirtualId());
        articleOutParam.setTitle(articleAndUserDo.getArticleDo().getTitle());
        articleOutParam.setAuthor_id(articleAndUserDo.getArticleDo().getArticleId());
        articleOutParam.setAuthor_nickname(articleAndUserDo.getUserDo().getNickname());
        articleOutParam.setType_1(articleAndUserDo.getArticleDo().getType1());
        articleOutParam.setType_2(articleAndUserDo.getArticleDo().getType2());
        articleOutParam.setLabel_name1(articleAndUserDo.getLabelDo().getLabelName());//暂时只能返回一个标签
        articleOutParam.setCover(null);//url在另一张表
        articleOutParam.setHidden(articleAndUserDo.getArticleDo().getHidden());
        articleOutParam.setContent(articleAndUserDo.getArticleDo().getContent());
        articleOutParam.setArticleAbstract(articleAndUserDo.getArticleDo().getArticleAbstract());
        articleOutParam.setRelease_time(1000);
        articleOutParam.setThumb(articleAndUserDo.getArticleDynamicDo().getThumbNum());
        articleOutParam.setReading(articleAndUserDo.getArticleDynamicDo().getReadingNum());

        return articleOutParam;
    }
}
