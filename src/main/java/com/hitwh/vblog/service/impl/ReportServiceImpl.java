package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ReportRecordDo;
import com.hitwh.vblog.bean.ThumbRecordDo;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

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
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

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
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }

    @Override
    public List<ReportOutParam> queryAllReports(Integer start, Integer end) throws BusinessException {
        if(start == null || end == null || start < 0 || end <= start || end == 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        return null;
    }

    @Override
    public ReportOutParam queryReportByArticleId(Integer start, Integer end, Integer articleId) {
        return null;
    }

    @Override
    public List<ReportOutParam> queryReportsByHandleResult(Integer start, Integer end) {
        return null;
    }

    @Override
    public Integer handleReport(ReportModel reportModel) {
        return null;
    }
}
