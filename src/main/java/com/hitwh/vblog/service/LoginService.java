package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.outparam.LoginOutParam;
import com.hitwh.vblog.response.BusinessException;

import java.util.Map;

public interface LoginService {
    //Map<String,Object> getLoginInfoByAccout(LoginModel loginModel) throws BusinessException;

    Boolean keyValidate(String key,Integer uid,long Request_time) throws BusinessException;

    Boolean tokenValidate(Integer uid, long Request_time);

    String renewToken(Integer uid, String token) throws BusinessException;

    //Map<String,Object> getLoginInfoByPhone(LoginModel loginModel) throws BusinessException;

    //Map<String,Object> getLoginInfoByEmail(LoginModel loginModel) throws BusinessException;

    LoginOutParam returnMap(UserDo userDo) throws BusinessException;

    Integer loginValidateByAccount(LoginModel loginModel) throws BusinessException;

    Integer loginValidateByPhone(LoginModel loginModel) throws BusinessException;

    Integer loginValidateByEmail(LoginModel loginModel) throws BusinessException;

    Boolean adminValidate(Integer uid);

    void logOut(Integer uid) throws BusinessException;

    LoginOutParam login(LoginModel loginModel) throws BusinessException;

    LoginOutParam OutLoginParam(UserDo userDoFromTable,String password,UserDo userDo) throws BusinessException;
}
