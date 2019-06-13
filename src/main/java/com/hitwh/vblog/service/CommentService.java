package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.inparam.CommentInParam;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentForUserOutParam;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentService {
    Map<String,Object> selectDisplayComment(Integer start, Integer end, Integer articleId) throws BusinessException;

    Map<String,Object> selectDisplayCommentById(Integer start, Integer end, Integer userId) throws BusinessException;

    CommentForUserOutParam selectForParent(@Param("parent_comment_id") Integer parent_comment_id) throws BusinessException;

    void insertComment(CommentModel commentModel) throws BusinessException;

    void hideComment(Integer commentId) throws BusinessException;

    void commentAdminHide(Integer uid, Integer commentId) throws BusinessException;

    List<CommentForUserOutParam> queryForUser(Integer userId) throws BusinessException;
}
