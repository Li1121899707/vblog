package com.hitwh.vblog.service;

import com.hitwh.vblog.bean.ConcernRecordDo;
import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.outparam.ConcernOutParam;
import com.hitwh.vblog.response.BusinessException;

import java.util.List;

public interface ConcernService {
    void insert(ConcernModel concernModel) throws BusinessException;

    void delete(ConcernModel concernModel) throws BusinessException;

    List<ConcernOutParam> queryFollower(Integer userId) throws BusinessException;

    List<ConcernOutParam>  queryTarget(Integer userId) throws BusinessException;
}
