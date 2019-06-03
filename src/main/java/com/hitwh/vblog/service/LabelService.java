package com.hitwh.vblog.service;

import com.hitwh.vblog.outparam.LabelOutParam;
import com.hitwh.vblog.response.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabelService {

    LabelOutParam queryLabelById(Integer id) throws BusinessException;

    List<LabelOutParam> queryAllInterests(Integer start, Integer end);




}
