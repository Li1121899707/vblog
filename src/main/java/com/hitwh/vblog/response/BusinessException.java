package com.hitwh.vblog.response;

// 包装器业务异常类实现
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    // 直接接受EnumError的传参用于构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    // 接收自定义ErrorMsg的方式构造业务异常
    public BusinessException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return this;
    }
}
