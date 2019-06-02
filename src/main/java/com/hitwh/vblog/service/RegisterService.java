package com.hitwh.vblog.service;

import com.hitwh.vblog.model.RegisterModel;
import com.hitwh.vblog.response.BusinessException;

public interface RegisterService {
    void register(RegisterModel registerModel) throws BusinessException;
}
