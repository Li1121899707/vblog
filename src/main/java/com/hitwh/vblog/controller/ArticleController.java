package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.inparam.ArticleInParam;
import com.hitwh.vblog.model.ArticleModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.ArticleService;
import com.hitwh.vblog.util.LoginRequired;
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @Autowired
    TimestampUtil timestampUtil;

    @LoginRequired
    @PostMapping("/write")
    public CommonReturnType writeArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException {
        ArticleModel articleModel = new ArticleModel();
        BeanUtils.copyProperties(articleInParam, articleModel);
        articleModel.setArticleAbstract(articleInParam.getArticle_abstract());
        articleService.write(articleModel);
        return CommonReturnType.success();
    }

    @LoginRequired
    @PostMapping("/update")
    public CommonReturnType updateArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException {
        ArticleModel articleModel = new ArticleModel();
        BeanUtils.copyProperties(articleInParam, articleModel);
        articleModel.setArticleAbstract(articleInParam.getArticle_abstract());
        articleService.update(articleModel);
        return CommonReturnType.success();
    }

    @LoginRequired
    @PostMapping("/delete")
    public CommonReturnType deleteArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        articleService.delete(articleInParam.getArticle_id(), articleInParam.getUid());
        return CommonReturnType.success();
    }


    @RequestMapping("/query_by_id")
    public CommonReturnType queryArticleById(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        return CommonReturnType.create(articleService.queryArticleId(articleInParam.getArticle_id()));
    }

    @RequestMapping("/query_by_author")
    public CommonReturnType queryArticleByAuthor(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByAuthorId(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getAuthor_id());

        ArrayList articleOutParams = (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @RequestMapping("/query_by_title")
    public CommonReturnType queryArticleByTitle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByTitle(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getTitle());

        ArrayList articleOutParams =  (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @RequestMapping("/query_by_type")
    public CommonReturnType queryArticleByType(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByType(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getType_1());

        ArrayList articleOutParams = (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @RequestMapping("/query_random")
    public CommonReturnType queryRandomArticle() throws BusinessException{
        return CommonReturnType.create(articleService.queryRandomArticle().getArticle_id());
    }

    @RequestMapping("/query_all")
    public CommonReturnType queryAllArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        return CommonReturnType.create(articleService.selectAllArticle(articleInParam.getStart(),
                articleInParam.getEnd()));
    }

    @RequestMapping("/recommend")
    public CommonReturnType recommend() throws BusinessException{
        return CommonReturnType.create(articleService.recommend());
    }
}
