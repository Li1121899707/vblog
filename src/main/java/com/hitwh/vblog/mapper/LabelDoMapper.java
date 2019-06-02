package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.LabelDo;

public interface LabelDoMapper {
    int deleteByPrimaryKey(Integer lableId);

    int insert(LabelDo record);

    int insertSelective(LabelDo record);

    LabelDo selectByPrimaryKey(Integer lableId);

    int updateByPrimaryKeySelective(LabelDo record);

    int updateByPrimaryKey(LabelDo record);
}