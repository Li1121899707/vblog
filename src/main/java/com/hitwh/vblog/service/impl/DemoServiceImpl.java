package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.DemoUserDo;
import com.hitwh.vblog.bean.DemoUserPwdDo;
import com.hitwh.vblog.mapper.DemoUserDoMapper;
import com.hitwh.vblog.mapper.DemoUserPwdDoMapper;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.model.DemoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoUserDoMapper demoUserDoMapper;
    @Autowired
    DemoUserPwdDoMapper demoUserPwdDoMapper;

    // 读取数据库中userinfo userpassword
    @Override
    public DemoModel getDemoInfo(Integer id) {
        // 使用实体类接收数据库读出的数据
        DemoUserDo demoUserDo = demoUserDoMapper.selectByPrimaryKey(id);
        if(demoUserDo == null) return null;
        DemoUserPwdDo demoUserPwdDo = demoUserPwdDoMapper.selectByUserId(demoUserDo.getUserId());
        return convertFromDataObject(demoUserDo, demoUserPwdDo);
    }

    public DemoModel convertFromDataObject(DemoUserDo demoUserDo, DemoUserPwdDo demoUserPwdDo){
        DemoModel demoModel = new DemoModel();
        BeanUtils.copyProperties(demoUserDo, demoModel);
        BeanUtils.copyProperties(demoUserPwdDo, demoModel);
        return demoModel;
    }
}
