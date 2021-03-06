package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ConcernInParam;
import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.outparam.ConcernOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.ConcernService;
import com.hitwh.vblog.util.LoginRequired;
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
@RequestMapping("/concern")
public class ConcernController extends BaseController {
    @Autowired
    ConcernService concernService;

    @LoginRequired
    @PostMapping("/add")
    public CommonReturnType addConcern(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        ConcernModel concernModel = new ConcernModel();
        concernModel.setUserId(concernInParam.getUid());
        concernModel.setTargetId(concernInParam.getTarget_id());
        concernService.insert(concernModel);
        return CommonReturnType.success();
    }

    @LoginRequired
    @PostMapping("/delete")
    public CommonReturnType deleteConcern(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        ConcernModel concernModel = new ConcernModel();
        concernModel.setUserId(concernInParam.getUid());
        concernModel.setTargetId(concernInParam.getTarget_id());
        concernService.delete(concernModel);
        return CommonReturnType.success();
    }

    @LoginRequired
    @RequestMapping("/query_concern_follower")
    public CommonReturnType queryConcernFollower(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        Map<String, Object> map = concernService.queryFollower(concernInParam.getStart(), concernInParam.getEnd(), concernInParam.getUid());
        if(map == null)
            return CommonReturnType.create(PageResponse.createBlank());

        ArrayList concernOutParams = (ArrayList)map.get("list");
        int sum = (int)map.get("sum");
        if(concernOutParams.size() ==
                concernInParam.getEnd() - concernInParam.getStart() + 1)
            return CommonReturnType.create(PageResponse.create(concernInParam.getStart(),
                    concernInParam.getEnd(),sum,concernOutParams));
        else
            return CommonReturnType.create(PageResponse.create(concernInParam.getStart(),
                    concernInParam.getStart() + concernOutParams.size() - 1
                    ,sum,concernOutParams));
    }

    @LoginRequired
    @RequestMapping("/query_concern_target")
    public CommonReturnType queryConcernTarget(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        Map<String, Object> map = concernService.queryTarget(concernInParam.getStart(), concernInParam.getEnd(), concernInParam.getUid());
        if(map == null)
            return CommonReturnType.create(PageResponse.createBlank());

        ArrayList concernOutParams = (ArrayList)map.get("list");
        int sum = (int)map.get("sum");
        if(concernOutParams.size() ==
                concernInParam.getEnd() - concernInParam.getStart() + 1)
            return CommonReturnType.create(PageResponse.create(concernInParam.getStart(),
                    concernInParam.getEnd(),sum,concernOutParams));
        else
            return CommonReturnType.create(PageResponse.create(concernInParam.getStart(),
                    concernInParam.getStart() + concernOutParams.size() - 1
                    ,sum,concernOutParams));

    }

    @LoginRequired
    @PostMapping("/is_concerned")
    public CommonReturnType isConcerned(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        ConcernModel concernModel = new ConcernModel();
        concernModel.setTargetId(concernInParam.getTarget_id());
        concernModel.setUserId(concernInParam.getUid());
        Map<String, Object> result = new HashMap<>();
        result.put("validate_result",concernService.isConcerned(concernModel));
        return CommonReturnType.create(result);
    }

}
