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
        List<ArticleAndUserDo> articleAndUserDos =  (ArrayList)articleService.selectArticleById(articleInParam.getStart(),
                articleInParam.getEnd() - articleInParam.getStart() + 1, articleInParam.getUid()).get("list");
        int sum = (int)articleService.selectArticleById(articleInParam.getStart(),
                articleInParam.getEnd()- articleInParam.getStart() + 1, articleInParam.getUid()).get("sum");
        List<ArticleOutParam> articleOutParams = new ArrayList<>();
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < articleAndUserDos.size();i++){
            ArticleOutParam articleOutParam = new ArticleOutParam();
            articleOutParam.setArticle_id(articleAndUserDos.get(i).getArticleDo().getVirtualId());
            articleOutParam.setTitle(articleAndUserDos.get(i).getArticleDo().getTitle());
            articleOutParam.setAuthor_id(articleAndUserDos.get(i).getArticleDo().getArticleId());
            articleOutParam.setAuthor_nickname(articleAndUserDos.get(i).getUserDo().getNickname());
            articleOutParam.setType_1(articleAndUserDos.get(i).getArticleDo().getType1());
            articleOutParam.setType_2(articleAndUserDos.get(i).getArticleDo().getType2());
            articleOutParam.setLabel_name1(articleAndUserDos.get(i).getLabelDo().getLabelName());//暂时只能返回一个标签
            articleOutParam.setCover(articleAndUserDos.get(i).getResourceDo().getUrl());//另一张表的url字段
            articleOutParam.setHidden(articleAndUserDos.get(i).getArticleDo().getHidden());
            articleOutParam.setContent(articleAndUserDos.get(i).getArticleDo().getContent());
            articleOutParam.setArticleAbstract(articleAndUserDos.get(i).getArticleDo().getArticleAbstract());
            articleOutParam.setRelease_time(articleAndUserDos.get(i).getArticleDo().getReleaseTime().getTime());//用getTime将Date转为long型
            articleOutParam.setThumb(articleAndUserDos.get(i).getArticleDynamicDo().getThumbNum());
            articleOutParam.setReading(articleAndUserDos.get(i).getArticleDynamicDo().getReadingNum());
            articleOutParams.add(articleOutParam);
        }
        int end = articleInParam.getEnd() - articleInParam.getStart() +1;

        if(end > articleAndUserDos.size()) end = articleAndUserDos.size();

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @PostMapping("/query_by_title")
    public CommonReturnType queryArticleByTitle(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        List<ArticleAndUserDo> articleAndUserDos =  (ArrayList)articleService.selectArticleByTitle(articleInParam.getStart(),
                articleInParam.getEnd() - articleInParam.getStart() + 1, articleInParam.getTitle()).get("list");
        int sum = (int)articleService.selectArticleByTitle(articleInParam.getStart(),
                articleInParam.getEnd()- articleInParam.getStart() + 1, articleInParam.getTitle()).get("sum");
        List<ArticleOutParam> articleOutParams = new ArrayList<>();
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < articleAndUserDos.size();i++){
            ArticleOutParam articleOutParam = new ArticleOutParam();
            articleOutParam.setArticle_id(articleAndUserDos.get(i).getArticleDo().getVirtualId());
            articleOutParam.setTitle(articleAndUserDos.get(i).getArticleDo().getTitle());
            articleOutParam.setAuthor_id(articleAndUserDos.get(i).getArticleDo().getArticleId());
            articleOutParam.setAuthor_nickname(articleAndUserDos.get(i).getUserDo().getNickname());
            articleOutParam.setType_1(articleAndUserDos.get(i).getArticleDo().getType1());
            articleOutParam.setType_2(articleAndUserDos.get(i).getArticleDo().getType2());
            articleOutParam.setLabel_name1(articleAndUserDos.get(i).getLabelDo().getLabelName());//暂时只能返回一个标签
            articleOutParam.setCover(articleAndUserDos.get(i).getResourceDo().getUrl());//另一张表的url字段
            articleOutParam.setHidden(articleAndUserDos.get(i).getArticleDo().getHidden());
            articleOutParam.setContent(articleAndUserDos.get(i).getArticleDo().getContent());
            articleOutParam.setArticleAbstract(articleAndUserDos.get(i).getArticleDo().getArticleAbstract());
            articleOutParam.setRelease_time(articleAndUserDos.get(i).getArticleDo().getReleaseTime().getTime());//用getTime将Date转为long型
            articleOutParam.setThumb(articleAndUserDos.get(i).getArticleDynamicDo().getThumbNum());
            articleOutParam.setReading(articleAndUserDos.get(i).getArticleDynamicDo().getReadingNum());
            articleOutParams.add(articleOutParam);
        }
        int end = articleInParam.getEnd() - articleInParam.getStart() +1;

        if(end > articleAndUserDos.size()) end = articleAndUserDos.size();

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }

    @PostMapping("/query_by_type")
    public CommonReturnType queryArticleByType(@RequestBody ArticleInParam articleInParam) throws BusinessException{
        List<ArticleAndUserDo> articleAndUserDos =  (ArrayList)articleService.selectArticleByType(articleInParam.getStart(),
                articleInParam.getEnd() - articleInParam.getStart() + 1, articleInParam.getType_1()).get("list");
        int sum = (int)articleService.selectArticleByType(articleInParam.getStart(),
                articleInParam.getEnd()- articleInParam.getStart() + 1, articleInParam.getType_1()).get("sum");
        List<ArticleOutParam> articleOutParams = new ArrayList<>();
        //将通过service获取的数据换成要输出的形式
        for(int i =0;i < articleAndUserDos.size();i++){
            ArticleOutParam articleOutParam = new ArticleOutParam();
            articleOutParam.setArticle_id(articleAndUserDos.get(i).getArticleDo().getVirtualId());
            articleOutParam.setTitle(articleAndUserDos.get(i).getArticleDo().getTitle());
            articleOutParam.setAuthor_id(articleAndUserDos.get(i).getArticleDo().getArticleId());
            articleOutParam.setAuthor_nickname(articleAndUserDos.get(i).getUserDo().getNickname());
            articleOutParam.setType_1(articleAndUserDos.get(i).getArticleDo().getType1());
            articleOutParam.setType_2(articleAndUserDos.get(i).getArticleDo().getType2());
            articleOutParam.setLabel_name1(articleAndUserDos.get(i).getLabelDo().getLabelName());//暂时只能返回一个标签
            articleOutParam.setCover(articleAndUserDos.get(i).getResourceDo().getUrl());//另一张表的url字段
            articleOutParam.setHidden(articleAndUserDos.get(i).getArticleDo().getHidden());
            articleOutParam.setContent(articleAndUserDos.get(i).getArticleDo().getContent());
            articleOutParam.setArticleAbstract(articleAndUserDos.get(i).getArticleDo().getArticleAbstract());
            articleOutParam.setRelease_time(articleAndUserDos.get(i).getArticleDo().getReleaseTime().getTime());//用getTime将Date转为long型
            articleOutParam.setThumb(articleAndUserDos.get(i).getArticleDynamicDo().getThumbNum());
            articleOutParam.setReading(articleAndUserDos.get(i).getArticleDynamicDo().getReadingNum());
            articleOutParams.add(articleOutParam);
        }
        int end = articleInParam.getEnd() - articleInParam.getStart() +1;

        if(end > articleAndUserDos.size()) end = articleAndUserDos.size();

        return CommonReturnType.create(PageResponse.create(articleInParam.getStart(),end,sum,articleOutParams));
    }
}
