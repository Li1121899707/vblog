package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.CommentDo;

public interface CommentDoMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(CommentDo record);

    int insertSelective(CommentDo record);

    CommentDo selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(CommentDo record);

    int updateByPrimaryKey(CommentDo record);
}