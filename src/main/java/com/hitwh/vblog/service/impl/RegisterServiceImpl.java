package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.RegisterModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.RegisterService;
import com.hitwh.vblog.util.MyMd5;
import com.hitwh.vblog.util.TimestampUtil;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.hitwh.vblog.util.TimestampUtil.getNowTime;
import static java.lang.Integer.valueOf;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserDoMapper userDoMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public void register(RegisterModel registerModel) throws BusinessException {
        if(registerModel == null){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult result = validator.validate(registerModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        Map password = new HashMap();
        password = MyMd5.md5SaltEncryption(registerModel.getPwd());
        int salt = (Integer) password.get("salt");
        UserDo userDo = new UserDo();
        userDo.setAccount(registerModel.getAccount());
        userDo.setPwd(password.get("encryption").toString());
        userDo.setNickname(registerModel.getNickname());
        userDo.setEmail(registerModel.getEmail());
        userDo.setPhone(registerModel.getPhone());
        userDo.setInterest1(0);
        userDo.setRegisterTime(TimestampUtil.getNowTime());
        userDo.setSalt(salt);
        Integer registerResult = 0;

        try {
            registerResult  = userDoMapper.insertSelective(userDo);
        }catch (Exception e){
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
        }

        if (registerResult != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

    }
}
