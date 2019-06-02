package com.hitwh.vblog.controller;

import com.hitwh.vblog.inparam.DemoInParam;
import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.outparam.DemoOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.util.MyMd5;
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

    // @RequestBody 需要用对象形式接受，否则只能接受前端传来的所有json内容。不可以接受int等类型。
    // @RequestParam 不可以接受 json 数据。
    //'Content-Type': 'application/x-www-form-urlencoded' 可以使用 @RequestParam
    /*headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },*/

    @PostMapping("/info")
    public CommonReturnType getUserInfo(@RequestBody DemoInParam demoInParam) throws BusinessException{
        DemoModel demoModel = demoService.getDemoInfo(demoInParam.getId());

        // 抛出用户不存在异常
        if(demoModel == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        // 领域模型转换为传出参数
        DemoOutParam demoOutParam = convertFromModel(demoModel);
        // 返回通用对象
        return CommonReturnType.create(demoOutParam);
    }

    @PostMapping("/md5")
    public Object getMD5(){
        return MyMd5.md5("123sbc");
    }

    // 领域模型转化为可供前端显示的输出模型
    private DemoOutParam convertFromModel(DemoModel demoModel){
        if(demoModel == null) return null;
        DemoOutParam demoOutParam = new DemoOutParam();
        BeanUtils.copyProperties(demoModel, demoOutParam);
        return demoOutParam;
    }

    // 加@RequestBody可以使用json传递
    // 不加@RequestBody使用form传递

    @PostMapping("/register")
    public CommonReturnType demoRegister(@RequestBody DemoInParam demoInParam) throws BusinessException {
        // 构造Service层需要的领域模型类，即将传入参数对象转换为领域模型对象
        DemoModel demoModel = new DemoModel();
        demoModel.setUserName(demoInParam.getUserName());
        demoModel.setUserPwd(demoInParam.getUserPwd());
        demoModel.setUserDescription(demoInParam.getUserDescription());
        Timestamp time=timestampUtil.getNowTime();
        demoModel.setUserRegistTime(time);
        demoService.demoRegister(demoModel);
        // 直接返回添加成功的原因是：如果在任何一步添加失败，会直接抛出异常，不会到这一句
        return CommonReturnType.create(EnumError.SUCCESS.getErrCode(), "添加成功");
    }

    @RequestMapping("/query_all")
    public CommonReturnType queryAll(){
        return CommonReturnType.create(demoService.queryAll());
    }
}
