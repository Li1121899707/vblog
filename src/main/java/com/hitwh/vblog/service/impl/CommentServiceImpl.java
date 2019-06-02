package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.bean.CommentDo;
import com.hitwh.vblog.mapper.CommentDoMapper;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.CommentService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDoMapper commentDoMapper;
    @Autowired
    ValidatorImpl validator;

    @Override
    public Map<String,Object> selectDisplayComment(Integer start, Integer num, Integer articleId) {
        //if(commentDoMapper.selectDisplayComment(start,num, articleId) == null)

        Map<String,Object> map = new HashMap<>();
        int sum = commentDoMapper.selectByArticleId(articleId).size();
        map.put("sum",sum);
        map.put("list",commentDoMapper.selectDisplayComment(start,num, articleId));
        return map;
    }

    @Override
    public Map<String, Object> selectDisplayCommentById(Integer start, Integer num, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        int sum = commentDoMapper.selectByUserId(userId).size();
        map.put("sum",sum);
        map.put("list",commentDoMapper.selectDisplayCommentById(start,num, userId));
        return map;
    }

    @Override
    public ComAndUserDo selectForParent(Integer parent_comment_id) {
        return commentDoMapper.selectForParent(parent_comment_id);
    }

    @Override
    public void insertComment(CommentModel commentModel) throws BusinessException {
        if(commentModel == null){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult result = validator.validate(commentModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CommentDo commentDo = new CommentDo();
        BeanUtils.copyProperties(commentModel,commentDo);
        commentDoMapper.insertSelective(commentDo);


        // 需要加验证！！！！！！！！！！！！！！！！！！！！！
    }
}
