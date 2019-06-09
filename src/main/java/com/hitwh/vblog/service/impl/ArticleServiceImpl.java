package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.*;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.ArticleLabelDoMapper;
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
import org.omg.CORBA.INTERNAL;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    ArticleLabelDoMapper articleLabelDoMapper;

    @Override
    public void write(ArticleModel articleModel) throws BusinessException {
        if(articleModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        ValidationResult result = validator.validate(articleModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        UserDo userDo = new UserDo();
        ArticleDo articleDo = new ArticleDo();
        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        ArticleLabelDo articleLabelDo = new ArticleLabelDo();

        userDo = userDoMapper.selectByPrimaryKey(articleModel.getUid());

        String title = articleModel.getTitle();
        String author = userDo.getAccount();
        String time = String.valueOf(System.currentTimeMillis());
        String virture = MyMd5.md5Encryption(title + author + time);
        articleDo.setVirtualId(virture);//标题+作者+时间 MD5
        articleDo.setTitle(articleModel.getTitle());
        articleDo.setAuthorId(articleModel.getUid());
        articleDo.setCover(articleModel.getCover());
        articleDo.setHidden(articleModel.getHidden());
        articleDo.setContent(articleModel.getContent());
        articleDo.setArticleAbstract(articleModel.getArticleAbstract());
        Timestamp timeStamp = TimestampUtil.getNowTime();
        articleDo.setReleaseTime(timeStamp);
        articleDo.setUpdateTime(timeStamp);

        Integer writeResult = articleDoMapper.insertSelective(articleDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        articleDynamicDo.setArticleId(articleDo.getArticleId());
        articleDynamicDo.setVirtualId(virture);

        writeResult = articleDynamicDoMapper.insertSelective(articleDynamicDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        articleLabelDo.setArticleId(articleDo.getArticleId());
        articleLabelDo.setLabelId(articleModel.getType_1());
        articleLabelDo.setLabelAddTime(timeStamp);

        writeResult = articleLabelDoMapper.insert(articleLabelDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        if (articleModel.getType_2() != null)
        {
            articleLabelDo.setArticleId(articleDo.getArticleId());
            articleLabelDo.setLabelId(articleModel.getType_2());
            articleLabelDo.setLabelAddTime(timeStamp);

            writeResult = articleLabelDoMapper.insert(articleLabelDo);

            if (writeResult != 1)
                throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        }
    }

    @Override
    public void delete(Integer article_id, Integer uid) throws BusinessException {
        if (article_id == null || uid == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        Integer deleteResult = articleDoMapper.deleteByPrimaryKey(article_id, uid);

        if (deleteResult != 1)
            throw new BusinessException(EnumError.DATABASE_DELETE_ERROR);

        deleteResult = articleDynamicDoMapper.deleteByPrimaryKey(article_id);

        if (deleteResult != 1)
            throw new BusinessException(EnumError.DATABASE_DELETE_ERROR);

    }

    @Override
    public void update(ArticleModel articleModel) throws BusinessException {
        if (articleModel.getArticle_id() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        ArticleDo articleDoTest = new ArticleDo();
        ArticleDo articleDo = new ArticleDo();
        ArticleLabelDo articleLabelDo = new ArticleLabelDo();
        articleDoTest = articleDoMapper.selectByPrimaryKey(articleModel.getArticle_id());

        if (articleDoTest == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, EnumError.PARAMETER_VALIDATION_ERROR.getErrMsg());

        ValidationResult result = validator.validate(articleModel);

        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        articleDo.setArticleId(articleModel.getArticle_id());
        articleDo.setVirtualId(null);
        articleDo.setTitle(articleModel.getTitle());
        articleDo.setAuthorId(articleModel.getUid());
        articleDo.setCover(articleModel.getCover());
        articleDo.setHidden(articleModel.getHidden());
        articleDo.setContent(articleModel.getContent());
        articleDo.setArticleAbstract(articleModel.getArticleAbstract());
        Timestamp timeStamp = TimestampUtil.getNowTime();
        articleDo.setUpdateTime(timeStamp);

        Integer writeResult  = articleDoMapper.updateByPrimaryKeySelective(articleDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        articleLabelDo.setArticleId(articleModel.getArticle_id());
        articleLabelDo.setLabelId(articleModel.getType_1());
        articleLabelDo.setLabelAddTime(timeStamp);

        writeResult = articleLabelDoMapper.deleteByPrimaryKey(articleLabelDo.getArticleId());
        writeResult = articleLabelDoMapper.insert(articleLabelDo);
        if (writeResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        if (articleModel.getType_2() != null)
        {
            articleLabelDo.setArticleId(articleModel.getArticle_id());
            articleLabelDo.setLabelId(articleModel.getType_2());

            writeResult = articleLabelDoMapper.insert(articleLabelDo);
            if (writeResult != 1)
                throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        }
    }

    // 查询单条记录
    @Override
    public ArticleOutParam queryArticleId(Integer article_id) throws BusinessException {
        if(article_id == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "文章id不合法");

        ArticleAndUserDo articleAndUserDo = new ArticleAndUserDo();
        articleAndUserDo = articleDoMapper.selectSingleArticle(article_id);
        if (articleAndUserDo == null)
            return null;

        ArticleOutParam articleOutParam = new ArticleOutParam();
        articleOutParam.setArticle_id(articleAndUserDo.getArticleDo().getArticleId());
        articleOutParam.setVirtual_id(articleAndUserDo.getArticleDo().getVirtualId());
        articleOutParam.setTitle(articleAndUserDo.getArticleDo().getTitle());
        articleOutParam.setAuthor_id(articleAndUserDo.getArticleDo().getArticleId());
        articleOutParam.setAuthor_nickname(articleAndUserDo.getUserDo().getNickname());
        articleOutParam.setCover(articleAndUserDo.getArticleDo().getCover());//另一张表的url字段
        articleOutParam.setContent(articleAndUserDo.getArticleDo().getContent());
        articleOutParam.setArticleAbstract(articleAndUserDo.getArticleDo().getArticleAbstract());
        articleOutParam.setRelease_time(articleAndUserDo.getArticleDo().getReleaseTime().getTime());//用getTime将Date转为long型
        articleOutParam.setThumb(articleAndUserDo.getArticleDynamicDo().getThumbNum());
        articleOutParam.setReading(articleAndUserDo.getArticleDynamicDo().getReadingNum());
        List<ArticleLabelDoSimple> articleLabelDoSimples = articleLabelDoMapper.selectAllArticleInterest(articleAndUserDo.getArticleDo().getArticleId());
        articleOutParam.setLabels(articleLabelDoSimples);

        return articleOutParam;
    }

    @Override
    public Map<String, Object> selectArticleByAuthorId(Integer start, Integer end, Integer userId) throws BusinessException {
        if(start == null || end == null || start < 0 || end <= start || end == 0 || userId == null || userId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByUserId(userId);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByUserId(start, end-start+1, userId);
        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> selectArticleByType(Integer start, Integer end, Integer typeId) throws BusinessException {
        if(start == null || end == null || start < 0 || end <= start || end == 0 || typeId == null || typeId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByType(typeId);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByType(start, end-start+1, typeId);
        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> selectArticleByTitle(Integer start, Integer end, String title) throws BusinessException {
        if(start == null || end == null || start < 0 || end <= start || end == 0 || title == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "参数错误");

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByTitle(title);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByTitle(start, end-start+1, title);
        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> recommend() {
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.recommend();
        Map<String,Object> map = new HashMap<>();
        map.put("sum", articleAndUserDos.size());
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    public List<ArticleOutParam> convertToArticleOutParams(List<ArticleAndUserDo> articleAndUserDos){
        List<ArticleOutParam> articleOutParams = new ArrayList<>();
        for (ArticleAndUserDo a: articleAndUserDos
        ) {
            ArticleOutParam articleOutParam = new ArticleOutParam();
            articleOutParam.setArticle_id(a.getArticleDo().getArticleId());
            articleOutParam.setVirtual_id(a.getArticleDo().getVirtualId());
            articleOutParam.setAuthor_id(a.getArticleDo().getAuthorId());
            articleOutParam.setAuthor_nickname(a.getUserDo().getNickname());
            articleOutParam.setTitle(a.getArticleDo().getTitle());
            articleOutParam.setArticleAbstract(a.getArticleDo().getArticleAbstract());
            articleOutParam.setContent(a.getArticleDo().getContent());
            articleOutParam.setCover(a.getArticleDo().getCover());
            articleOutParam.setReading(a.getArticleDynamicDo().getReadingNum());
            articleOutParam.setThumb(a.getArticleDynamicDo().getThumbNum());
            articleOutParam.setRelease_time(a.getArticleDo().getReleaseTime().getTime());//用getTime将Date转为long型
            List<ArticleLabelDoSimple> articleLabelDoSimples = articleLabelDoMapper.selectAllArticleInterest(a.getArticleDo().getArticleId());
            articleOutParam.setLabels(articleLabelDoSimples);
            articleOutParams.add(articleOutParam);
        }
        return articleOutParams;
    }

    @Override
    public ArticleOutParam queryRandomArticle() throws BusinessException {
        int number = articleDoMapper.selectArticleNumber();
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectAllArticle();
        List<ArticleOutParam> articleOutParams = convertToArticleOutParams(articleAndUserDos);
        int choice = (int)(Math.random()*number);
        ArticleOutParam articleOutParam = articleOutParams.get(choice);
        return articleOutParam;
    }




}
