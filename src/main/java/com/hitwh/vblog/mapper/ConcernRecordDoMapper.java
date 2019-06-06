package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ConcernAndUserDo;
import com.hitwh.vblog.bean.ConcernRecordDo;

import java.util.List;

public interface ConcernRecordDoMapper {
    int insert(ConcernRecordDo record);

    int insertSelective(ConcernRecordDo record);

    int delete(ConcernRecordDo concernRecordDo);

    int selectIsConcerned(ConcernRecordDo concernRecordDo);

    List<ConcernAndUserDo> selectFollower(Integer userId);

    List<ConcernAndUserDo> selectTarget(Integer userId);
}