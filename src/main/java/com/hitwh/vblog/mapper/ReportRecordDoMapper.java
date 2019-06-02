package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ReportRecordDo;

public interface ReportRecordDoMapper {
    int insert(ReportRecordDo record);

    int insertSelective(ReportRecordDo record);
}