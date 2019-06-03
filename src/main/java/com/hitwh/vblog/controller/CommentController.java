package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.inparam.CommentInParam;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.impl.CommentServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{
    @Autowired
    CommentServiceImpl commentService;

    @PostMapping("/article_query")
    public PageResponse queryByArticle(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断是否为空抛出异常
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        //获取需要的评论数据
        List<ComAndUserDo> comAndUserDos =  (ArrayList)commentService.selectDisplayComment(
                commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1,
                commentInParam.getArticle_id()).get("list");
        //获取文章的评论总数
        int sum = (int)commentService.selectDisplayComment(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1, commentInParam.getArticle_id()).get("sum");
        List<CommentOutParam> commentOutParams = new ArrayList<>();
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < comAndUserDos.size();i++){
            CommentOutParam c = new CommentOutParam();
            c.setUser_id(comAndUserDos.get(i).getUserDo().getUserId());
            c.setParent_comment_id(comAndUserDos.get(i).getCommentDo().getParentCommentId());
            c.setComment_time(comAndUserDos.get(i).getCommentDo().getCommentTime());
            c.setComment(comAndUserDos.get(i).getCommentDo().getComment());
            c.setUser_nickname(comAndUserDos.get(i).getUserDo().getNickname());
            c.setArticle_id(comAndUserDos.get(i).getCommentDo().getArticleId());
            commentOutParams.add(c);
        }
        //判断返回给前端的end的值
        int end = commentInParam.getEnd() - commentInParam.getStart() +1;

        if(end > comAndUserDos.size()) end = comAndUserDos.size();

        return PageResponse.create(commentInParam.getStart(),end,sum,commentOutParams);
    }

    @PostMapping("/person_query")
    public PageResponse queryByPerson(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断前端传来的参数是否为空
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        //通过用户的ID和start和end获取用户的评论信息
        List<ComAndUserDo> comAndUserDos =  (ArrayList)commentService.selectDisplayCommentById(
                commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1,
                commentInParam.getUid()).get("list");
        //获取用户的评论总数
        int sum = (int)commentService.selectDisplayCommentById(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1,
                commentInParam.getUid()).get("sum");
        List<CommentOutParam> commentOutParams = new ArrayList<>();

        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < comAndUserDos.size();i++){
            CommentOutParam c = new CommentOutParam();
            c.setUser_id(comAndUserDos.get(i).getUserDo().getUserId());
            c.setParent_comment_id(comAndUserDos.get(i).getCommentDo().getParentCommentId());
            c.setComment_time(comAndUserDos.get(i).getCommentDo().getCommentTime());
            c.setComment(comAndUserDos.get(i).getCommentDo().getComment());
            c.setUser_nickname(comAndUserDos.get(i).getUserDo().getNickname());
            c.setArticle_id(comAndUserDos.get(i).getCommentDo().getArticleId());
            commentOutParams.add(c);
        }
        //通过查询的条数判断返回给前端的end的值
        int end = commentInParam.getEnd() - commentInParam.getStart() +1;

        if(end > comAndUserDos.size()) end = comAndUserDos.size();

        return PageResponse.create(commentInParam.getStart(),end,sum,commentOutParams);
    }

    @PostMapping("/parent_query")
    public CommonReturnType queryByParent(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断输入是否为空
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        return CommonReturnType.create(commentService.selectForParent(commentInParam.getParent_comment_id()));
    }

    @PostMapping("/insert")
    public CommonReturnType commentInsert(@RequestBody CommentInParam commentInParam) throws BusinessException {

        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        CommentModel commentModel = new CommentModel();
        commentModel.setArticleId(commentInParam.getArticle_id());
        commentModel.setComment(commentInParam.getComment());
        commentModel.setCommentId(commentInParam.getComment_id());
        commentModel.setCommentTime(new Date(System.currentTimeMillis()));
        commentModel.setParentCommentId(commentInParam.getParent_comment_id());
        commentModel.setUserId(commentInParam.getUid());
        commentService.insertComment(commentModel);

        return CommonReturnType.success();
    }

    @PostMapping("/hide")
    public CommonReturnType commentHide(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断传入数据是否正确
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        commentService.hideComment(commentInParam.getComment_id());

        return CommonReturnType.success();

    }
}
