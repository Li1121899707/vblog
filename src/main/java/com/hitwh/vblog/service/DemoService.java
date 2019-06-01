package com.hitwh.vblog.service;


import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.PageResponse;

// Service接口，可以有不同的实现方式
public interface DemoService {
    Integer demoRegister(DemoModel demoModel) throws BusinessException;
    DemoModel getDemoInfo(Integer id);
    PageResponse queryAll();
}
