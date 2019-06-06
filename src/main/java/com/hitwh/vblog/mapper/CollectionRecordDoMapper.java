package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.CollectionRecordDo;
import org.apache.ibatis.annotations.Param;

public interface CollectionRecordDoMapper {
    int insert(CollectionRecordDo record);

    int insertSelective(CollectionRecordDo record);

    int delete(CollectionRecordDo record);

    int selectNumByUserId(@Param("userId")Integer userId);

    CollectionRecordDo selectIfCollected(@Param("userId")Integer userId,
                          @Param("articleId")Integer articleId);
}