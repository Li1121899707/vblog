package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.DemoUserDo;

// 数据库映射类，是mybatis中的xml的函数声明，与bean中的xxxDo类一一对应
public interface DemoUserDoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(DemoUserDo record);

    int insertSelective(DemoUserDo record);

    DemoUserDo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(DemoUserDo record);

    int updateByPrimaryKeyWithBLOBs(DemoUserDo record);

    int updateByPrimaryKey(DemoUserDo record);
}