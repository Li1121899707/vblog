package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ReportInParam;
import com.hitwh.vblog.model.ReportModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:29
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping("/article")
    public CommonReturnType reportArticle(@RequestBody ReportInParam reportInParam) throws BusinessException {
        ReportModel reportModel = new ReportModel();
        reportModel.setArticleId(reportInParam.getArticle_id());
        reportModel.setReporterId(reportInParam.getUid());
        reportModel.setReason(reportInParam.getReason());
        reportService.addReport(reportModel);
        return CommonReturnType.success();
    }


}
