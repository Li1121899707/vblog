package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.bean.ComUserArticleDo;
import com.hitwh.vblog.bean.CommentDo;
import com.hitwh.vblog.outparam.CommentOutParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDoMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(CommentDo record);

    int insertSelective(CommentDo record);

    CommentDo selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(CommentDo record);

    int updateByPrimaryKey(CommentDo record);

    List<ComAndUserDo> selectDisplayComment(@Param("start") Integer start,
                                               @Param("num") Integer num,
                                               @Param("articleId") Integer articleId);

    List<CommentDo> selectByArticleId(Integer articleId);

    List<ComUserArticleDo> selectDisplayCommentById(@Param("start") Integer start,
                                            @Param("num") Integer num,
                                            @Param("userId") Integer userId);

    List<CommentDo> selectByUserId(Integer userId);

    ComUserArticleDo selectForParent(Integer parent_comment_id);

    Integer updateCommentHide(Integer commentId);

    List<ComAndUserDo> selectForUser(@Param("articleId") Integer articleId);

}