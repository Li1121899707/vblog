package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.mapper.CommentDoMapper;
import com.hitwh.vblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDoMapper commentDoMapper;
    @Override
    public List<ComAndUserDo> selectDisplayComment(Integer start, Integer num, Integer articleId) {

        return commentDoMapper.selectDisplayComment(start,num, articleId);
    }
}
