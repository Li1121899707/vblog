package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.DemoUserPwdDo;

// 数据库映射类，是mybatis中的xml的函数声明，与bean中的xxxDo类一一对应
public interface DemoUserPwdDoMapper {
    int deleteByPrimaryKey(Integer demoUserPwdId);

    int insert(DemoUserPwdDo record);

    int insertSelective(DemoUserPwdDo record);

    DemoUserPwdDo selectByPrimaryKey(Integer demoUserPwdId);

    DemoUserPwdDo selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(DemoUserPwdDo record);

    int updateByPrimaryKey(DemoUserPwdDo record);
}