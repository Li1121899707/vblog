package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.TokenDo;
import org.apache.ibatis.annotations.Param;

public interface TokenDoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(TokenDo record);

    int insertSelective(TokenDo record);

    TokenDo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(TokenDo record);

    int updateByPrimaryKey(TokenDo record);

    int updateForLogout(@Param("userId")Integer userId);
}