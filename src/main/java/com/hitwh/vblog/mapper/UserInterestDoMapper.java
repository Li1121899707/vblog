package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.UserInterestDo;
import com.hitwh.vblog.bean.UserInterestDoOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 臧博浩
 * @date 2019/6/3 23:17
 */
public interface UserInterestDoMapper {

    int insert(UserInterestDo record);

    int insertSelective(UserInterestDo record);

    List<UserInterestDoOut> queryAllInterestsByUserId(@Param("userId") Integer userId);

    Integer deleteInterestByUserId(@Param("userId") Integer userId);
}
