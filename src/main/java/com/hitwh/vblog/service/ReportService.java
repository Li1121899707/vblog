package com.hitwh.vblog.service;

import com.hitwh.vblog.model.ReportModel;
import com.hitwh.vblog.outparam.ReportOutParam;
import com.hitwh.vblog.response.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:16
 */
public interface ReportService {
    void addReport(ReportModel reportModel) throws BusinessException;

    Map<String, Object> queryAllReports(Integer start, Integer end) throws BusinessException;

    Map<String, Object> queryReportsByArticleId(Integer start, Integer end, Integer articleId) throws BusinessException;

    Map<String, Object> queryReportsByHandleResult(Integer start, Integer end, Integer handleResult) throws BusinessException;

    void handleReport(ReportModel reportModel) throws BusinessException;
}
