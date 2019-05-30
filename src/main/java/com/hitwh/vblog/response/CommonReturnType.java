package com.hitwh.vblog.response;

import com.hitwh.vblog.util.TimestampUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonReturnType {
    // 时间戳
    private String response_time;

    // 响应代码， 0为成功，其他所有为错误
    private String code;

    private String msg;

    // code为0， 则data返回前端需要的数据
    // code不为0， 则data返回通用的错误码格式
    private Object data;

    // 定义一个通用的创建方法（失败）
    public static CommonReturnType create(Integer code, String msg){
        CommonReturnType returnType = new CommonReturnType();
        returnType.setResponse_time(String.valueOf(System.currentTimeMillis()));
        returnType.setCode(String.valueOf(code));
        returnType.setMsg(msg);
        return returnType;
    }

    // 定义一个通用的创建方法（成功）,result为返回前端的数据
    public static CommonReturnType create(Object result){
        CommonReturnType returnType = new CommonReturnType();
        returnType.setResponse_time(String.valueOf(System.currentTimeMillis()));
        returnType.setCode(String.valueOf(EnumError.SUCCESS.getErrCode()));
        returnType.setMsg(EnumError.SUCCESS.getErrMsg());
        returnType.setData(result);
        return returnType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
