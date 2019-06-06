package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleDynamicDo;
import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.bean.CommentDo;
import com.hitwh.vblog.mapper.ArticleDynamicDoMapper;
import com.hitwh.vblog.mapper.CommentDoMapper;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.CommentService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private ArticleDynamicDo articleDynamicDo = new ArticleDynamicDo();

    private Integer endForOut;

    @Autowired
    CommentDoMapper commentDoMapper;
    @Autowired
    ValidatorImpl validator;

    @Autowired
    ArticleDynamicDoMapper articleDynamicDoMapper;
    /**
     *
     * @param start
     * @param num
     * @param articleId
     * @return 通过文章ID查找评论
     */
    @Override
    public Map<String,Object> selectDisplayComment(Integer start, Integer num, Integer articleId) {
        //if(commentDoMapper.selectDisplayComment(start,num, articleId) == null)

        Map<String,Object> map = new HashMap<>();

        //通过文章的ID查找出所有的评论
        int sum = commentDoMapper.selectByArticleId(articleId).size();

        //查找出要展示的评论
        List<ComAndUserDo> comAndUserDoList = commentDoMapper.selectDisplayComment(
                start,num, articleId);

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
    public Map<String, Object> selectDisplayCommentById(Integer start, Integer num, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        int sum = commentDoMapper.selectByUserId(userId).size();

        //查找出要展示的评论
        List<ComAndUserDo> comAndUserDoList = commentDoMapper.selectDisplayCommentById(
                start,num, userId);

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
        //通过数据库查询评论
        ComAndUserDo comAndUserDo = commentDoMapper.selectForParent(parent_comment_id);
        //判断是否为空
        if (comAndUserDo == null)
            return null;
        //转换成commentoutparam
        CommentOutParam commentOutParam = typeChange(comAndUserDo);
        //判断父评论是否被隐藏
        if(comAndUserDo.getCommentDo().getCommentHide() == 1)
            throw new BusinessException(EnumError.PARENT_COMMENT_HIDDEN);

        return commentOutParam;
    }
//添加评论
    @Override
    public void insertComment(CommentModel commentModel) throws BusinessException {
        if (commentModel == null) {
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult result = validator.validate(commentModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //往数据库中存储评论
        Integer ifInsert;
        CommentDo commentDo = new CommentDo();
        BeanUtils.copyProperties(commentModel, commentDo);
        try {
            ifInsert = commentDoMapper.insertSelective(commentDo);
        } catch (Exception e) {
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        }
        //返回值不为1则抛出错误
        if (ifInsert != 1) {
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        }
        articleDynamicDo.setArticleId(commentModel.getArticleId());
        articleDynamicDo.setCommentNum(1);
        int i = articleDynamicDoMapper.addArticleDynamic(articleDynamicDo);
        if(i != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }
//隐藏评论
    @Override
    public void hideComment(Integer commentId) throws BusinessException {
        //判断control传来的参数的正确性
        if(commentId == null || commentId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        Integer hideResult = commentDoMapper.updateCommentHide(commentId);
        //判断是否隐藏成功
        if(hideResult != 1)
            throw new BusinessException(EnumError.COMMENT_HIDE_ERROR);

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
