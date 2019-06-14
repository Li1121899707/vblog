package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ConcernAndUserDo;
import com.hitwh.vblog.bean.ConcernRecordDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.ConcernRecordDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.outparam.ConcernOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.ConcernService;
import com.hitwh.vblog.service.UserService;
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
    @Autowired
    UserDoMapper userDoMapper;
    @Autowired
    UserService userService;

    @Override
    public void insert(ConcernModel concernModel) throws BusinessException {
        if(concernModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ValidationResult result = validator.validate(concernModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());

        userService.ifBan(concernModel.getTargetId());

        if(concernModel.getUserId() == concernModel.getTargetId())
            throw new BusinessException(EnumError.CONCERN_MYSELF);

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectByPrimaryKey(concernModel.getTargetId());
        }catch (Exception e){
            throw new BusinessException(EnumError.TARGET_NOT_EXIST);
        }

        if(userDo == null)
            throw new BusinessException(EnumError.TARGET_NOT_EXIST);

        ConcernRecordDo concernRecordDo = new ConcernRecordDo();
        concernRecordDo.setFollowerId(concernModel.getUserId());
        concernRecordDo.setTargetId(concernModel.getTargetId());

        Integer testIfConcerned = concernRecordDoMapper.selectIsConcerned(concernRecordDo);
        if(testIfConcerned != 0)
            throw new BusinessException(EnumError.CONCERN_EXIST);

        Timestamp timestamp = TimestampUtil.getNowTime();
        concernRecordDo.setConcernTime(timestamp);

        int writeResult = 0;
        try {
            writeResult = concernRecordDoMapper.insertSelective(concernRecordDo);
        }catch (Exception e){
            throw new BusinessException(EnumError.CONCERN_INSERT_ERROR);
        }

        if (writeResult != 1)
            throw new BusinessException(EnumError.CONCERN_INSERT_ERROR);
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

        Integer testIfConcerned = concernRecordDoMapper.selectIsConcerned(concernRecordDo);
        if(testIfConcerned == 0)
            throw new BusinessException(EnumError.CONCERN_NOT_EXIST);

        int writeResult = concernRecordDoMapper.delete(concernRecordDo);
        if (writeResult != 1)
            throw new BusinessException(EnumError.CONCERN_DELETE_ERROR);
    }

    @Override
    public Map<String, Object> queryFollower(Integer start, Integer end, Integer userId) throws BusinessException {
        if(start == null || end == null || userId == null || start < 0 || end < start || userId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        List<ConcernAndUserDo> concernAndUserDos = concernRecordDoMapper.selectFollower(start, end-start+1, userId);
        if(concernAndUserDos == null || concernAndUserDos.size() == 0)
            return null;

        List<ConcernOutParam> concernOutParams = new ArrayList<>();
        for (ConcernAndUserDo concernAndUserDo: concernAndUserDos) {
            ConcernOutParam concernOutParam = new ConcernOutParam();
            concernOutParam.setUser_id(concernAndUserDo.getConcernRecordDo().getFollowerId());
            concernOutParam.setUser_nickname(concernAndUserDo.getUserDo().getNickname());
            concernOutParam.setSignature(concernAndUserDo.getUserDo().getSignature());
            concernOutParam.setAvatar_md(concernAndUserDo.getUserDo().getAvatarMd());
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
        if(concernAndUserDos == null || concernAndUserDos.size() == 0)
            return null;

        List<ConcernOutParam> concernOutParams = new ArrayList<>();
        for (ConcernAndUserDo concernAndUserDo: concernAndUserDos) {
            ConcernOutParam concernOutParam = new ConcernOutParam();
            concernOutParam.setUser_id(concernAndUserDo.getConcernRecordDo().getTargetId());
            concernOutParam.setUser_nickname(concernAndUserDo.getUserDo().getNickname());
            concernOutParam.setSignature(concernAndUserDo.getUserDo().getSignature());
            concernOutParam.setAvatar_md(concernAndUserDo.getUserDo().getAvatarMd());
            concernOutParams.add(concernOutParam);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", concernOutParams);
        map.put("sum", concernRecordDoMapper.selectTargetNum(userId));
        return map;
    }

    @Override
    public boolean isConcerned(ConcernModel concernModel) throws BusinessException{
        if(concernModel.getTargetId() == null || concernModel.getUserId() == null || concernModel.getUserId()<=0 || concernModel.getTargetId()<=0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        if(concernModel.getTargetId() == concernModel.getUserId())
            throw new BusinessException(EnumError.CONCERN_MYSELF);

        userService.ifBan(concernModel.getTargetId());

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
