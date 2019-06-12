package com.hitwh.vblog.response;

// 错误枚举
public enum EnumError implements CommonError {
    SUCCESS(0, "success"),
    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(1, "传入参数错误"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    // 10000开头为用户信息相关错误定义
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_HIDDEN(1002, "用户被隐藏"),
    USER_PASSWORD_ERROR(1003,"用户密码错误"),
    ACCOUNT_NOT_EXIST(1101, "账号不存在"),
    PHONE_NOT_EXIST(1102, "手机号不存在"),
    EMAIL_NOT_EXIST(1103, "邮箱不存在"),

    ARTICLE_NOT_EXIST(2001, "文章不存在"),
    ARTICLE_HIDDEN(2002, "文章被隐藏"),
    ARTICLE_DELETE_FAILED(2004, "文章删除失败"),

    PARENT_COMMENT_HIDDEN(3001,"评论被隐藏"),
    COMMENT_INSERT_ERROR(3002,"添加评论失败"),
    COMMENT_UPDATE_ERROR(3003,"修改评论失败"),

    LABEL_EXIST(4001,"标签已存在"),
    LABEL_INSERT_ERROR(4002, "添加标签失败"),
    LABEL_UPDATE_ERROR(4003, "修改标签失败"),

    CONCERN_INSERT_ERROR(5001, "关注失败"),
    CONCERN_EXIST(5002, "请勿重复关注"),
    CONCERN_DELETE_ERROR(5003, "解除关注失败"),
    TARGET_NOT_EXIST(5004, "关注者不存在"),

    THUMB_INSERT_ERROR(6001, "点赞失败"),
    THUMB_EXIST(6002, "请勿重复点赞"),
    THUMB_DELETE_ERROR(6003, "取消点赞失败"),
    THUMB_NOT_EXIST(6004, "未点赞，不可取消点赞"),

    REPORT_FAILED(7001, "不可举报自己文章"),
    REPORT_EXIST(7002, "举报已存在"),
    REPORT_INSERT_ERROR(7003, "添加举报失败"),
    REPORT_NOT_EXIST(7004, "举报不存在"),
    REPORT_HANDLE_ERROR(7005, "处理举报失败"),


    //COMMENT_HIDE_ERROR(3002,"评论隐藏失败"),
    //DATABASE_INSERT_ERROR(2001, "数据库添加失败"),
    //DATABASE_QUERY_NULL_ERROR(2002, "数据库查询数据为空"),
    //DATABASE_DELETE_ERROR(2003, "数据库删除失败"),
    //UNKNOWN_ERROR(4002, "未知错误"),

    //MD5_ERROR(4003, "MD5加密错误"),
    KEY_ERROR(2, "Key 错误"),
    TOKEN_OVERDUE(3,"Token 已过期"),
    KEY_VALIDATE_PARAM_ERROR(4, "验证Token时参数错误"),
    TOKEN_RENEW_FAILED(5, "刷新Token"),
    QUERY_NOT_EXIST(6,"查询项不存在"),
    UNAUTHORIZED(7, "用户没有权限"),

    //TOKEN_UID_NOT_FOUND(5, "验证 Token，用户ID参数错误"),
    //TOKEN_TIME_NOT_FOUND(6, "验证 Token，时间参数错误"),
    //TOKEN_KEY_NOT_FOUND(7, "验证 Token ，key参数错误"),

    //PERMISSION_DENIED(8, "权限不足"),
    //LACK_OF_AUTHORITY(10,"权限缺失")
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
