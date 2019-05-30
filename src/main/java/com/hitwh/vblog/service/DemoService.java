package com.hitwh.vblog.service;


import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.response.BusinessException;

public interface DemoService {
    Integer demoRegister(DemoModel demoModel) throws BusinessException;
    DemoModel getDemoInfo(Integer id);
}
