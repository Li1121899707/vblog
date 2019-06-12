package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.TokenDo;
import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.mapper.TokenDoMapper;
import com.hitwh.vblog.mapper.UserDoMapper;
import com.hitwh.vblog.model.LoginModel;
import com.hitwh.vblog.outparam.LoginOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.LoginService;
import com.hitwh.vblog.util.MyMd5;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDoMapper userDoMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private TokenDoMapper tokenDoMapper;



//    @Override
//    public Map<String,Object> getLoginInfoByAccout(LoginModel loginModel) throws BusinessException {
//
//        Map<String,Object> returnMap = new HashMap<>();
//
//        if(loginModel == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
//
//        ValidationResult result = validator.validate(loginModel);
//        if(result.isHasErrors()){
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
//        }
//        //判断用户密码是否正确
//        UserDo userDo = new UserDo();
//        BeanUtils.copyProperties(loginModel, userDo);
//
//        //查询用户信息
//        UserDo userDoFromTable = userDoMapper.selectIfLogin(userDo);
//        if(userDoFromTable == null)
//            throw new BusinessException(EnumError.ACCOUNT_NOT_EXIST);
//
//        Integer salt = userDoFromTable.getSalt();
//        String saltPassword = MyMd5.md5Encryption(userDo.getPwd()) + MyMd5.md5Encryption(salt.toString());
//        if(!MyMd5.md5Encryption(saltPassword).equals(userDoFromTable.getPwd()))
//            throw new BusinessException(EnumError.USER_PASSWORD_ERROR);
//
//        if(userDoFromTable.getBan() == 1)
//            throw new BusinessException(EnumError.USER_HIDDEN);
//
//        TokenDo tokenDo = new TokenDo();
//        Map<String,Object> map = MyMd5.GetToken(String.valueOf(userDo.getUserId()));
//        tokenDo.setUserId(userDoFromTable.getUserId());
//        long current = Long.valueOf(map.get("currentTime").toString());
//        tokenDo.setToken(map.get("token").toString());
//        tokenDo.setCreateTime(new Date(current));
//        tokenDo.setExpiryTime(new Date(Long.valueOf(map.get("expiryTime").toString())));
//        //tokenDoMapper.insert(tokenDo);
//        tokenDoMapper.updateByPrimaryKeySelective(tokenDo);
//        returnMap.put("allowance",userDoFromTable.getAllowance());
//        returnMap.put("uid",userDoFromTable.getUserId());
//        returnMap.put("token",map.get("token").toString());
//        return returnMap;
//    }

    @Override
    public Boolean keyValidate(String key, Integer uid, long Request_time) throws BusinessException {
        TokenDo tokenDo = tokenDoMapper.selectByPrimaryKey(uid);
        if(tokenDo == null)
            return false;
        String token = tokenDo.getToken();
        System.out.println("token : " +token);
        int start = (int) (Request_time % 4);
        System.out.println("start : " + start);
        char[] charToken = new char[8];
        try {
            for(int i = 0; i < 8; i++){
                System.out.println(charToken[i]);
                charToken[i] = token.charAt(start);
                start += 4;
            }
        }catch (Exception e){
            throw new BusinessException(EnumError.TOKEN_ERROR);
        }


        System.out.println("token  " + token );
        System.out.println("char  " + String.valueOf(charToken));
        System.out.println("uid  " + uid);
        System.out.println("request_time  " +String.valueOf(Request_time));

        String finalKey = String.valueOf(charToken) + uid.toString() + String.valueOf(Request_time);

        System.out.println("finalkey  " + finalKey);

        String md5;

        try {
            md5 = MyMd5.md5Encryption(finalKey);
        } catch (BusinessException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("key  " + key);
        System.out.println("md5  " + md5);

        if (md5.equals(key)){
            System.out.println("key success");
            return true;
        }

        else{
            System.out.println("key fail");
            return false;
        }
    }

    @Override
    public Boolean tokenValidate(Integer uid, long Request_time) {
        TokenDo tokenDo = tokenDoMapper.selectByPrimaryKey(uid);
        if(tokenDo == null)
            return false;
        if(Request_time > tokenDo.getExpiryTime().getTime())
            return false;

        return true;
    }

    @Override
    public String renewToken(Integer uid, String token) throws BusinessException {
        TokenDo oldtokenDo = tokenDoMapper.selectByPrimaryKey(uid);
        System.out.println("old token : " + oldtokenDo.getToken());
        System.out.println("token : " + token);
        if(!token.equals(oldtokenDo.getToken()))
            throw new BusinessException(EnumError.TOKEN_RENEW_FAILED);

        Map<String,Object> returnMap = new HashMap<>();

        TokenDo tokenDo = new TokenDo();
        Map<String,Object> map = MyMd5.GetToken(String.valueOf(uid));
        tokenDo.setUserId(uid);
        long current = Long.valueOf(map.get("currentTime").toString());
        tokenDo.setToken(map.get("token").toString());
        tokenDo.setCreateTime(new Date(current));
        tokenDo.setExpiryTime(new Date(Long.valueOf(map.get("expiryTime").toString())));
        tokenDoMapper.updateByPrimaryKeySelective(tokenDo);
        return tokenDo.getToken();
    }

    public LoginOutParam returnMap(UserDo userDo) throws BusinessException {
//        Map<String,Object> returnMap = new HashMap<>();
        LoginOutParam loginOutParam = new LoginOutParam();
        TokenDo tokenDo = new TokenDo();
        Map<String,Object> map = MyMd5.GetToken(userDo.getAccount());
        tokenDo.setUserId(userDo.getUserId());
        long current = Long.valueOf(map.get("currentTime").toString());
        tokenDo.setToken(map.get("token").toString());
        tokenDo.setCreateTime(new Date(current));
        tokenDo.setExpiryTime(new Date(Long.valueOf(map.get("expiryTime").toString())));

        tokenDoMapper.updateByPrimaryKeySelective(tokenDo);

        loginOutParam.setAllowance(userDo.getAllowance());
        loginOutParam.setUid(userDo.getUserId());
        loginOutParam.setToken(map.get("token").toString());
        loginOutParam.setNickname(userDo.getNickname());
        loginOutParam.setAvatar_lg(userDo.getAvatarLg());
        loginOutParam.setAvatar_md(userDo.getAvatarMd());
        loginOutParam.setAvatar_sm(userDo.getAvatarSm());


//        returnMap.put("allowance",userDo.getAllowance());
//        returnMap.put("uid",userDo.getUserId());
//        returnMap.put("token",map.get("token").toString());
        return loginOutParam;
    }

//    @Override
//    public Map<String, Object> getLoginInfoByPhone(LoginModel loginModel) throws BusinessException {
//        //判断电话和密码不为空
//        if(loginModel.getPhone() == null || loginModel.getPwd() == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
//        //将model中的数据封装到userDo
//        UserDo userDo = new UserDo();
//        userDo.setPhone(loginModel.getPhone());
//        userDo.setPwd(loginModel.getPwd());
//        //利用phone查询用户信息
//        UserDo userDoFromTable = userDoMapper.selectIfLoginByPhone(userDo);
//
//        if(userDoFromTable == null)
//            throw new BusinessException(EnumError.PHONE_NOT_EXIST);
//
//        System.out.println(userDoFromTable.getAccount());
//        Integer salt = userDoFromTable.getSalt();
//        String saltPassword = MyMd5.md5Encryption(userDo.getPwd()) + MyMd5.md5Encryption(salt.toString());
//        if(!MyMd5.md5Encryption(saltPassword).equals(userDoFromTable.getPwd()))
//            throw new BusinessException(EnumError.USER_PASSWORD_ERROR);
//
//        if(userDoFromTable.getBan() == 1)
//            throw new BusinessException(EnumError.USER_HIDDEN);
//
//        Map<String,Object> map = returnMap(userDoFromTable);
//
//        return map;
//    }

//    @Override
//    public Map<String, Object> getLoginInfoByEmail(LoginModel loginModel) throws BusinessException {
//        //判断邮箱和密码不为空
//        if(loginModel.getEmail() == null || loginModel.getPwd() == null)
//            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");
//        //将model中的数据封装到userDo
//        UserDo userDo = new UserDo();
//        userDo.setEmail(loginModel.getEmail());
//        userDo.setPwd(loginModel.getPwd());
//        //利用phone查询用户信息
//        UserDo userDoFromTable = userDoMapper.selectIfLoginByEmail(userDo);
//
//        if(userDoFromTable == null)
//            throw new BusinessException(EnumError.EMAIL_NOT_EXIST);
//
//        System.out.println(userDoFromTable.getAccount());
//        Integer salt = userDoFromTable.getSalt();
//        String saltPassword = MyMd5.md5Encryption(userDo.getPwd()) + MyMd5.md5Encryption(salt.toString());
//        if(!MyMd5.md5Encryption(saltPassword).equals(userDoFromTable.getPwd()))
//            throw new BusinessException(EnumError.USER_PASSWORD_ERROR);
//
//        if(userDoFromTable.getBan() == 1)
//            throw new BusinessException(EnumError.USER_HIDDEN);
//
//        Map<String,Object> map = returnMap(userDoFromTable);
//
//        return map;
//    }

    @Override
    public Integer loginValidateByAccount(LoginModel loginModel) throws BusinessException {
        Integer returnInt  = 0;

        if(loginModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        if(loginModel.getAccount() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userDo = new UserDo();

        if(loginModel.getAccount() != null){
            userDo.setAccount(loginModel.getAccount());
            if(userDoMapper.selectIfAccount(userDo) == null)
                returnInt = 1;
            else returnInt  = 0;
        }

        return returnInt;
    }

    @Override
    public Integer loginValidateByPhone(LoginModel loginModel) throws BusinessException {
        Integer returnInt  = 0;

        if(loginModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        if(loginModel.getPhone() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userDo = new UserDo();

        if(loginModel.getPhone() != null){
            userDo.setPhone(loginModel.getPhone());
            if(userDoMapper.selectIfPhone(userDo) == null)
                returnInt = 1;
            else returnInt  = 0;
        }

        return returnInt;
    }

    @Override
    public Integer loginValidateByEmail(LoginModel loginModel) throws BusinessException {
        Integer returnInt  = 0;

        if(loginModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        if(loginModel.getEmail() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        UserDo userDo = new UserDo();

        if(loginModel.getEmail() != null){
            userDo.setEmail(loginModel.getEmail());
            if(userDoMapper.selectIfEmail(userDo) == null)
                returnInt = 1;
            else returnInt  = 0;
        }

        return returnInt;
    }

    @Override
    public Boolean adminValidate(Integer uid) {
        if(uid == null || uid <= 0)
            return false;

        UserDo userDo = userDoMapper.selectAdmin(uid);
        if(userDo == null || userDo.getUserId() != uid)
            return false;
        else
            return true;
    }

    @Override
    public void logOut(Integer uid) throws BusinessException {
        if(uid == null || uid <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        TokenDo tokenDo1 = tokenDoMapper.selectByPrimaryKey(uid);
        if (tokenDo1.getToken().equals("0"))
            throw  new BusinessException(EnumError.LOGOUT_EXIST);
        else {
            TokenDo tokenDo = new TokenDo();
            tokenDo.setUserId(uid);
            tokenDo.setToken("0");

            int result = tokenDoMapper.updateByPrimaryKeySelective(tokenDo);

            if (result != 1)
                throw new BusinessException(EnumError.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LoginOutParam login(LoginModel loginModel) throws BusinessException {
        //判断登录信息和密码不为空
        if(loginModel.getLoginInfo() == null || loginModel.getPwd() == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, "传入参数错误");

        LoginOutParam loginOutParam = new LoginOutParam();
        String password = loginModel.getPwd();
        //将model中的数据封装到userDo
        UserDo userDo = new UserDo();
        userDo.setAccount(loginModel.getLoginInfo());
        userDo.setPwd(loginModel.getPwd());

        UserDo userDoFromTable = userDoMapper.selectIfLogin(userDo);

        if(userDoFromTable == null){
            userDo.setPhone(loginModel.getLoginInfo());
            UserDo userDoFromTable1 = userDoMapper.selectIfLoginByPhone(userDo);

            if(userDoFromTable1 == null){
                userDo.setEmail(loginModel.getLoginInfo());
                UserDo userDoFromTable2 = userDoMapper.selectIfLoginByEmail(userDo);

                if (userDoFromTable2 == null)
                    throw new BusinessException(EnumError.USER_NOT_EXIST);
                else
                    loginOutParam = OutLoginParam(userDoFromTable2,password,userDo);

            }
            else loginOutParam = OutLoginParam(userDoFromTable1,password,userDo);
        }
        else loginOutParam = OutLoginParam(userDoFromTable,password,userDo);


        return loginOutParam;
    }

    @Override
    public LoginOutParam OutLoginParam(UserDo userDoFromTable,String password,UserDo userDo) throws BusinessException {
        Integer salt = userDoFromTable.getSalt();
        String saltPassword = MyMd5.md5Encryption(password) + MyMd5.md5Encryption(salt.toString());
        if(!MyMd5.md5Encryption(saltPassword).equals(userDoFromTable.getPwd()))
            throw new BusinessException(EnumError.USER_PASSWORD_ERROR);

        LoginOutParam loginOutParam = returnMap(userDoFromTable);

        return loginOutParam;

    }

}
