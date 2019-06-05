package com.hitwh.vblog.service;

import com.hitwh.vblog.model.ThumbModel;
import com.hitwh.vblog.response.BusinessException;

/**
 * @author 11218
 * @description: 点赞Service
 * @date 2019/6/517:00
 */
public interface ThumbService {
    void insertThumbRecord(ThumbModel thumbRecordModel) throws BusinessException;

    Integer countThumbNum(Integer articleId) throws BusinessException;

    void deleteThumbRecord(ThumbModel thumbRecordModel) throws BusinessException;
}
