package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ThumbInParam;
import com.hitwh.vblog.model.ThumbModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.service.ThumbService;
import com.hitwh.vblog.service.impl.ThumbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 添加点赞
    @PostMapping("/add")
    public CommonReturnType insertThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(thumbInParam.getArticle_id());
        thumbModel.setUserId(thumbInParam.getUid());
        thumbService.insertThumbRecord(thumbModel);
        return CommonReturnType.success();
    }

    // 取消点赞
    @PostMapping("/delete")
    public CommonReturnType deleteThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(thumbInParam.getArticle_id());
        thumbModel.setUserId(thumbInParam.getUid());
        thumbService.deleteThumbRecord(thumbModel);
        return CommonReturnType.success();
    }

    // 查看点赞数
    @PostMapping("/query_thumb_number")
    public CommonReturnType queryThumbNumber(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        return CommonReturnType.create(thumbService.countThumbNum(thumbInParam.getArticle_id()));
    }

    // 查看是否点赞
    @PostMapping("/query_if_thumb")
    public CommonReturnType queryIfThumb(@RequestBody ThumbInParam thumbInParam) throws BusinessException {
        ThumbModel thumbModel = new ThumbModel();
        thumbModel.setArticleId(thumbInParam.getArticle_id());
        thumbModel.setUserId(thumbInParam.getUid());
        return CommonReturnType.create(thumbService.queryIfThumb(thumbModel));
    }

}
