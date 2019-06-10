package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.*;
import com.hitwh.vblog.mapper.ReportRecordDoMapper;
import com.hitwh.vblog.model.ReportModel;
import com.hitwh.vblog.outparam.ReportOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ReportService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/522:17
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRecordDoMapper reportRecordDoMapper;

    @Autowired
    ValidatorImpl validator;

    @Override
    public void addReport(ReportModel reportModel) throws BusinessException {
        if(reportModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(reportModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        ReportRecordDo reportRecordDo = new ReportRecordDo();
        reportRecordDo.setArticleId(reportModel.getArticleId());
        reportRecordDo.setReporterId(reportModel.getReporterId());
        reportRecordDo.setReason(reportModel.getReason());
        reportRecordDo.setReportTime(new Date(System.currentTimeMillis()));

        Integer column = reportRecordDoMapper.insertSelective(reportRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Map<String, Object> queryAllReports(Integer start, Integer end) throws BusinessException {
        Map<String,Object> map = new HashMap<>();
        if(start == null || end == null || start < 0 || end <= start || end == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Integer sum = reportRecordDoMapper.selectAllReportRecordsNum();
        List<ReportAndArticleDo> reportAndArticleDos = reportRecordDoMapper.selectAllReportRecords(start, end-start+1);

        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(reportAndArticleDos));
        return map;
    }

    @Override
    public Map<String, Object> queryReportsByArticleId(Integer start, Integer end, Integer articleId) throws BusinessException {
        Map<String,Object> map = new HashMap<>();
        if(start == null || end == null || start < 0 || end <= start || end == 0 || articleId == null || articleId == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Integer sum = reportRecordDoMapper.selectReportRecordsByArticleIdNum(articleId);
        List<ReportAndArticleDo> reportAndArticleDos = reportRecordDoMapper.selectReportRecordsByArticleId(start, end-start+1, articleId);

        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(reportAndArticleDos));
        return map;
    }

    @Override
    public Map<String, Object> queryReportsByHandleResult(Integer start, Integer end, Integer handleResult) throws BusinessException {
        Map<String,Object> map = new HashMap<>();
        if(start == null || end == null || start < 0 || end <= start || end == 0 || handleResult == null || handleResult > 3 || handleResult < 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        Integer sum = reportRecordDoMapper.selectReportRecordsByHandleResultNum(handleResult);
        List<ReportAndArticleDo> reportAndArticleDos = reportRecordDoMapper.selectReportRecordsByHandleResult(start, end-start +1, handleResult);

        map.put("sum", sum);
        map.put("list", convertToArticleOutParams(reportAndArticleDos));
        return map;
    }


    @Override
    public void handleReport(ReportModel reportModel) throws BusinessException {
        if(reportModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        if(reportModel.getArticleId() == null || reportModel.getArticleId() == 0 || reportModel.getHandleResult() == null
        || reportModel.getHandleResult() <=0 || reportModel.getHandleResult() >2)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ReportRecordDo reportRecordDo = new ReportRecordDo();
        reportRecordDo.setArticleId(reportModel.getArticleId());
        reportRecordDo.setHandleResult(reportModel.getHandleResult());

        Integer column = reportRecordDoMapper.updateByArticleId(reportRecordDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);

    }

    public List<ReportOutParam> convertToArticleOutParams(List<ReportAndArticleDo> reportAndArticleDos){
        List<ReportOutParam> reportOutParams = new ArrayList<>();
        for (ReportAndArticleDo r: reportAndArticleDos
        ) {
            ReportOutParam reportOutParam = new ReportOutParam();
            reportOutParam.setArticle_id(r.getArticleDo().getArticleId());
            reportOutParam.setVirtual_id(r.getArticleDo().getVirtualId());
            reportOutParam.setArticle_name(r.getArticleDo().getTitle());
            reportOutParam.setReporter_id(r.getUserDo().getUserId());
            reportOutParam.setReport_nickname(r.getUserDo().getNickname());
            reportOutParam.setAdmin_id(r.getReportRecordDo().getAdminId());
            reportOutParam.setReason(r.getReportRecordDo().getReason());
            reportOutParam.setReport_time(r.getReportRecordDo().getReportTime().getTime());
            if(r.getReportRecordDo().getHandleTime() != null)
                reportOutParam.setHandle_time(r.getReportRecordDo().getHandleTime().getTime());
            reportOutParam.setHandle_result(r.getReportRecordDo().getHandleResult());
            reportOutParams.add(reportOutParam);
        }
        return reportOutParams;
    }
}
