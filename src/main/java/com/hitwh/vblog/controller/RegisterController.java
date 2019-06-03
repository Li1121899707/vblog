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
        //将传入参数转换为Model
        registerModel.setAccount(registerInParam.getAccount());
        registerModel.setEmail(registerInParam.getEmail());
        registerModel.setNickname(registerInParam.getNickname());
        registerModel.setPhone(registerInParam.getPhone());
        registerModel.setPwd(registerInParam.getPwd());
        registerModel.setAvatarLg(registerInParam.getAvatar_lg());
        registerModel.setAvatarMd(registerInParam.getAvatar_md());
        registerModel.setAvatarSm(registerInParam.getAvatar_sm());
        registerModel.setInterest1(registerInParam.getInterest_1());
        registerModel.setInterest2(registerInParam.getInterest_2());
        registerModel.setInterest3(registerInParam.getInterest_3());
        //执行Register
        registerService.register(registerModel);
        //返回执行结果
        return CommonReturnType.success();
    }
}
