package com.hitwh.vblog.response;

// 错误枚举
public enum EnumError implements CommonError {
    SUCCESS(0, "成功"),
    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(00001, "参数不合法"),
    // 10000开头为用户信息相关错误定义
    USER_NOT_EXIST(10001, "用户不存在")
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
