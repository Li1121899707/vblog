package com.hitwh.vblog.service.impl;

import com.hitwh.vblog.bean.DemoUserDo;
import com.hitwh.vblog.bean.DemoUserPwdDo;
import com.hitwh.vblog.mapper.DemoUserDoMapper;
import com.hitwh.vblog.mapper.DemoUserPwdDoMapper;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.service.DemoService;
import com.hitwh.vblog.model.DemoModel;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoUserDoMapper demoUserDoMapper;
    @Autowired
    private DemoUserPwdDoMapper demoUserPwdDoMapper;
    @Autowired
    private ValidatorImpl validator;


    /*********************************************************************************************/
    /**
     * 增
     * */
    @Override
    @Transactional
    public Integer demoRegister(DemoModel demoModel) throws BusinessException {
        if(demoModel == null){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }

        // 验证数据是否合法，不合法抛出数据不合法异常，由于使用了插件，所以result中包含的是所有参数不合法的信息
        ValidationResult result = validator.validate(demoModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // 将领域模型转为Dao模型
        DemoUserDo demoUserDo = convertFromModel(demoModel);
        // 向userDao表插入数据，返回主键，即userId
        Integer usercolumn = demoUserDoMapper.insertSelective(demoUserDo);

        // 插入数据返回的数值，代表影响的行数
        if(usercolumn != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        // 领域模型更新，将userId插入
        demoModel.setUserId(demoUserDo.getUserId());

        // 将领域模型转为Dao模型
        DemoUserPwdDo demoUserPwdDo = convertPwdFromModel(demoModel);
        // 向password表插入数据
        Integer pwdcolumn = demoUserPwdDoMapper.insertSelective(demoUserPwdDo);

        // 插入数据返回的数值，代表影响的行数
        if(pwdcolumn != 1)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);

        return 1;
    }

    // Model 转 数据库实体类
    private DemoUserDo convertFromModel(DemoModel demoModel){
        if(demoModel == null)
            return null;
        DemoUserDo demoUserDo = new DemoUserDo();
        BeanUtils.copyProperties(demoModel, demoUserDo);
        return demoUserDo;
    }

    // Model 转 数据库实体类
    private DemoUserPwdDo convertPwdFromModel(DemoModel demoModel){
        if(demoModel == null)
            return null;
        DemoUserPwdDo demoUserPwdDo = new DemoUserPwdDo();
        demoUserPwdDo.setUserId(demoModel.getUserId());
        demoUserPwdDo.setUserPwd(demoModel.getUserPwd());
        return demoUserPwdDo;
    }


    /*********************************************************************************************/
    /**
     * 查
     * @param id
     * @return
     */
    // 读取数据库中userinfo userpassword
    @Override
    public DemoModel getDemoInfo(Integer id) {
        // 使用实体类接收数据库读出的数据
        DemoUserDo demoUserDo = demoUserDoMapper.selectByPrimaryKey(id);
        if(demoUserDo == null) return null;
        DemoUserPwdDo demoUserPwdDo = demoUserPwdDoMapper.selectByUserId(demoUserDo.getUserId());
        return convertFromDataObject(demoUserDo, demoUserPwdDo);
    }

    private DemoModel convertFromDataObject(DemoUserDo demoUserDo, DemoUserPwdDo demoUserPwdDo){
        DemoModel demoModel = new DemoModel();
        BeanUtils.copyProperties(demoUserDo, demoModel);
        BeanUtils.copyProperties(demoUserPwdDo, demoModel);
        return demoModel;
    }
}
