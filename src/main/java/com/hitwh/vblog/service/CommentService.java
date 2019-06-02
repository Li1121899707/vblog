package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ComAndUserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    List<ComAndUserDo> selectDisplayComment(@Param("start") Integer start,
                                            @Param("num") Integer num,
                                            @Param("articleId")Integer articleId);
}
