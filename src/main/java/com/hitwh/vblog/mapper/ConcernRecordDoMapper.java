package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ConcernAndUserDo;
import com.hitwh.vblog.bean.ConcernRecordDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConcernRecordDoMapper {
    int insert(ConcernRecordDo record);

    int insertSelective(ConcernRecordDo record);

    int delete(ConcernRecordDo concernRecordDo);

    int selectIsConcerned(ConcernRecordDo concernRecordDo);

    List<ConcernAndUserDo> selectFollower(@Param("start") Integer start,
                                          @Param("num") Integer num,
                                          @Param("userId") Integer userId);

    int selectFollowerNum(@Param("userId") Integer userId);

    List<ConcernAndUserDo> selectTarget(@Param("start") Integer start,
                                        @Param("num") Integer num,
                                        @Param("userId") Integer userId);

    int selectTargetNum(@Param("userId") Integer userId);
}