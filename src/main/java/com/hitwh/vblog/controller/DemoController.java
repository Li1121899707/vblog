package com.hitwh.vblog.controller;

import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.outparam.DemoOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/demo")
public class DemoController {
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

    //定义exceptionhandler解决未被controller吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonReturnType handlerException(HttpServletRequest request, Exception ex){
        BusinessException businessException = (BusinessException)ex;
        return CommonReturnType.create(businessException.getErrCode(), businessException.getErrMsg());
    }
}
