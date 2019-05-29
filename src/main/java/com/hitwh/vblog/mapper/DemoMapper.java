package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.Demo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoMapper {
    int deleteByPrimaryKey(Integer testId);

    int insert(Demo record);

    int insertSelective(Demo record);

    Demo selectByPrimaryKey(Integer testId);

    int updateByPrimaryKeySelective(Demo record);

    int updateByPrimaryKeyWithBLOBs(Demo record);

    int updateByPrimaryKey(Demo record);
}