package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.LoginInParam;
import com.hitwh.vblog.inparam.TokenRenewParam;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LoginService;
import com.hitwh.vblog.util.LoginRequired;
import com.hitwh.vblog.util.MyMd5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class  LoginController extends BaseController{
    @Autowired
    LoginService loginService;

    /**
     * @description 使用账户登录
     * @param loginInParam
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/login")
    public CommonReturnType login(@RequestBody LoginInParam loginInParam) throws BusinessException {
        LoginModel loginModel = new LoginModel();
        loginModel.setLoginInfo(loginInParam.getLoginInfo());
        loginModel.setPwd(loginInParam.getPwd());


        return CommonReturnType.create(loginService.login(loginModel));
    }
//    @RequestMapping("/login_account")
//    public CommonReturnType loginAccount(@RequestBody LoginInParam loginInParam) throws BusinessException {
//        //String pwd = MyMd5.md5Encryption(loginInParam.getPwd());
//        if(loginInParam == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
//
//        LoginModel loginModel = new LoginModel();
//
//        loginModel.setAccount(loginInParam.getAccount());
//        loginModel.setPwd(loginInParam.getPwd());
//
//        return CommonReturnType.create(loginService.getLoginInfoByAccout(loginModel));
//    }

    /**
     * @description 使用手机号登陆
     * @param loginInParam
     * @return
     * @throws BusinessException
     */

//    @RequestMapping("/login_phone")
//    public CommonReturnType loginPhone(@RequestBody LoginInParam loginInParam) throws BusinessException {
//        if(loginInParam == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
//
//        LoginModel loginModel = new LoginModel();
//        loginModel.setPwd(loginInParam.getPwd());
//        loginModel.setPhone(loginInParam.getPhone());
//
//        return CommonReturnType.create(loginService.getLoginInfoByPhone(loginModel));
//
//    }

//    @RequestMapping("/login_email")
//    public CommonReturnType loginEmail(@RequestBody LoginInParam loginInParam) throws BusinessException {
//        if(loginInParam == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
//
//        LoginModel loginModel = new LoginModel();
//        loginModel.setPwd(loginInParam.getPwd());
//        loginModel.setEmail(loginInParam.getEmail());
//
//        return CommonReturnType.create(loginService.getLoginInfoByEmail(loginModel));
//    }

    @RequestMapping("/login_validate_account")
    public CommonReturnType loginValidateByAccount(@RequestBody LoginInParam loginInParam) throws BusinessException {
        if(loginInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        LoginModel loginModel = new LoginModel();
        loginModel.setAccount(loginInParam.getAccount());

        Integer result = loginService.loginValidateByAccount(loginModel);
        Map<String,Object> outResult = new HashMap<>();
        outResult.put("validate_result", result);

        return CommonReturnType.create(outResult);
    }

    @RequestMapping("/login_validate_phone")
    public CommonReturnType loginValidateByPhone(@RequestBody LoginInParam loginInParam) throws BusinessException {
        if(loginInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        LoginModel loginModel = new LoginModel();
        loginModel.setPhone(loginInParam.getPhone());

        Integer result = loginService.loginValidateByPhone(loginModel);
        Map<String,Object> outResult = new HashMap<>();
        outResult.put("validate_result", result);

        return CommonReturnType.create(outResult);
    }

    @RequestMapping("/login_validate_email")
    public CommonReturnType loginValidateByEmail(@RequestBody LoginInParam loginInParam) throws BusinessException {
        if(loginInParam == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(loginInParam.getEmail());

        Integer result = loginService.loginValidateByEmail(loginModel);
        Map<String,Object> outResult = new HashMap<>();
        outResult.put("validate_result", result);

        return CommonReturnType.create(outResult);
    }

    @LoginRequired
    @RequestMapping("/logout")
    public CommonReturnType logout(@RequestBody LoginInParam loginInParam) throws BusinessException {
        if(loginInParam.getUid() == null || loginInParam.getUid() <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        loginService.logOut(loginInParam.getUid());
        return CommonReturnType.success();
    }

    @RequestMapping("/token_renew")
    public CommonReturnType tokenRenew(@RequestBody TokenRenewParam tokenRenewParam) throws BusinessException {
        return CommonReturnType.create(loginService.renewToken(tokenRenewParam.getUid(), tokenRenewParam.getToken()));
    }
}
