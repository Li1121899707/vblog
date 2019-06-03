package com.hitwh.vblog.service;

import com.hitwh.vblog.outparam.LabelOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface LabelService {

    LabelOutParam queryLabelById(Integer id) throws BusinessException;

    PageResponse queryAllInterests(Integer start, Integer end) throws BusinessException;




}
