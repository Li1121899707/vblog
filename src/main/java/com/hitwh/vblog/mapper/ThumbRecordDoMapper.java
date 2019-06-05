package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.ThumbRecordDo;
import org.apache.ibatis.annotations.Param;

public interface ThumbRecordDoMapper {
    int insert(ThumbRecordDo record);

    int insertSelective(ThumbRecordDo record);

    int delete(ThumbRecordDo record);

    int countThumbNum(@Param("articleId") Integer articleId);
}