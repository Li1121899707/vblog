package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.ReportInParam;
import com.hitwh.vblog.model.ReportModel;
import com.hitwh.vblog.outparam.ArticleOutParam;
import com.hitwh.vblog.outparam.ReportOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:29
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController{
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

    @PostMapping("/admin/query_all")
    public CommonReturnType queryAllReport(@RequestBody ReportInParam reportInParam) throws BusinessException {
        Map<String,Object> result = reportService.queryAllReports(reportInParam.getStart(),
                reportInParam.getEnd());

        int sum = (int) result.get("sum");
        // sum = 0, 读取数据为空，直接返回前台，data为空
        if(sum == 0)
            return CommonReturnType.success();

        ArrayList reportOutParams = (ArrayList) result.get("list");

        int end = reportInParam.getEnd() - reportInParam.getStart() ;
        if(end > reportOutParams.size()) end = reportOutParams.size() + reportInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(reportInParam.getStart(),end,sum,reportOutParams));
    }

    @PostMapping("/admin/query_by_article")
    public CommonReturnType queryReportByArticle(@RequestBody ReportInParam reportInParam) throws BusinessException {
        Map<String,Object> result = reportService.queryReportsByArticleId(reportInParam.getStart(),
                reportInParam.getEnd(), reportInParam.getArticle_id());

        int sum = (int) result.get("sum");
        if(sum == 0)
            return CommonReturnType.success();

        ArrayList reportOutParams = (ArrayList) result.get("list");

        int end = reportInParam.getEnd() - reportInParam.getStart() ;
        if(end > reportOutParams.size()) end = reportOutParams.size() + reportInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(reportInParam.getStart(),end,sum,reportOutParams));
    }

    @PostMapping("/admin/query_by_handle_result")
    public CommonReturnType queryReportByHandleResult(@RequestBody ReportInParam reportInParam) throws BusinessException {
        Map<String,Object> result = reportService.queryReportsByHandleResult(reportInParam.getStart(),
                reportInParam.getEnd(), reportInParam.getHandle_result());

        int sum = (int) result.get("sum");
        // sum = 0, 读取数据为空，直接返回前台，data为空
        if(sum == 0)
            return CommonReturnType.success();

        ArrayList reportOutParams = (ArrayList) result.get("list");

        int end = reportInParam.getEnd() - reportInParam.getStart() ;
        if(end > reportOutParams.size()) end = reportOutParams.size() + reportInParam.getStart() - 1;

        return CommonReturnType.create(PageResponse.create(reportInParam.getStart(),end,sum,reportOutParams));
    }

    @PostMapping("/admin/handle")
    public CommonReturnType handleReport(@RequestBody ReportInParam reportInParam) throws BusinessException {
        ReportModel reportModel = new ReportModel();
        reportModel.setArticleId(reportInParam.getArticle_id());
        reportModel.setHandleResult(reportInParam.getHandle_result());
        reportService.handleReport(reportModel);
        return CommonReturnType.success();
    }


}