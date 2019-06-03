package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.response.BusinessException;

import java.util.Map;

public interface LoginService {
    Map<String,Object> getLoginInfoByAccout(LoginModel loginModel) throws BusinessException;

    Boolean tokenValidate(String key,Integer uid,long Request_time);

    Map<String,Object> getLoginInfoByPhone(LoginModel loginModel) throws BusinessException;

    Map<String,Object> getLoginInfoByEmail(LoginModel loginModel) throws BusinessException;

    Map<String,Object> returnMap(UserDo userDo) throws BusinessException;

    Integer loginValidateByAccount(LoginModel loginModel) throws BusinessException;

    Integer loginValidateByPhone(LoginModel loginModel) throws BusinessException;

    Integer loginValidateByEmail(LoginModel loginModel) throws BusinessException;
}
