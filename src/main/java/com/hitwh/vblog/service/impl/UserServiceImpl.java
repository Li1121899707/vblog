package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.bean.UserInterestDoOut;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.mapper.UserInterestDoMapper;
import com.hitwh.vblog.model.UserModel;
import com.hitwh.vblog.outparam.UserOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/920:32
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDoMapper userDoMapper;
    @Autowired
    UserInterestDoMapper userInterestDoMapper;

    @Override
    public UserOutParam queryById(Integer uid) throws BusinessException {
        if(uid == null || uid <=0 )
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userResult = userDoMapper.selectByPrimaryKey(uid);
        if(userResult == null)
            return null;
        return convertUserDoToUserOutParam(userResult);
    }

    @Override
    public UserOutParam queryByAccount(String account) throws BusinessException {
        if(account == null || account.equals(""))
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
        UserDo userDo = new UserDo();
        userDo.setAccount(account);
        UserDo userResult = userDoMapper.selectIfAccount(userDo);

        if(userResult == null)
            return null;

        return convertUserDoToUserOutParam(userResult);
    }

    @Override
    public UserOutParam queryByPhone(String phone) throws BusinessException {
        if(phone == null || phone.equals(""))
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
        UserDo userDo = new UserDo();
        userDo.setPhone(phone);
        UserDo userResult = userDoMapper.selectIfLoginByPhone(userDo);

        if(userResult == null)
            return null;

        return convertUserDoToUserOutParam(userResult);
    }

    @Override
    public UserOutParam queryByEmail(String email) throws BusinessException {
        if(email == null || email.equals(""))
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
        UserDo userDo = new UserDo();
        userDo.setEmail(email);
        UserDo userResult = userDoMapper.selectIfLoginByEmail(userDo);

        if(userResult == null)
            return null;

        return convertUserDoToUserOutParam(userResult);
    }

    @Override
    public Map<String, Object> queryAllUserByLabel(Integer start, Integer end, Integer labelId) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || labelId == null || labelId <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        List<UserDo> userDos = userDoMapper.selectAllUserByLabel(start, end-start+1, labelId);

        if(userDos.size() == 0)
            return null;

        Map<String, Object> map = new HashMap<>();
        map.put("sum", userDoMapper.selectAllUserByLabelNum(labelId));
        map.put("list", convertUserDoToUserOutParamList(userDos));
        return map;
    }

    @Override
    public Map<String, Object> queryAllUser(Integer start, Integer end) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        List<UserDo> userDos = userDoMapper.selectAllUser(start, end-start+1);

        if(userDos.size() == 0)
            return null;

        Map<String, Object> map = new HashMap<>();
        map.put("sum", userDoMapper.selectAllUserNum());
        map.put("list", convertUserDoToUserOutParamList(userDos));
        return map;
    }

    @Override
    public void updateUser(UserModel userModel) {

    }

    public UserOutParam convertUserDoToUserOutParam(UserDo userDo){
        UserOutParam userOutParam = new UserOutParam();
        userOutParam.setUser_id(userDo.getUserId());
        userOutParam.setAccount(userDo.getAccount());
        userOutParam.setEmail(userDo.getEmail());
        userOutParam.setPhone(userDo.getPhone());
        userOutParam.setNickname(userDo.getSignature());
        userOutParam.setAvatar_lg(userDo.getAvatarLg());
        userOutParam.setAvatar_md(userDo.getAvatarMd());
        userOutParam.setAvatar_sm(userDo.getAvatarSm());
        userOutParam.setSignature(userDo.getSignature());
        ArrayList<UserInterestDoOut> userInterestDoOuts = (ArrayList)userInterestDoMapper.queryAllInterestsByUserId(userDo.getUserId());
        userOutParam.setInterest(userInterestDoOuts);
        return userOutParam;
    }

    public List<UserOutParam> convertUserDoToUserOutParamList(List<UserDo> userDos){
        List<UserOutParam> userOutParams = new ArrayList<>();
        for (UserDo userDo: userDos
        ){
            UserOutParam userOutParam = new UserOutParam();
            userOutParam.setUser_id(userDo.getUserId());
            userOutParam.setAccount(userDo.getAccount());
            userOutParam.setEmail(userDo.getEmail());
            userOutParam.setPhone(userDo.getPhone());
            userOutParam.setNickname(userDo.getSignature());
            userOutParam.setAvatar_lg(userDo.getAvatarLg());
            userOutParam.setAvatar_md(userDo.getAvatarMd());
            userOutParam.setAvatar_sm(userDo.getAvatarSm());
            userOutParam.setSignature(userDo.getSignature());
            ArrayList<UserInterestDoOut> userInterestDoOuts = (ArrayList)userInterestDoMapper.queryAllInterestsByUserId(userDo.getUserId());
            userOutParam.setInterest(userInterestDoOuts);
            userOutParams.add(userOutParam);
        }
        return userOutParams;


    }

}
