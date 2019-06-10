package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ConcernAndUserDo;
import com.hitwh.vblog.bean.ConcernRecordDo;
import com.hitwh.vblog.mapper.ConcernRecordDoMapper;
import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.outparam.ConcernOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ConcernService;
import com.hitwh.vblog.util.TimestampUtil;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConcernServiceImpl implements ConcernService {
    @Autowired
    ConcernRecordDoMapper concernRecordDoMapper;
    @Autowired
    ValidatorImpl validator;
    @Override
    public void insert(ConcernModel concernModel) throws BusinessException {
        if(concernModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(concernModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        ConcernRecordDo concernRecordDo = new ConcernRecordDo();
        concernRecordDo.setFollowerId(concernModel.getUserId());
        concernRecordDo.setTargetId(concernModel.getTargetId());
        Timestamp timestamp = TimestampUtil.getNowTime();
        concernRecordDo.setConcernTime(timestamp);

        int writeResult = concernRecordDoMapper.insertSelective(concernRecordDo);
        if (writeResult != 1)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void delete(ConcernModel concernModel) throws BusinessException {
        if(concernModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(concernModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        ConcernRecordDo concernRecordDo = new ConcernRecordDo();
        concernRecordDo.setFollowerId(concernModel.getUserId());
        concernRecordDo.setTargetId(concernModel.getTargetId());
        int writeResult = concernRecordDoMapper.delete(concernRecordDo);
        if (writeResult != 1)
            throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Map<String, Object> queryFollower(Integer start, Integer end, Integer userId) throws BusinessException {
        if(start == null || end == null || userId == null || start < 0 || end < start || userId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        List<ConcernAndUserDo> concernAndUserDos = concernRecordDoMapper.selectFollower(start, end-start+1, userId);
        List<ConcernOutParam> concernOutParams = new ArrayList<>();
        for (ConcernAndUserDo concernAndUserDo: concernAndUserDos) {
            ConcernOutParam concernOutParam = new ConcernOutParam();
            concernOutParam.setUser_id(concernAndUserDo.getConcernRecordDo().getFollowerId());
            concernOutParam.setUser_nickname(concernAndUserDo.getUserDo().getNickname());
            concernOutParams.add(concernOutParam);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", concernOutParams);
        map.put("sum", concernRecordDoMapper.selectFollowerNum(userId));
        return map;
    }

    @Override
    public Map<String, Object> queryTarget(Integer start, Integer end, Integer userId) throws BusinessException {
        if(start == null || end == null || userId == null || start < 0 || end < start || userId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        List<ConcernAndUserDo> concernAndUserDos = concernRecordDoMapper.selectTarget(start, end-start+1, userId);
        List<ConcernOutParam> concernOutParams = new ArrayList<>();
        for (ConcernAndUserDo concernAndUserDo: concernAndUserDos) {
            ConcernOutParam concernOutParam = new ConcernOutParam();
            concernOutParam.setUser_id(concernAndUserDo.getConcernRecordDo().getTargetId());
            concernOutParam.setUser_nickname(concernAndUserDo.getUserDo().getNickname());
            concernOutParams.add(concernOutParam);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", concernOutParams);
        map.put("sum", concernRecordDoMapper.selectTargetNum(userId));

        return map;
    }

    @Override
    public boolean isConcerned(ConcernModel concernModel) throws BusinessException{
        ConcernRecordDo concernRecordDo = new ConcernRecordDo();
        concernRecordDo.setTargetId(concernModel.getTargetId());
        concernRecordDo.setFollowerId(concernModel.getUserId());
        int result = concernRecordDoMapper.selectIsConcerned(concernRecordDo);
        if (result != 1)
            return false;
        else
            return true;
    }
}
