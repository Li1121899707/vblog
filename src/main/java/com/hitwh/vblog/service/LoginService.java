package com.hitwh.vblog.service;

import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.response.BusinessException;

import java.util.Map;

public interface LoginService {
    Map<String,Object> getLoginInfo(LoginModel loginModel) throws BusinessException;

}
