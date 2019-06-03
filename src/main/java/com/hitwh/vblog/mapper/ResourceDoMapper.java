package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ResourceDo;

public interface ResourceDoMapper {
    int deleteByPrimaryKey(Integer resourceId);

    int insert(ResourceDo record);

    int insertSelective(ResourceDo record);

    ResourceDo selectByPrimaryKey(Integer resourceId);

    int updateByPrimaryKeySelective(ResourceDo record);

    int updateByPrimaryKey(ResourceDo record);
}