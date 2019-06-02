package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ConcernRecordDo;

public interface ConcernRecordDoMapper {
    int insert(ConcernRecordDo record);

    int insertSelective(ConcernRecordDo record);
}