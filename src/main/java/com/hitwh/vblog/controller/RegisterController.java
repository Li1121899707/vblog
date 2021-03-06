package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.RegisterInParam;
import com.hitwh.vblog.model.RegisterModel;
import com.hitwh.vblog.outparam.RegisterOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.RegisterService;
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends BaseController {
    @Autowired
    RegisterService registerService;
    @Autowired
    TimestampUtil timestampUtil;

    @PostMapping("/register")
    public CommonReturnType register(@RequestBody RegisterInParam registerInParam) throws BusinessException {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setAccount(registerInParam.getAccount());
        registerModel.setEmail(registerInParam.getEmail());
        registerModel.setNickname(registerInParam.getUsername());
        registerModel.setPhone(registerInParam.getPhone());
        registerModel.setPwd(registerInParam.getPwd());
        registerModel.setAvatarLg(registerInParam.getAvatar_lg());
        registerModel.setAvatarMd(registerInParam.getAvatar_md());
        registerModel.setAvatarSm(registerInParam.getAvatar_sm());
        registerModel.setInterest(registerInParam.getInterest());
        registerService.register(registerModel);
        return CommonReturnType.success();
    }
}
