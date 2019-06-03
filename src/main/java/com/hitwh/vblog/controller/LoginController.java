package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.LoginInParam;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LoginService;
import com.hitwh.vblog.util.MyMd5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  LoginController extends BaseController{
    @Autowired
    LoginService loginService;

    @PostMapping("/login_account")
    public CommonReturnType loginAccount(@RequestBody LoginInParam loginInParam) throws BusinessException {
        //String pwd = MyMd5.md5Encryption(loginInParam.getPwd());
        LoginModel loginModel = new LoginModel();
        try{
            loginModel.setAccount(loginInParam.getAccount());
            loginModel.setPwd(loginInParam.getPwd());
        }catch (Exception e){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        return CommonReturnType.create(loginService.getLoginInfo(loginModel));
    }
}
