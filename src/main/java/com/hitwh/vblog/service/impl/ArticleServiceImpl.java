package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.*;
import com.hitwh.vblog.mapper.*;
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
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Random rng = new Random();

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
    @Autowired
    LabelDoMapper labelDoMapper;

    @Override
    public void write(ArticleModel articleModel) throws BusinessException {
        if(articleModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(articleModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        UserDo userDo = new UserDo();
        ArticleDo articleDo = new ArticleDo();
        ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();
        ArticleLabelDo articleLabelDo = new ArticleLabelDo();

        try {
            userDo = userDoMapper.selectByPrimaryKey(articleModel.getUid());
        }catch (Exception e){
            System.out.println("用户不存在");
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

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

        Integer writeResult = 0;
        try {
            writeResult = articleDoMapper.insertSelective(articleDo);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_INSERT_ERROR);
        }

        if (writeResult != 1)
            throw new BusinessException(EnumError.ARTICLE_INSERT_ERROR);

        articleDynamicDo.setArticleId(articleDo.getArticleId());
        articleDynamicDo.setVirtualId(virture);

        writeResult = articleDynamicDoMapper.insertSelective(articleDynamicDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.ARTICLE_INSERT_ERROR);

        articleLabelDo.setArticleId(articleDo.getArticleId());
        articleLabelDo.setLabelId(articleModel.getType_1());
        articleLabelDo.setLabelAddTime(timeStamp);

        writeResult = articleLabelDoMapper.insert(articleLabelDo);

        if (writeResult != 1)
            throw new BusinessException(EnumError.ARTICLE_INSERT_ERROR);

        if (articleModel.getType_2() != null)
        {
            articleLabelDo.setArticleId(articleDo.getArticleId());
            articleLabelDo.setLabelId(articleModel.getType_2());
            articleLabelDo.setLabelAddTime(timeStamp);

            writeResult = articleLabelDoMapper.insert(articleLabelDo);

            if (writeResult != 1)
                throw new BusinessException(EnumError.ARTICLE_INSERT_ERROR);
        }
    }

    @Override
    public void delete(Integer article_id, Integer uid) throws BusinessException {
        if (article_id == null || uid == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ArticleDo articleDo = articleDoMapper.selectByPrimaryKey(article_id);
        if(articleDo == null)
            throw new BusinessException(EnumError.QUERY_NOT_EXIST);

        if(articleDo.getAuthorId() != uid)
            throw new BusinessException(EnumError.UNAUTHORIZED);

        Integer deleteResult = articleDoMapper.deleteByPrimaryKey(article_id, uid);

        boolean deletedTA = true, deletedTB = true, deleteTC = true;

        if (deleteResult != 1)
            throw new BusinessException(EnumError.ARTICLE_DELETE_FAILED);

        try {
            deleteResult = articleDynamicDoMapper.deleteByPrimaryKey(article_id);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

//        if(deletedTA && deletedTB) // success
//            else
//                // failed

        if (deleteResult != 1)
            throw new BusinessException(EnumError.ARTICLE_DELETE_FAILED);

        try {
            deleteResult = articleLabelDoMapper.deleteByPrimaryKey(article_id);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

        if(deleteResult < 1)
            throw new BusinessException(EnumError.ARTICLE_DELETE_FAILED);

    }

    @Override
    public void update(ArticleModel articleModel) throws BusinessException {
        if (articleModel.getArticle_id() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(articleModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ArticleDo articleDoTest = new ArticleDo();
        articleDoTest = articleDoMapper.selectByPrimaryKey(articleModel.getArticle_id());

        if (articleDoTest == null)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
//        if(articleDoTest.getHidden() == 1)
//            throw new BusinessException(EnumError.ARTICLE_HIDDEN);
        if(articleDoTest.getAuthorId() != articleModel.getUid())
            throw new BusinessException(EnumError.UNAUTHORIZED);

        ArticleDo articleDo = new ArticleDo();
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

        Integer writeResult  = 0;
        try {
            writeResult = articleDoMapper.updateByPrimaryKeySelective(articleDo);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_UPDATE_ERROR);
        }

        if (writeResult != 1)
            throw new BusinessException(EnumError.ARTICLE_UPDATE_ERROR);

        ArticleLabelDo articleLabelDo = new ArticleLabelDo();
        articleLabelDo.setArticleId(articleModel.getArticle_id());
        articleLabelDo.setLabelId(articleModel.getType_1());
        articleLabelDo.setLabelAddTime(timeStamp);

        writeResult = articleLabelDoMapper.deleteByPrimaryKey(articleLabelDo.getArticleId());
        writeResult = articleLabelDoMapper.insert(articleLabelDo);
        if (writeResult != 1)
            throw new BusinessException(EnumError.ARTICLE_UPDATE_ERROR);
        if (articleModel.getType_2() != null)
        {
            articleLabelDo.setArticleId(articleModel.getArticle_id());
            articleLabelDo.setLabelId(articleModel.getType_2());

            writeResult = articleLabelDoMapper.insert(articleLabelDo);
            if (writeResult != 1)
                throw new BusinessException(EnumError.ARTICLE_UPDATE_ERROR);
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
//        if(articleAndUserDo.getArticleDo().getHidden() == 1)
//            throw new BusinessException(EnumError.ARTICLE_HIDDEN);

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
    public Map<String, Object> selectArticleByAuthorId(Integer start, Integer end, Integer authorId) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || end == 0 || authorId == null || authorId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectByPrimaryKey(authorId);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(userDo == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByUserId(authorId);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByUserId(start, end-start+1, authorId);
        if(articleAndUserDos == null || articleAndUserDos.size() == 0)
            return null;

        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> selectArticleByType(Integer start, Integer end, Integer typeId) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || end == 0 || typeId == null || typeId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        LabelDo labelDo = null;
        try {
            labelDo = labelDoMapper.selectByPrimaryKey(typeId);
        }catch (Exception e){
            throw new BusinessException(EnumError.LABEL_NOT_EXIST);
        }
        if(labelDo == null)
            throw new BusinessException(EnumError.LABEL_NOT_EXIST);

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByType(typeId);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByType(start, end-start+1, typeId);
        if(articleAndUserDos == null || articleAndUserDos.size() == 0)
            return null;
        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> selectArticleByTitle(Integer start, Integer end, String title) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || end == 0 || title == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumByTitle(title);
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectArticleByTitle(start, end-start+1, title);
        if(articleAndUserDos == null || articleAndUserDos.size() == 0)
            return null;
        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> selectAllArticle(Integer start, Integer end) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || end == 0 )
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Map<String,Object> map = new HashMap<>();
        int sum = articleDoMapper.selectArticleNumber();
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectAllArticle(start, end-start+1);
        if(articleAndUserDos == null || articleAndUserDos.size() == 0)
            return null;

        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Map<String, Object> recommend(Integer num) {
        Integer count = 0;
        if(num == null || num <= 0)
            count = 10;
        else
            count = num;

        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.recommend(count);
        Map<String,Object> map = new HashMap<>();
        map.put("sum", articleAndUserDos.size());
        map.put("list", convertToArticleOutParams(articleAndUserDos));
        return map;
    }

    @Override
    public Integer getArticleId(String virtualId) throws BusinessException {
        if(virtualId == null || virtualId.equals(""))
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "文章ID传入参数错误");

        Integer articleId = 0;
        try {
            articleId = articleDoMapper.selectArticleIdByVirtualId(virtualId);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }
        if(articleId == null || articleId <= 0)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        return articleId;
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
        System.out.println("number " + number + "");
        List<ArticleAndUserDo> articleAndUserDos = articleDoMapper.selectAllArticle(0,number);
        List<ArticleOutParam> articleOutParams = convertToArticleOutParams(articleAndUserDos);

        int choice = rng.nextInt(number);

        ArticleOutParam articleOutParam = articleOutParams.get(choice);
        return articleOutParam;
    }
}
