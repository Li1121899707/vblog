package com.hitwh.vblog.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LoginService;
import com.hitwh.vblog.service.impl.LoginServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * @Author yf
 * @Date 2018/10/15 15:22
 * @Version 1.0
 * <p>
 * 登录拦截器，即Token验证过滤器,判断是否已登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * 在controller处理之前首先对请求参数进行处理，以及对公共参数的保存
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BusinessException {
        System.out.println("---------------拦截器开始------------------");
        if (!(handler instanceof HandlerMethod)) {
            LOGGER.info("不是HandlerMethod类型，则无需检查");
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        boolean hasLoginAnnotation = method.getMethod().isAnnotationPresent(LoginRequired.class);
        //不存在LoginRequired注解，则直接通过
        if (!hasLoginAnnotation)
            return true;

        LoginRequired loginRequired = method.getMethod().getAnnotation(LoginRequired.class);

        response.setHeader("Content-type", "application/json;charset=UTF-8");

        //请求方法
        String requestMethord = request.getRequestURI();
        if (requestMethord == null)
            return false;

        //获取请求参数
        JSONObject parameterMap = null;
        try {
            parameterMap = JSON.parseObject(new BodyReaderHttpServletRequestWrapper(request).getBodyString(request));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long request_time;
        String key;
        Integer uid;

        //long request_time = (long) parameterMap.get("request_time");
        //request_time = 1559483686339L;
        if(parameterMap == null)
            throw new BusinessException(EnumError.KEY_VALIDATE_PARAM_ERROR);

        try {
            request_time = (long) parameterMap.get("request_time");
        }catch (Exception e){
            throw new BusinessException(EnumError.KEY_VALIDATE_PARAM_ERROR);
        }

        key = String.valueOf(parameterMap.get("key"));
        if(key == null || key.equals("") || key.equals("null"))
            throw new BusinessException(EnumError.KEY_VALIDATE_PARAM_ERROR);

        uid = (Integer) parameterMap.get("uid");
        if(uid == null || uid.equals(0))
            throw new BusinessException(EnumError.KEY_VALIDATE_PARAM_ERROR);

        LoginService loginService = (LoginService) SpringUtil.getBean(LoginService.class);

        Boolean keyValidateResult = loginService.keyValidate(key, uid, request_time);

        // result为false，代表token验证失败，直接返回
        if(!keyValidateResult)
            throw new BusinessException(EnumError.KEY_ERROR);

        Boolean tokenValidateResult = loginService.tokenValidate(uid, request_time);

        if(!tokenValidateResult)
            throw new BusinessException(EnumError.TOKEN_OVERDUE);

        // result为true，代表token验证成功，需要判断是否需要管理员权限
        // 不需要管理员权限，直接返回
        if(!loginRequired.admin())
            return true;

        Boolean adminResult = loginService.adminValidate(uid);
        if(adminResult)
            return true;
        else
            throw new BusinessException(EnumError.UNAUTHORIZED);
    }
}
