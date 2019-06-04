package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.BaseInParam;
import com.hitwh.vblog.inparam.LabelInParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/label")
public class LabelController extends BaseController {

    @Autowired
    LabelService labelService;

    @PostMapping("/query_by_id")
    public CommonReturnType queryLabelById(@RequestBody LabelInParam labelInParam) throws BusinessException {
        if(labelInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        return CommonReturnType.create(labelService.queryLabelById(labelInParam.getLabel_id()));
    }

    @PostMapping("/query_all_interests")
    public CommonReturnType queryAllInterests(@RequestBody LabelInParam labelInParam) throws BusinessException {
        return CommonReturnType.create(labelService.queryAllInterests(labelInParam.getStart(), labelInParam.getEnd()));
    }



}