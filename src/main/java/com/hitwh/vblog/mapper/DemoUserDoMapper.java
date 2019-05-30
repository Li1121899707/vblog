package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.DemoUserDo;

public interface DemoUserDoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(DemoUserDo record);

    int insertSelective(DemoUserDo record);

    DemoUserDo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(DemoUserDo record);

    int updateByPrimaryKeyWithBLOBs(DemoUserDo record);

    int updateByPrimaryKey(DemoUserDo record);
}