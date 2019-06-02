package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.CollectionRecordDo;

public interface CollectionRecordDoMapper {
    int insert(CollectionRecordDo record);

    int insertSelective(CollectionRecordDo record);
}