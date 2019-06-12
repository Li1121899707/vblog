package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.*;
import com.hitwh.vblog.mapper.ArticleDoMapper;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.CommentDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentForUserOutParam;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.CommentService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    private ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();

    private Integer endForOut;

    @Autowired
    CommentDoMapper commentDoMapper;
    @Autowired
    ValidatorImpl validator;
    @Autowired
    UserDoMapper userDoMapper;
    @Autowired
    ArticleDoMapper articleDoMapper;
    @Autowired
    ArticleDynamicDoMapper articleDynamicDoMapper;


    @Override
    public Map<String,Object> selectDisplayComment(Integer start, Integer end, Integer articleId) throws BusinessException {
        if(start == null || end == null || articleId == null || start < 0|| end < start )
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        int num = end - start + 1;
        int sum = 0;

        Map<String,Object> map = new HashMap<>();

        ArticleDo articleDo = null;
        try {
            articleDo = articleDoMapper.selectByPrimaryKey(articleId);
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

        if(articleDo == null)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        //通过文章的ID查找出所有的评论
        try {
            sum = commentDoMapper.selectByArticleId(articleId).size();
        }catch (Exception e){
            throw new BusinessException(EnumError.QUERY_NOT_EXIST);
        }

        //查找出要展示的评论
        List<ComAndUserDo> comAndUserDoList = commentDoMapper.selectDisplayComment(
                start,num, articleId);

        if(comAndUserDoList == null || comAndUserDoList.size() == 0)
            return null;

        //将格式转换成输出类型
        List<CommentOutParam> commentOutParamList = new ArrayList<>();
        for (ComAndUserDo c:comAndUserDoList) {
            commentOutParamList.add(typeChange(c));
        }

        //判断是否取到足够的数据
        if (comAndUserDoList.size() < num)
            endForOut = start + comAndUserDoList.size() - 1;
        else endForOut = start + num - 1;

        //将数据进行封装，返回
        map.put("start",start);
        map.put("end",endForOut);
        map.put("sum",sum);
        map.put("list",commentOutParamList);
        return map;
    }

    //通过用户ID来查找评论
    @Override
    public Map<String, Object> selectDisplayCommentById(Integer start, Integer end, Integer userId) throws BusinessException {
        if(start == null || end == null || userId == null || start < 0|| end < start)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        int num = end - start + 1;

        Map<String,Object> map = new HashMap<>();
        int sum = commentDoMapper.selectByUserId(userId).size();

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectByPrimaryKey(userId);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(userDo == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        //查找出要展示的评论
        List<ComAndUserDo> comAndUserDoList = commentDoMapper.selectDisplayCommentById(
                start,num, userId);

        if(comAndUserDoList == null || comAndUserDoList.size() == 0)
            return null;

        //将格式转换成输出类型
        List<CommentOutParam> commentOutParamList = new ArrayList<>();
        for (ComAndUserDo c:comAndUserDoList) {
            commentOutParamList.add(typeChange(c));
        }

        //判断是否取到足够的数据
        if (comAndUserDoList.size() < num)
            endForOut = start + comAndUserDoList.size() - 1;
        else endForOut = start + num - 1;

        //将数据进行封装，返回
        map.put("start",start);
        map.put("end",endForOut);
        map.put("sum",sum);
        map.put("list",commentOutParamList);

        return map;
    }
    //查找父评论
    @Override
    public CommentOutParam selectForParent(Integer parent_comment_id) throws BusinessException {
        if(parent_comment_id == null || parent_comment_id <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
        //通过数据库查询评论
        ComAndUserDo comAndUserDo = commentDoMapper.selectForParent(parent_comment_id);
        //判断是否为空
        if (comAndUserDo == null)
            throw new BusinessException(EnumError.QUERY_NOT_EXIST);
        //转换成commentoutparam
        CommentOutParam commentOutParam = typeChange(comAndUserDo);
        //判断父评论是否被隐藏
//        if(comAndUserDo.getCommentDo().getCommentHide() == 1)
//            throw new BusinessException(EnumError.PARENT_COMMENT_HIDDEN);

        return commentOutParam;
    }
//添加评论
    @Override
    public void insertComment(CommentModel commentModel) throws BusinessException {
        if (commentModel == null) {
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
        }

        ValidationResult result = validator.validate(commentModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        ArticleDo articleDo;
        try {
            articleDo = articleDoMapper.selectByPrimaryKey(commentModel.getArticleId());
        }catch (Exception e){
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);
        }

        if(articleDo == null)
            throw new BusinessException(EnumError.ARTICLE_NOT_EXIST);

        //往数据库中存储评论
        Integer ifInsert = 0;
        CommentDo commentDo = new CommentDo();
        commentDo.setComment(commentModel.getComment());
        commentDo.setArticleId(commentModel.getArticleId());
        commentDo.setCommentTime(new Date(System.currentTimeMillis()));
        commentDo.setUserId(commentModel.getUserId());

        if(commentModel.getParentCommentId() == null || commentModel.getParentCommentId() <= 0)
            commentDo.setParentCommentId(0);
        else
            commentDo.setParentCommentId(commentModel.getParentCommentId());

        try {
            ifInsert = commentDoMapper.insertSelective(commentDo);
        } catch (Exception e) {
            throw new BusinessException(EnumError.COMMENT_INSERT_ERROR);
        }
        //返回值不为1则抛出错误
        if (ifInsert != 1) {
            throw new BusinessException(EnumError.COMMENT_INSERT_ERROR);
        }

        if(commentModel.getParentCommentId() == null || commentModel.getParentCommentId() <=0){
            CommentDo updateCommentDo = new CommentDo();
            updateCommentDo.setCommentId(commentDo.getCommentId());
            updateCommentDo.setParentCommentId(commentDo.getCommentId());
            try {
                commentDoMapper.updateByPrimaryKeySelective(updateCommentDo);
            }catch (Exception e){
                throw new BusinessException(EnumError.COMMENT_INSERT_ERROR);
            }

        }

        articleDynamicDo.setArticleId(commentModel.getArticleId());
        articleDynamicDo.setCommentNum(1);
        int i = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);
        if(i != 1)
            throw new BusinessException(EnumError.COMMENT_INSERT_ERROR);
    }
    //隐藏评论
    @Override
    public void hideComment(Integer commentId) throws BusinessException {
        //判断control传来的参数的正确性
        if(commentId == null || commentId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        CommentDo commentDo = null;
        try {
            commentDo = commentDoMapper.selectByPrimaryKey(commentId);
        }catch (Exception e){
            throw new BusinessException(EnumError.COMMENT_NOT_EXIST);
        }

        Integer hideResult = 0;
        try {
            hideResult = commentDoMapper.updateCommentHide(commentId);
        }catch (Exception e){
            throw new BusinessException(EnumError.COMMENT_HIDDEN_ERROR);
        }

        //判断是否隐藏成功
        if(hideResult != 1)
            throw new BusinessException(EnumError.COMMENT_HIDDEN_ERROR);

    }

    @Override
    public void commentAdminHide(Integer uid, Integer commentId) throws BusinessException {
        if (uid == null||commentId == null||uid < 0||commentId < 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        CommentDo commentDo = null;
        try {
            commentDo = commentDoMapper.selectByPrimaryKey(commentId);
        } catch (Exception e){
            throw new BusinessException(EnumError.COMMENT_NOT_EXIST);
        }

        if(commentDo == null)
            throw new BusinessException(EnumError.COMMENT_NOT_EXIST);

        Integer hideResult = 0;
        try {
            hideResult  = commentDoMapper.updateCommentHide(commentId);
        }catch (Exception e){
            throw new BusinessException(EnumError.COMMENT_HIDDEN_ERROR);
        }

        //判断是否隐藏成功
        if(hideResult != 1)
            throw new BusinessException(EnumError.COMMENT_HIDDEN_ERROR);

    }

    @Override
    public List<Map<String, Object>> queryForUser(Integer userId) throws BusinessException {
        if (userId == null||userId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        
        List<ArticleDo> articleDoList = articleDoMapper.selectByUserId(userId);
        //List<List<>>
        List<Map<String,Object>> maps = new ArrayList<>();
        for (int i = 0;i < articleDoList.size();i++)
        {
            Map<String,Object> map = new HashMap<>();
            List<CommentForUserOutParam> commentForUserOutParams = new ArrayList<>();
            List<ComAndUserDo> comAndUserDos = commentDoMapper.selectForUser(articleDoList.get(i).getArticleId());
            for (int j = 0;j < comAndUserDos.size();j++){

                CommentForUserOutParam commentForUserOutParam = new CommentForUserOutParam();
                commentForUserOutParam.setArticle_id(articleDoList.get(i).getArticleId());
                commentForUserOutParam.setTitle(articleDoList.get(i).getTitle());
                commentForUserOutParam.setAvatar_sm(comAndUserDos.get(j).getUserDo().getAvatarSm());
                commentForUserOutParam.setComment(comAndUserDos.get(j).getCommentDo().getComment());
                commentForUserOutParam.setComment_time((comAndUserDos.get(j).getCommentDo().getCommentTime()).getTime());
                commentForUserOutParam.setParent_comment_id(comAndUserDos.get(j).getCommentDo().getCommentId());
                commentForUserOutParam.setUser_id(comAndUserDos.get(j).getUserDo().getUserId());
                commentForUserOutParam.setUser_nickname(comAndUserDos.get(j).getUserDo().getNickname());

                commentForUserOutParams.add(commentForUserOutParam);
            }
            map.put("title",articleDoList.get(i).getTitle());
            map.put("comment",commentForUserOutParams);
            maps.add(map);
        }

        return maps;
    }

    //将数据库查出的类型转换为输出类型
    public CommentOutParam typeChange(ComAndUserDo comAndUserDo){
        CommentOutParam commentOutParam = new CommentOutParam();
        commentOutParam.setArticle_id(comAndUserDo.getCommentDo().getArticleId());
        commentOutParam.setUser_nickname(comAndUserDo.getUserDo().getNickname());
        commentOutParam.setComment(comAndUserDo.getCommentDo().getComment());
        commentOutParam.setComment_time(comAndUserDo.getCommentDo().getCommentTime().getTime()/1000);
        commentOutParam.setUser_id(comAndUserDo.getUserDo().getUserId());
        commentOutParam.setParent_comment_id(comAndUserDo.getCommentDo().getParentCommentId());
        commentOutParam.setAvatar_sm(comAndUserDo.getUserDo().getAvatarSm());
        return commentOutParam;
    }
}
