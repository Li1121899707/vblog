package com.hitwh.vblog.mapper;

import com.hitwh.vblog.bean.UserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserDoMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId);

    int insert(UserDo record);

    int insertSelective(UserDo record);

    UserDo selectByPrimaryKey(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(UserDo record);

    int updateByPrimaryKey(UserDo record);

    UserDo selectIfLogin(UserDo userDo);

    UserDo selectIfLoginByPhone(UserDo userDo);

    UserDo selectIfLoginByEmail(UserDo userDo);

    UserDo selectIfAccount(UserDo userDo);

    UserDo selectIfPhone(UserDo userDo);

    UserDo selectIfEmail(UserDo userDo);

    UserDo selectAdmin(@Param("userId") Integer userId);

    List<UserDo> selectAllUserByLabel(@Param("start")Integer start, @Param("num") Integer num, @Param("labelId")Integer labelId);

    Integer selectAllUserByLabelNum(@Param("labelId")Integer labelId);

    List<UserDo> selectAllUserWithBan(@Param("start")Integer start, @Param("num") Integer num, @Param("ban") Integer ban);

    List<UserDo> selectAllUser(@Param("start")Integer start, @Param("num") Integer num);

    Integer selectAllUserNum();

    Integer selectAllUserNumWithBan(@Param("ban") Integer ban);

}