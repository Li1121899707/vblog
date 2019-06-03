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
    MD5_ERROR(4003, "MD5加密错误"),
    TOKEN_ERROR(2, "Token 错误"),
    TOKEN_OVERDUE(3,"Token 已过期"),
    TOKEN_UID_NOT_FOUND(4, "验证 Token，用户ID参数错误"),
    TOKEN_TIME_NOT_FOUND(5, "验证 Token，时间参数错误"),
    TOKEN_KEY_NOT_FOUND(6, "验证 Token ，key参数错误"),
    TOKEN_VALIDATE_PARAM_NOT_FOUND(7, "验证Token， 参数未传入"),
    PARENT_COMMENT_HIDDEN(4004,"父评论被隐藏"),
    COMMENT_HIDE_ERROR(4005,"评论隐藏失败")
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
