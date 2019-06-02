package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ThumbRecordDo;

public interface ThumbRecordDoMapper {
    int insert(ThumbRecordDo record);

    int insertSelective(ThumbRecordDo record);
}