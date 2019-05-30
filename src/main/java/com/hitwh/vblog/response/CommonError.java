package com.hitwh.vblog.response;

// 通用错误返回
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
