package com.hitwh.vblog.service;


import com.hitwh.vblog.model.DemoModel;

public interface DemoService {
    //Integer insertRecord(Demo demo);
    DemoModel getDemoInfo(Integer id);
}
