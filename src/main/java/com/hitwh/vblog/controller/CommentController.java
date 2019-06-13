package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.inparam.CommentInParam;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.ArticleService;
import com.hitwh.vblog.service.impl.CommentServiceImpl;
import com.hitwh.vblog.util.LoginRequired;
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

    @Autowired
    ArticleService articleService;
    /**
     *
     * @param commentInParam
     * @return
     * @throws BusinessException
     * 通过文章ID查找评论
     */
    @RequestMapping("/query_by_article")
    public CommonReturnType queryByArticle(@RequestBody CommentInParam commentInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(commentInParam.getArticle_id());
        //获取得到的信息
        Map<String,Object> map = new HashMap<>();
        map = commentService.selectDisplayComment(
                commentInParam.getStart() ,
                commentInParam.getEnd(),
                articleIdInteger
        );

        if(map == null)
            return CommonReturnType.create(PageResponse.createBlank());

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
    @RequestMapping("/query_by_user")
    public CommonReturnType queryByPerson(@RequestBody CommentInParam commentInParam) throws BusinessException {
        //获取得到的信息
        Map<String,Object> map = new HashMap<>();
        map = commentService.selectDisplayCommentById(
                commentInParam.getStart(),
                commentInParam.getEnd(),
                commentInParam.getUser_id());

        if(map == null)
            return CommonReturnType.create(PageResponse.createBlank());

        startGForOut = (int)map.get("start");
        endForOut = (int)map.get("end");
        numForOut = (int)map.get("sum");
        commentOutParamsForOut = (ArrayList)map.get("list");

        return CommonReturnType.create(PageResponse.create(startGForOut,
                endForOut,numForOut,commentOutParamsForOut));

    }

    @RequestMapping("/query_by_parent")
    public CommonReturnType queryByParent(@RequestBody CommentInParam commentInParam) throws BusinessException {
        return CommonReturnType.create(commentService.selectForParent(commentInParam.getParent_comment_id()));
    }

    //@LoginRequired
    @RequestMapping("/query_for_user")
    public CommonReturnType queryForUser(@RequestBody CommentInParam commentInParam) throws BusinessException {
        return CommonReturnType.create(commentService.queryForUser(commentInParam.getUid()));
    }

    @LoginRequired
    @PostMapping("/insert")
    public CommonReturnType commentInsert(@RequestBody CommentInParam commentInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(commentInParam.getArticle_id());
        CommentModel commentModel = new CommentModel();
        commentModel.setArticleId(articleIdInteger);
        commentModel.setComment(commentInParam.getComment());
        commentModel.setCommentId(commentInParam.getComment_id());
        commentModel.setCommentTime(new Date(System.currentTimeMillis()));
        commentModel.setParentCommentId(commentInParam.getParent_comment_id());
        commentModel.setUserId(commentInParam.getUid());
        commentService.insertComment(commentModel);

        return CommonReturnType.success();
    }

//    @LoginRequired
//    @PostMapping("/hide")
//    public CommonReturnType commentHide(@RequestBody CommentInParam commentInParam) throws BusinessException {
//        commentService.hideComment(commentInParam.getComment_id());
//        return CommonReturnType.success();
//    }

    @LoginRequired(admin = true)
    @PostMapping("/admin/hide")
    public CommonReturnType commentAdminHide(@RequestBody CommentInParam commentInParam) throws BusinessException {
        commentService.commentAdminHide(commentInParam.getUid(),commentInParam.getComment_id());
        return CommonReturnType.success();
    }


}
