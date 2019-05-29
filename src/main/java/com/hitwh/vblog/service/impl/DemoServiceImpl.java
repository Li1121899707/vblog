package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.Demo;
import com.hitwh.vblog.mapper.DemoMapper;
import com.hitwh.vblog.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    DemoMapper demoMapper;

    @Override
    public Integer insertRecord(Demo demo) {
        return demoMapper.insert(demo);
    }
}
