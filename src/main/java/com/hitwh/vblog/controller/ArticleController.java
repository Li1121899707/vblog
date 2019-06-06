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
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @Autowired
    TimestampUtil timestampUtil;

    @PostMapping("/write")
    public CommonReturnType writeArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException {
        ArticleModel articleModel = new ArticleModel();
        //将传入参数转换为Model
        BeanUtils.copyProperties(articleInParam, articleModel);
        //执行Register
        articleService.write(articleModel);
        //返回执行结果
        return CommonReturnType.success();
    }

    @PostMapping("/update")
    public CommonReturnType updateArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException {
        ArticleModel articleModel = new ArticleModel();
        BeanUtils.copyProperties(articleInParam, articleModel);
        articleService.update(articleModel);
        return CommonReturnType.success();
    }

    @PostMapping("/delete")
    public CommonReturnType deleteArticle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        articleService.delete(articleInParam.getArticle_id());
        return CommonReturnType.create(EnumError.SUCCESS);
    }

    @PostMapping("/query_by_id")
    public CommonReturnType queryArticleById(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        return CommonReturnType.create(articleService.queryArticleId(articleInParam.getArticle_id()));
    }

    @PostMapping("/query_by_author")
    public CommonReturnType queryArticleByAuthor(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByAuthorId(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getUid());

        ArrayList articleOutParams = (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @PostMapping("/query_by_title")
    public CommonReturnType queryArticleByTitle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByTitle(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getTitle());

        ArrayList articleOutParams =  (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @PostMapping("/query_by_type")
    public CommonReturnType queryArticleByType(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        Map<String, Object> result = articleService.selectArticleByType(articleInParam.getStart(),
                articleInParam.getEnd(), articleInParam.getType_1());

        ArrayList articleOutParams = (ArrayList) result.get("list");

        int sum = (int) result.get("sum");
        int end = articleInParam.getEnd() - articleInParam.getStart();
        if(end > articleOutParams.size()) end = articleOutParams.size() + articleInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @PostMapping("/query_random")
    public CommonReturnType queryRandomArticle() throws BusinessException{
        return CommonReturnType.create(articleService.queryRandomArticle());
    }
}
