package com.hitwh.vblog.service;

import com.hitwh.vblog.model.ReportModel;
import com.hitwh.vblog.outparam.ReportOutParam;
import com.hitwh.vblog.response.BusinessException;

import java.util.List;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:16
 */
public interface ReportService {
    void addReport(ReportModel reportModel) throws BusinessException;

    List<ReportOutParam> queryAllReports(Integer start, Integer end) throws BusinessException;

    ReportOutParam queryReportByArticleId(Integer start, Integer end, Integer articleId);

    List<ReportOutParam> queryReportsByHandleResult(Integer start, Integer end);

    Integer handleReport(ReportModel reportModel);
}
