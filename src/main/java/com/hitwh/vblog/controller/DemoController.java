package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.Demo;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.util.TimestampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {
    @Autowired
    DemoService demoService;
    @Autowired
    TimestampUtil timestampUtil;

    @RequestMapping(value = "/demo/test")
    public Map<String, Object> demoTest(Demo demo){
        Map<String,Object> map = new HashMap<>();
        if(demo.getTestName()!= null && demo.getTestDescription() != null){
            Timestamp time= timestampUtil.getNowTime();
            demo.setTestRegistTime(time);
        }else{
            map.put("code", 1);
            map.put("msg", "接收参数有误");
            return map;
        }

        Integer column = demoService.insertRecord(demo);
        if(column > 0){
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("column", "影响了" + column + "行");
            map.put("key", "新插入的数据主键为 " + demo.getTestId());
        }else{
            map.put("code", 2);
            map.put("msg", "插入数据失败");
        }
        return map;
    }
}
