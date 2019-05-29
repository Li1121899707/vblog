package com.hitwh.vblog.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimestampUtil {
    //获取当前时间
    public static Timestamp getNowTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String nowtime = simpleDateFormat.format(date);
        Timestamp time= Timestamp.valueOf(nowtime);
        return time;
    }
    //将string转换为timestamp
    public static Timestamp changeStringTime(String nowTime){
        Timestamp time=Timestamp.valueOf(nowTime);
        return time;
    }
}