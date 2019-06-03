package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.bean.CommentDo;
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

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDoMapper commentDoMapper;
    @Autowired
    ValidatorImpl validator;

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
        int sum = commentDoMapper.selectByArticleId(articleId).size();
        map.put("sum",sum);
        map.put("list",commentDoMapper.selectDisplayComment(start,num, articleId));
        return map;
    }

    @Override
    public Map<String, Object> selectDisplayCommentById(Integer start, Integer num, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        int sum = commentDoMapper.selectByUserId(userId).size();
        map.put("sum",sum);
        map.put("list",commentDoMapper.selectDisplayCommentById(start,num, userId));
        return map;
    }

    @Override
    public CommentOutParam selectForParent(Integer parent_comment_id) throws BusinessException {
        //通过数据库查询评论
        ComAndUserDo comAndUserDo = commentDoMapper.selectForParent(parent_comment_id);
        //判断是否为空
        if (comAndUserDo == null)
            return null;
        //转换成commentoutparam
        CommentOutParam commentOutParam = new CommentOutParam();
        commentOutParam.setArticle_id(comAndUserDo.getCommentDo().getArticleId());
        commentOutParam.setUser_nickname(comAndUserDo.getUserDo().getNickname());
        commentOutParam.setComment(comAndUserDo.getCommentDo().getComment());
        commentOutParam.setComment_time(comAndUserDo.getCommentDo().getCommentTime());
        commentOutParam.setUser_id(comAndUserDo.getUserDo().getUserId());
        commentOutParam.setParent_comment_id(comAndUserDo.getCommentDo().getParentCommentId());
        commentOutParam.setAvatar_sm(comAndUserDo.getUserDo().getAvatarSm());
        //判断父评论是否被隐藏
        if(comAndUserDo.getCommentDo().getCommentHide() == 1)
            throw new BusinessException(EnumError.PARENT_COMMENT_HIDDEN);

        return commentOutParam;
    }

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
    }

    @Override
    public void hideComment(Integer commentId) throws BusinessException {
        //判断control传来的参数的正确性
        if(commentId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        Integer hideResult = commentDoMapper.updateCommentHide(commentId);
        //判断是否隐藏成功
        if(hideResult != 1)
            throw new BusinessException(EnumError.COMMENT_HIDE_ERROR);

    }
}
