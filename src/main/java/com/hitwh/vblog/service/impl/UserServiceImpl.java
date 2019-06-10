package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.ArticleAndUserDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.bean.UserInterestDo;
import com.hitwh.vblog.bean.UserInterestDoOut;
import com.hitwh.vblog.inparam.UserInparam;
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
    public void updateUserInfo(UserDo userDo) throws BusinessException {
        if(userDo.getUserId() == null || userDo.getUserId() <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        userDoMapper.updateByPrimaryKeySelective(userDo);
    }

    @Override
    public void updateUserInterest(List<Integer> userInterestDos, Integer userId) throws BusinessException {
        if(userInterestDos == null || userInterestDos.size() == 0)
            return;

        Integer interestNum = userInterestDoMapper.queryByUserIdNum(userId);
        if(interestNum > 0)
            userInterestDoMapper.deleteInterestByUserId(userId);

        for(int i=0; i<userInterestDos.size(); i++){
            UserInterestDo userInterestDo = new UserInterestDo();
            userInterestDo.setUserId(userId);
            userInterestDo.setLabelId(userInterestDos.get(i));
            userInterestDoMapper.insert(userInterestDo);
        }
    }

    @Override
    public void banUser(UserInparam userInparam) throws BusinessException {
        if (userInparam.getUid() == null || userInparam.getBan_user_id() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        UserDo userDo = userDoMapper.selectAdmin(userInparam.getUid());
        if (userDo == null || userDo.getUserId() == null)
            throw new BusinessException(EnumError.UNAUTHORIZED);

        UserDo userDo1 = new UserDo();
        userDo1.setUserId(userInparam.getBan_user_id());
        userDo1.setBan(1);
        userDoMapper.updateByPrimaryKeySelective(userDo1);

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
            userOutParams.add(userOutParam);
        }
        return userOutParams;


    }

}
