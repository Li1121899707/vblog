package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.UserInterestDo;

/**
 * @author 臧博浩
 * @date 2019/6/3 23:17
 */
public interface UserInterestDoMapper {

    int insert(UserInterestDo record);

    int insertSelective(UserInterestDo record);
}
