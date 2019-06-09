package com.hitwh.vblog.service;

import com.hitwh.vblog.model.UserModel;
import com.hitwh.vblog.outparam.UserOutParam;
import com.hitwh.vblog.response.BusinessException;

import java.util.Map;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/920:32
 */
public interface UserService {
    UserOutParam queryById(Integer uid) throws BusinessException;

    UserOutParam queryByAccount(String account) throws BusinessException;

    UserOutParam queryByPhone(String phone) throws BusinessException;

    UserOutParam queryByEmail(String email) throws BusinessException;

    Map<String, Object> queryAllUserByLabel(Integer start, Integer end, Integer labelId) throws BusinessException;

    Map<String, Object> queryAllUser(Integer start, Integer end) throws BusinessException;

    void updateUser(UserModel userModel);
}
