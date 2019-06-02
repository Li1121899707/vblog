package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ComAndUserDo;
import com.hitwh.vblog.inparam.CommentInParam;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;
    @PostMapping("/article_query")
    public CommonReturnType queryByArticle(@RequestBody CommentInParam commentInParam){
        System.out.println(commentInParam.getStart());
        System.out.println(commentInParam.getEnd());
        System.out.println(commentInParam.getEnd()- commentInParam.getStart());
        //CommonReturnType commonReturnType = new CommonReturnType();
        List<ComAndUserDo> commentDos =  commentService.selectDisplayComment(commentInParam.getStart(),
                commentInParam.getEnd()- commentInParam.getStart(), commentInParam.getArticleId());
        return CommonReturnType.create(commentDos);
    }
}
