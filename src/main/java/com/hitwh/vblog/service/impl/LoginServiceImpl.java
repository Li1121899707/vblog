package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.TokenDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.TokenDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LoginService;
import com.hitwh.vblog.util.MyMd5;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDoMapper userDoMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private TokenDoMapper tokenDoMapper;

    @Override
    public Map<String,Object> getLoginInfo(LoginModel loginModel) throws BusinessException {

        Map<String,Object> returnMap = new HashMap<>();

        if(loginModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        //LoginModel loginModel = userDoMapper.selectByPrimaryKey();
        ValidationResult result = validator.validate(loginModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(loginModel, userDo);

        UserDo userDoFromTable = userDoMapper.selectIfLogin(userDo);
        if(userDoFromTable == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        Integer salt = userDoFromTable.getSalt();
        String saltPassword = MyMd5.md5Encryption(userDo.getPwd()) + MyMd5.md5Encryption(salt.toString());
        if(!MyMd5.md5Encryption(saltPassword).equals(userDoFromTable.getPwd()))
            throw new BusinessException(EnumError.PASSWORD_ERROR);


        TokenDo tokenDo = new TokenDo();
        Map<String,Object> map = MyMd5.GetToken(userDo.getAccount());
        tokenDo.setUserId(userDoFromTable.getUserId());
        long current = Long.valueOf(map.get("currentTime").toString());
        tokenDo.setToken(map.get("token").toString());
        tokenDo.setCreateTime(new Date(current));
        tokenDo.setExpiryTime(new Date(Long.valueOf(map.get("expiryTime").toString())));
        tokenDoMapper.insert(tokenDo);
        returnMap.put("userId",userDoFromTable.getUserId());
        returnMap.put("token",map.get("token").toString());
        return returnMap;
    }

}