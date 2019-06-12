package com.hitwh.vblog.controller;

import com.hitwh.vblog.bean.UserDo;
import com.hitwh.vblog.inparam.UserInparam;
import com.hitwh.vblog.model.UserModel;
import com.hitwh.vblog.outparam.ConcernOutParam;
import com.hitwh.vblog.outparam.UserOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.UserService;
import com.hitwh.vblog.util.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liysuzy
 * @description: TODO
 * @date 2019/6/920:31
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping("/query_by_id")
    public CommonReturnType queryUserById(@RequestBody UserInparam userInparam) throws BusinessException {
        return CommonReturnType.create(userService.queryById(userInparam.getUid()));
    }

    @RequestMapping("/query_by_account")
    public CommonReturnType queryUserByAccount(@RequestBody UserInparam userInparam) throws BusinessException {
        return CommonReturnType.create(userService.queryByAccount(userInparam.getAccount()));
    }

    @RequestMapping("/query_by_email")
    public CommonReturnType queryUserByEmail(@RequestBody UserInparam userInparam) throws BusinessException {
        return CommonReturnType.create(userService.queryByEmail(userInparam.getEmail()));
    }

    @RequestMapping("/query_by_phone")
    public CommonReturnType queryUserByPhone(@RequestBody UserInparam userInparam) throws BusinessException {
        return CommonReturnType.create(userService.queryByPhone(userInparam.getPhone()));
    }

    @LoginRequired
    @RequestMapping("/query_by_label")
    public CommonReturnType queryAllUserByLabel(@RequestBody UserInparam userInparam) throws BusinessException {
        Map<String, Object> map = userService.queryAllUserByLabel(userInparam.getStart(), userInparam.getEnd(), userInparam.getLabel_id());

        if(map == null)
            return CommonReturnType.create(PageResponse.createBlank());

        ArrayList userOutParams = (ArrayList)map.get("list");
        int sum = (int)map.get("sum");

        if(sum == 0)
            return CommonReturnType.success();

        if(userOutParams.size() ==
                userInparam.getEnd() - userInparam.getStart() + 1)
            return CommonReturnType.create(PageResponse.create(userInparam.getStart(),
                    userInparam.getEnd(),sum,userOutParams));
        else
            return CommonReturnType.create(PageResponse.create(userInparam.getStart(),
                    userInparam.getStart() + userOutParams.size() - 1
                    ,sum,userOutParams));
    }

    @LoginRequired(admin = true)
    @RequestMapping("/admin/query_all")
    public CommonReturnType queryAllUser(@RequestBody UserInparam userInparam) throws BusinessException {
        Map<String, Object> map = userService.queryAllUser(userInparam.getStart(), userInparam.getEnd());

        ArrayList userOutParams = (ArrayList)map.get("list");
        int sum = (int)map.get("sum");

        if(sum == 0)
            return CommonReturnType.success();

        if(userOutParams.size() ==
                userInparam.getEnd() - userInparam.getStart() + 1)
            return CommonReturnType.create(PageResponse.create(userInparam.getStart(),
                    userInparam.getEnd(),sum,userOutParams));
        else
            return CommonReturnType.create(PageResponse.create(userInparam.getStart(),
                    userInparam.getStart() + userOutParams.size() - 1
                    ,sum,userOutParams));
    }

    @LoginRequired
    @RequestMapping("/update")
    public CommonReturnType updateUserInfo(@RequestBody UserInparam userInparam) throws BusinessException {
        UserDo userDo = new UserDo();
        userDo.setUserId(userInparam.getUid());
        userDo.setAccount(userInparam.getAccount());
        userDo.setEmail(userInparam.getEmail());
        userDo.setNickname(userInparam.getNickname());
        userDo.setSignature(userInparam.getSignature());
        userDo.setPhone(userInparam.getPhone());
        userDo.setPwd(userInparam.getPwd());
        userDo.setAvatarLg(userInparam.getAvatar_lg());
        userDo.setAvatarMd(userInparam.getAvatar_md());
        userDo.setAvatarSm(userInparam.getAvatar_sm());
        userService.updateUserInfo(userDo);

        List<Integer> interest = userInparam.getInterest();
        userService.updateUserInterest(interest, userInparam.getUid());
        return CommonReturnType.success();
    }

    @LoginRequired(admin = true)
    @RequestMapping("/admin/ban_user")
    public CommonReturnType banUser(@RequestBody UserInparam userInparam) throws BusinessException {
        userService.banUser(userInparam);
        return CommonReturnType.success();
    }

    @RequestMapping("/avatar")
    public CommonReturnType getUserAvatar(@RequestBody UserInparam userInparam) throws BusinessException{
        return  CommonReturnType.create(userService.userAvatar(userInparam.getUid()));
    }
}
