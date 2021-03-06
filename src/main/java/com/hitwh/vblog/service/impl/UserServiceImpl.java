package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.*;
import com.hitwh.vblog.inparam.UserInparam;
import com.hitwh.vblog.mapper.LabelDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.mapper.UserInterestDoMapper;
import com.hitwh.vblog.model.UserModel;
import com.hitwh.vblog.outparam.UserOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    @Autowired
    LabelDoMapper labelDoMapper;

    @Override
    public UserOutParam queryById(Integer uid) throws BusinessException {
        if(uid == null || uid <=0 )
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ifBan(uid);

        UserDo userResult = null;
        try {
            userResult = userDoMapper.selectByPrimaryKey(uid);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(userResult == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        return convertUserDoToUserOutParam(userResult);
    }

    @Override
    public UserOutParam queryByAccount(String account) throws BusinessException {
        if(account == null || account.equals(""))
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userDo = new UserDo();
        userDo.setAccount(account);
        UserDo userResult = userDoMapper.selectIfLogin(userDo);

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

        LabelDo labelDo = null;
        try {
            labelDo = labelDoMapper.selectByPrimaryKey(labelId);
        }catch (Exception e){
            throw new BusinessException(EnumError.LABEL_NOT_EXIST);
        }

        if(labelDo == null)
            throw new BusinessException(EnumError.LABEL_NOT_EXIST);

        List<UserDo> userDos = userDoMapper.selectAllUserByLabel(start, end-start+1, labelId);

        if(userDos.size() == 0)
            return null;

        Map<String, Object> map = new HashMap<>();
        map.put("sum", userDoMapper.selectAllUserByLabelNum(labelId));
        map.put("list", convertUserDoToUserOutParamList(userDos));
        return map;
    }

    @Override
    public Map<String, Object> queryAllUser(Integer start, Integer end, Integer ban) throws BusinessException {
        if(start == null || end == null || start < 0 || end < start || ban == null || ban < 0 || ban > 2)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");


        List<UserDo> userDos = null;

        if(ban == 2)
            userDos = userDoMapper.selectAllUser(start, end-start+1);
        else
            userDos = userDoMapper.selectAllUserWithBan(start, end-start+1, ban);

        if(userDos.size() == 0)
            return null;

        Map<String, Object> map = new HashMap<>();
        if(ban == 2)
            map.put("sum", userDoMapper.selectAllUserNum());
        else
            map.put("sum", userDoMapper.selectAllUserNumWithBan(ban));
        map.put("list", convertUserDoToUserOutParamList(userDos));
        return map;
    }

    @Override
    public void updateUserInfo(UserDo userDo) throws BusinessException {
        if(userDo.getUserId() == null || userDo.getUserId() <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        ifBan(userDo.getUserId());

        if(userDo.getNickname() == null && userDo.getAccount() == null && userDo.getEmail() == null && userDo.getPhone() == null
        && userDo.getSignature() == null && userDo.getAvatarSm() == null && userDo.getAvatarMd() == null && userDo.getAvatarLg() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        try {
            userDoMapper.updateByPrimaryKeySelective(userDo);
        }catch (Exception e){
            System.out.println("update by primary key error");
        }

    }

    @Override
    public void updateUserInterest(List<Integer> userInterestDos, Integer userId) throws BusinessException {
        if(userInterestDos == null || userInterestDos.size() == 0)
            return;

        ifBan(userId);

        Integer interestNum = userInterestDoMapper.queryByUserIdNum(userId);

        if(interestNum > 0){
            try {
                userInterestDoMapper.deleteInterestByUserId(userId);
            }catch (Exception e){
                System.out.println("delete interest error");
            }
        }

        for(int i=0; i<userInterestDos.size(); i++){
            UserInterestDo userInterestDo = new UserInterestDo();
            userInterestDo.setUserId(userId);
            userInterestDo.setLabelId(userInterestDos.get(i));
            System.out.println(userId + " label_id: " + userInterestDos.get(i));
            try {
                userInterestDoMapper.insert(userInterestDo);
            }catch (Exception e){
                System.out.println(userId + " label_id: " + userInterestDos.get(i) + "错误");
            }

        }
    }

    @Override
    public void banUser(UserInparam userInparam) throws BusinessException {
        if (userInparam.getUid() == null || userInparam.getBan_uid() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectAdmin(userInparam.getUid());
        }catch (Exception e){
            throw new BusinessException(EnumError.UNAUTHORIZED);
        }

        if (userDo == null || userDo.getUserId() == null)
            throw new BusinessException(EnumError.UNAUTHORIZED);

        if(userDo.getUserId() == userInparam.getBan_uid()){
            System.out.println("管理员不能禁自己");
            throw new BusinessException(EnumError.UNAUTHORIZED);
        }

        UserDo testUserDo = null;
        try {
            testUserDo = userDoMapper.selectByPrimaryKey(userInparam.getBan_uid());
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(testUserDo == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        if(testUserDo.getBan() == 1)
            throw new BusinessException(EnumError.BAN_USER_REPEAT);

        UserDo userDo1 = new UserDo();
        userDo1.setUserId(userInparam.getBan_uid());
        userDo1.setBan(1);
        try {
            userDoMapper.updateByPrimaryKeySelective(userDo1);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }
    }

    @Override
    public Map<String, Object> userAvatar(Integer uid) throws BusinessException {
        if(uid == null || uid <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectByPrimaryKey(uid);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(userDo == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        Map<String, Object> map = new HashMap<>();
        map.put("avatar_lg", userDo.getAvatarLg());
        map.put("avatar_md", userDo.getAvatarMd());
        map.put("avatar_sm", userDo.getAvatarSm());

        return map;
    }

    @Override
    public void ifBan(Integer uid) throws BusinessException {
        if(uid == null || uid <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "验证用户是否被禁用时uid参数错误");

        UserDo userDo = null;
        try {
            userDo = userDoMapper.selectByPrimaryKey(uid);
        }catch (Exception e){
            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }

        if(userDo == null)
            throw new BusinessException(EnumError.USER_NOT_EXIST);

        if(userDo.getBan() == null){
            System.out.println(uid + "用户隐藏属性为null，检查数据库");
            throw new BusinessException(EnumError.USER_HIDDEN);
        }

        if(userDo.getBan() != 0)
            throw new BusinessException(EnumError.USER_HIDDEN);
    }


    public UserOutParam convertUserDoToUserOutParam(UserDo userDo){
        UserOutParam userOutParam = new UserOutParam();
        userOutParam.setUser_id(userDo.getUserId());
        userOutParam.setAccount(userDo.getAccount());
        userOutParam.setEmail(userDo.getEmail());
        userOutParam.setPhone(userDo.getPhone());
        userOutParam.setNickname(userDo.getNickname());
        userOutParam.setAvatar_lg(userDo.getAvatarLg());
        userOutParam.setAvatar_md(userDo.getAvatarMd());
        userOutParam.setAvatar_sm(userDo.getAvatarSm());
        userOutParam.setSignature(userDo.getSignature());
        ArrayList<UserInterestDoOut> userInterestDoOuts = (ArrayList)userInterestDoMapper.queryAllInterestsByUserId(userDo.getUserId());
        if(userDo.getBan() != null)
            userOutParam.setBan(userDo.getBan());
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
            userOutParam.setNickname(userDo.getNickname());
            userOutParam.setAvatar_lg(userDo.getAvatarLg());
            userOutParam.setAvatar_md(userDo.getAvatarMd());
            userOutParam.setAvatar_sm(userDo.getAvatarSm());
            userOutParam.setSignature(userDo.getSignature());
            ArrayList<UserInterestDoOut> userInterestDoOuts = (ArrayList)userInterestDoMapper.queryAllInterestsByUserId(userDo.getUserId());
            userOutParam.setInterest(userInterestDoOuts);
            if(userDo.getBan() != null)
                userOutParam.setBan(userDo.getBan());
            userOutParams.add(userOutParam);
        }
        return userOutParams;
    }



}
