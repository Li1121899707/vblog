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

import java.util.*;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{
    @Autowired
    CommentServiceImpl commentService;

    private Integer startGForOut;

    private Integer endForOut;

    private Integer numForOut;

    private List<CommentOutParam> commentOutParamsForOut;
    /**
     *
     * @param commentInParam
     * @return
     * @throws BusinessException
     * 通过文章ID查找评论
     */
    @PostMapping("/article_query")
    public CommonReturnType queryByArticle(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断是否为空抛出异常
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        //获取得到的信息
        Map<String,Object> map = new HashMap<>();
        map = commentService.selectDisplayComment(
                commentInParam.getStart() ,
                commentInParam.getEnd()- commentInParam.getStart() + 1,
                commentInParam.getArticle_id()
        );

        startGForOut = (int)map.get("start");
        endForOut = (int)map.get("end");
        numForOut = (int)map.get("sum");
        commentOutParamsForOut = (ArrayList)map.get("list");

        return CommonReturnType.create(PageResponse.create(startGForOut,
                endForOut,numForOut,commentOutParamsForOut));
    }

    /**
     * 通过用户ID查找评论
     * @param commentInParam
     * @return
     * @throws BusinessException
     */
    @PostMapping("/person_query")
    public CommonReturnType queryByPerson(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //判断前端传来的参数是否为空
        if(commentInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        //获取得到的信息
        Map<String,Object> map = new HashMap<>();
        map = commentService.selectDisplayCommentById(
                commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1,
                commentInParam.getUid());

        startGForOut = (int)map.get("start");
        endForOut = (int)map.get("end");
        numForOut = (int)map.get("sum");
        commentOutParamsForOut = (ArrayList)map.get("list");

        return CommonReturnType.create(PageResponse.create(startGForOut,
                endForOut,numForOut,commentOutParamsForOut));

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
