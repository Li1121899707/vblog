package com.hitwh.vblog.response;

// 错误枚举
public enum EnumError implements CommonError {
    SUCCESS(0, "success"),
    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(4001, "参数不合法"),
    // 10000开头为用户信息相关错误定义
    USER_NOT_EXIST(1001, "用户不存在"),
    DATABASE_INSERT_ERROR(2001, "数据库添加失败"),
    UNKNOWN_ERROR(4002, "未知错误"),
    PASSWORD_ERROR(1003,"密码错误"),
    MD5_ERROR(4003, "MD5加密错误")


    ;

    private int errCode;
    private String errMsg;

    EnumError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    // 定制错误信息
    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
