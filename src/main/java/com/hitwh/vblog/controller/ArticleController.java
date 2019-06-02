package com.hitwh.vblog.controller;

import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    
    @PostMapping("/write")
    public CommonReturnType writeArticle(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/update")
    public CommonReturnType updateArticle(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/delete")
    public CommonReturnType deleteArticle(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/query_by_id")
    public CommonReturnType queryArticleById(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/query_by_author")
    public CommonReturnType queryArticleByAuthor(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/query_by_type")
    public CommonReturnType queryArticleByType(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/query_by_title")
    public CommonReturnType queryArticleByTitle(){
        return CommonReturnType.create(EnumError.SUCCESS);
    }
}
