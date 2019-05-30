package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.DemoInParam;
import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.outparam.DemoOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;


@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController{
    @Autowired
    DemoService demoService;
    @Autowired
    TimestampUtil timestampUtil;

    @RequestMapping("/info")
    public CommonReturnType getUserInfo(@RequestParam(value = "id") Integer id) throws BusinessException{
        DemoModel demoModel = demoService.getDemoInfo(id);

        // 抛出用户不存在异常
        if(demoModel == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        DemoOutParam demoOutParam = convertFromModel(demoModel);
        // 返回通用对象
        return CommonReturnType.create(demoOutParam);
    }

    // 领域模型转化为可供前端显示的输出模型
    private DemoOutParam convertFromModel(DemoModel demoModel){
        if(demoModel == null) return null;
        DemoOutParam demoOutParam = new DemoOutParam();
        BeanUtils.copyProperties(demoModel, demoOutParam);
        return demoOutParam;
    }


    @RequestMapping("/register")
    public CommonReturnType demoRegister(DemoInParam demoInParam) throws BusinessException {
        DemoModel demoModel = new DemoModel();
        demoModel.setUserName(demoInParam.getUserName());
        demoModel.setUserPwd(demoInParam.getUserPwd());
        demoModel.setUserDescription(demoInParam.getUserDescription());
        Timestamp time=timestampUtil.getNowTime();
        demoModel.setUserRegistTime(time);
        demoService.demoRegister(demoModel);
        return CommonReturnType.create(EnumError.SUCCESS.getErrCode(), "添加成功");
    }
}
