package com.hitwh.vblog.response;

// 错误枚举
public enum EnumError implements CommonError {
    SUCCESS(0, "success"),
    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(10, "参数不合法"),
    // 10000开头为用户信息相关错误定义
    USER_NOT_EXIST(1001, "用户不存在"),
    DATABASE_INSERT_ERROR(2001, "数据库添加失败"),
    DATABASE_QUERY_NULL_ERROR(2002, "数据库查询数据为空"),
    DATABASE_DELETE_ERROR(2003, "数据库删除失败"),
    UNKNOWN_ERROR(4002, "未知错误"),
    PASSWORD_ERROR(1003,"密码错误"),
    MD5_ERROR(4003, "MD5加密错误"),
    TOKEN_ERROR(2, "Token 错误"),
    TOKEN_OVERDUE(3,"Token 已过期"),
    TOKEN_UID_NOT_FOUND(4, "验证 Token，用户ID参数错误"),
    TOKEN_TIME_NOT_FOUND(5, "验证 Token，时间参数错误"),
    TOKEN_KEY_NOT_FOUND(6, "验证 Token ，key参数错误"),
    TOKEN_VALIDATE_PARAM_NOT_FOUND(7, "验证Token， 参数未传入"),
    PARENT_COMMENT_HIDDEN(3201,"评论被隐藏"),
    COMMENT_HIDE_ERROR(3202,"评论隐藏失败"),
    ACCOUNT_NOT_EXIST(1201, "账号不存在或密码错误"),
    PHONE_NOT_EXIST(1202, "手机号不存在或密码错误"),
    EMAIL_NOT_EXIST(1203, "邮箱不存在或密码错误")
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
