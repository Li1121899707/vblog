package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.UserDo;

public interface UserDoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserDo record);

    int insertSelective(UserDo record);

    UserDo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDo record);

    int updateByPrimaryKey(UserDo record);

    UserDo selectIfLogin(UserDo userDo);

}