package com.hitwh.vblog.service;

import com.hitwh.vblog.model.ConcernModel;
import com.hitwh.vblog.response.BusinessException;

import java.util.Map;

public interface ConcernService {
    void insert(ConcernModel concernModel) throws BusinessException;

    void delete(ConcernModel concernModel) throws BusinessException;

    Map<String, Object> queryFollower(Integer start, Integer end, Integer userId) throws BusinessException;

    Map<String, Object> queryTarget(Integer start, Integer end, Integer userId) throws BusinessException;

    boolean isConcerned(ConcernModel concernModel) throws BusinessException;
}
