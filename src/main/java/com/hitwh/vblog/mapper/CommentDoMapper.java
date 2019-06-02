package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.bean.CommentDo;
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


}