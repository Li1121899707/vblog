package com.hitwh.vblog.response;


// 通用返回类型，按照接口文档格式编写
public class CommonReturnType {
    // 时间戳
    private long response_time;

    // 响应代码， 0为成功，其他所有为错误
    private Integer code;

    private String msg;

    // code为0， 则data返回前端需要的数据
    // code不为0， 则data返回通用的错误码格式
    private Object data;

    // 定义一个通用的创建方法（失败）
    public static CommonReturnType create(Integer code, String msg){
        CommonReturnType returnType = new CommonReturnType();
        returnType.setResponse_time(System.currentTimeMillis());
        returnType.setCode(code);
        returnType.setMsg(msg);
        return returnType;
    }

    // 定义一个通用的创建方法（成功）,result为返回前端的数据
    public static CommonReturnType create(Object result){
        CommonReturnType returnType = new CommonReturnType();
        returnType.setResponse_time(System.currentTimeMillis());
        returnType.setCode(EnumError.SUCCESS.getErrCode());
        returnType.setMsg(EnumError.SUCCESS.getErrMsg());
        returnType.setData(result);
        return returnType;
    }

    public static CommonReturnType success(){
        CommonReturnType returnType = new CommonReturnType();
        returnType.setResponse_time(System.currentTimeMillis());
        returnType.setCode(EnumError.SUCCESS.getErrCode());
        returnType.setMsg(EnumError.SUCCESS.getErrMsg());
        return returnType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
