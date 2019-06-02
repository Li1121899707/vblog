package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.inparam.CommentInParam;
import com.hitwh.vblog.model.CommentModel;
import com.hitwh.vblog.outparam.CommentOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
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
    public PageResponse queryByArticle(@RequestBody CommentInParam commentInParam){
        System.out.println(commentInParam.getStart());
        System.out.println(commentInParam.getEnd());
        System.out.println(commentInParam.getEnd()- commentInParam.getStart());

        List<ComAndUserDo> comAndUserDos =  (ArrayList)commentService.selectDisplayComment(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1, commentInParam.getArticleId()).get("list");
        int sum = (int)commentService.selectDisplayComment(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1, commentInParam.getArticleId()).get("sum");
        List<CommentOutParam> commentOutParams = new ArrayList<>();
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < comAndUserDos.size();i++){
            CommentOutParam c = new CommentOutParam();
            c.setUserId(comAndUserDos.get(i).getUserDo().getUserId());
            c.setParentCommentId(comAndUserDos.get(i).getCommentDo().getParentCommentId());
            c.setCommentTime(comAndUserDos.get(i).getCommentDo().getCommentTime());
            c.setComment(comAndUserDos.get(i).getCommentDo().getComment());
            c.setUserNickname(comAndUserDos.get(i).getUserDo().getNickname());
            c.setArticleId(comAndUserDos.get(i).getCommentDo().getArticleId());
            commentOutParams.add(c);
        }
        int end = commentInParam.getEnd() - commentInParam.getStart() +1;

        if(end > comAndUserDos.size()) end = comAndUserDos.size();

        return PageResponse.create(commentInParam.getStart(),end,sum,commentOutParams);
    }

    @PostMapping("/person_query")
    public PageResponse queryByPerson(@RequestBody CommentInParam commentInParam){
        List<ComAndUserDo> comAndUserDos =  (ArrayList)commentService.selectDisplayCommentById(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1, commentInParam.getUid()).get("list");
        int sum = (int)commentService.selectDisplayCommentById(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart() + 1, commentInParam.getUid()).get("sum");
        List<CommentOutParam> commentOutParams = new ArrayList<>();
        System.out.println(comAndUserDos.size());
        System.out.println(commentInParam.getUid());
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < comAndUserDos.size();i++){
            CommentOutParam c = new CommentOutParam();
            c.setUserId(comAndUserDos.get(i).getUserDo().getUserId());
            c.setParentCommentId(comAndUserDos.get(i).getCommentDo().getParentCommentId());
            c.setCommentTime(comAndUserDos.get(i).getCommentDo().getCommentTime());
            c.setComment(comAndUserDos.get(i).getCommentDo().getComment());
            c.setUserNickname(comAndUserDos.get(i).getUserDo().getNickname());
            c.setArticleId(comAndUserDos.get(i).getCommentDo().getArticleId());
            commentOutParams.add(c);
        }
        int end = commentInParam.getEnd() - commentInParam.getStart() +1;

        if(end > comAndUserDos.size()) end = comAndUserDos.size();

        return PageResponse.create(commentInParam.getStart(),end,sum,commentOutParams);
    }

    @PostMapping("/parent_query")
    public CommonReturnType parent_comment_id(@RequestBody CommentInParam commentInParam){

        CommentOutParam commentOutParam = new CommentOutParam();
        ComAndUserDo comAndUserDo = commentService.selectForParent(commentInParam.getCommentId());
        commentOutParam.setArticleId(comAndUserDo.getCommentDo().getArticleId());
        commentOutParam.setUserNickname(comAndUserDo.getUserDo().getNickname());
        commentOutParam.setComment(comAndUserDo.getCommentDo().getComment());
        commentOutParam.setCommentTime(comAndUserDo.getCommentDo().getCommentTime());
        commentOutParam.setUserId(comAndUserDo.getUserDo().getUserId());
        commentOutParam.setParentCommentId(comAndUserDo.getCommentDo().getParentCommentId());
        return CommonReturnType.create(commentOutParam);
    }

    @PostMapping("/insert")
    public CommonReturnType commentInsert(@RequestBody CommentInParam commentInParam) throws BusinessException {
        CommentModel commentModel = new CommentModel();
        commentModel.setArticleId(commentInParam.getArticleId());
        commentModel.setComment(commentInParam.getComment());
        commentModel.setCommentId(commentInParam.getCommentId());
        commentModel.setCommentTime(new Date(System.currentTimeMillis()));
        commentModel.setParentCommentId(commentInParam.getParentCommentId());
        commentModel.setUserId(commentInParam.getUid());
        commentService.insertComment(commentModel);
        return CommonReturnType.success();
    }
}
