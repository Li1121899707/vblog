package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.DemoUserPwdDo;

public interface DemoUserPwdDoMapper {
    int deleteByPrimaryKey(Integer demoUserPwdId);

    int insert(DemoUserPwdDo record);

    int insertSelective(DemoUserPwdDo record);

    DemoUserPwdDo selectByPrimaryKey(Integer demoUserPwdId);

    DemoUserPwdDo selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(DemoUserPwdDo record);

    int updateByPrimaryKey(DemoUserPwdDo record);
}