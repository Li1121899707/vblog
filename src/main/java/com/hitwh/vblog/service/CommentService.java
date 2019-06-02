package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.response.BusinessException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommentService {
    Map<String,Object> selectDisplayComment(@Param("start") Integer start,
                                            @Param("num") Integer num,
                                            @Param("articleId")Integer articleId);
    Map<String,Object> selectDisplayCommentById(@Param("start") Integer start,
                                                @Param("num") Integer num,
                                                @Param("userId")Integer userId);
    ComAndUserDo selectForParent(@Param("parent_comment_id") Integer parent_comment_id);
    void insertComment(CommentModel commentModel) throws BusinessException;
}
