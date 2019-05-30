package com.hitwh.vblog.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    // 实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        // set 存放了validator未通过验证的结果
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size() > 0){
            // 有错误
            result.setHasErrors(true);
            // 遍历
            constraintViolationSet.forEach(objectConstraintViolation -> {
                String errMsg = objectConstraintViolation.getMessage();
                String propertyName = objectConstraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator 通过工厂的初始化方式使其实例化
        this.validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();
    }
}
