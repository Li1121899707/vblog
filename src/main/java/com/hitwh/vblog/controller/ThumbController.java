package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ThumbInParam;
import com.hitwh.vblog.model.ThumbModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.service.ArticleService;
import com.hitwh.vblog.service.ThumbService;
import com.hitwh.vblog.service.impl.ThumbServiceImpl;
import com.hitwh.vblog.util.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liysuzy
 * @description: 点赞Controller
 * @date 2019/6/517:11
 */
@RestController
@RequestMapping("/thumb")
public class ThumbController extends BaseController {
    @Autowired
    ThumbService thumbService;
    @Autowired
    ArticleService articleService;

    // 添加点赞
    @LoginRequired
    @PostMapping("/add")
    public CommonReturnType insertThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(thumbInParam.getArticle_id());
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(articleIdInteger);
        thumbModel.setUserId(thumbInParam.getUid());
        thumbService.insertThumbRecord(thumbModel);

        return CommonReturnType.success();
    }

    // 取消点赞
    @LoginRequired
    @PostMapping("/delete")
    public CommonReturnType deleteThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(thumbInParam.getArticle_id());
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(articleIdInteger);
        thumbModel.setUserId(thumbInParam.getUid());
        thumbService.deleteThumbRecord(thumbModel);

        return CommonReturnType.success();
    }

    // 查看点赞数
    @RequestMapping("/query_thumb_number")
    public CommonReturnType queryThumbNumber(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(thumbInParam.getArticle_id());
        Map<String,Object> map = new HashMap<>();
        map.put("thumb_num",thumbService.countThumbNum(articleIdInteger));
        return CommonReturnType.create(map);
    }

    // 查看是否点赞
    @RequestMapping("/query_if_thumb")
    public CommonReturnType queryIfThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        Integer articleIdInteger = articleService.getArticleId(thumbInParam.getArticle_id());
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(articleIdInteger);
        thumbModel.setUserId(thumbInParam.getUid());
        Map<String,Object> map = new HashMap<>();
        map.put("validate_result",thumbService.queryIfThumb(thumbModel));
        return CommonReturnType.create(map);
    }

}
