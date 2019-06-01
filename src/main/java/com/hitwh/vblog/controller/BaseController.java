package com.hitwh.vblog.controller;

import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

// Controller基类，负责抛出异常，管理登陆信息
public class BaseController {

    //定义exceptionhandler解决未被controller吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonReturnType handlerException(HttpServletRequest request, Exception ex){
//        if(ex instanceof BusinessException){
//            BusinessException businessException = (BusinessException)ex;
//            return CommonReturnType.create(businessException.getErrCode(), businessException.getErrMsg());
//        }else{
//            return CommonReturnType.create(EnumError.UNKNOWN_ERROR.getErrCode(), EnumError.UNKNOWN_ERROR.getErrMsg());
//        }

        BusinessException businessException = (BusinessException)ex;
        return CommonReturnType.create(businessException.getErrCode(), businessException.getErrMsg());
    }
}
