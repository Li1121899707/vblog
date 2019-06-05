package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ConcernInParam;
import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concern")
public class ConcernController extends BaseController {
    @Autowired
    ConcernService concernService;

    @PostMapping("/add")
    public CommonReturnType addConcern(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        ConcernModel concernModel = new ConcernModel();
        concernModel.setUserId(concernInParam.getUser_id());
        concernModel.setTargetId(concernInParam.getTarget_id());
        concernService.insert(concernModel);
        return CommonReturnType.success();
    }

    @PostMapping("/delete")
    public CommonReturnType deleteConcern(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        ConcernModel concernModel = new ConcernModel();
        concernModel.setUserId(concernInParam.getUser_id());
        concernModel.setTargetId(concernInParam.getTarget_id());
        concernService.delete(concernModel);
        return CommonReturnType.success();
    }

    @PostMapping("/query_concern_follower")
    public CommonReturnType queryConcernFollower(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        Integer userId = concernInParam.getUser_id();
        return CommonReturnType.create(concernService.queryFollower(userId));
    }

    @PostMapping("/query_concern_target")
    public CommonReturnType queryConcernTarget(@RequestBody ConcernInParam concernInParam) throws BusinessException{
        Integer targetId = concernInParam.getTarget_id();
        return CommonReturnType.create(concernService.queryTarget(targetId));
    }
}
