package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.LabelDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelDoMapper {
    int deleteByPrimaryKey(Integer lableId);

    int insert(LabelDo record);

    int insertSelective(LabelDo record);

    LabelDo selectByPrimaryKey(Integer lableId);

    int updateByPrimaryKeySelective(LabelDo record);

    int updateByPrimaryKey(LabelDo record);

    List<LabelDo> selectAllInterestsWithPage(@Param("start") Integer start, @Param("num") Integer num);

    List<LabelDo> selectAllInterests();

    int interestsPageCount();
}