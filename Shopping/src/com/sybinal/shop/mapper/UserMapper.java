package com.sybinal.shop.mapper;

import com.sybinal.shop.model.User;
import com.sybinal.shop.model.UserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int countByExample(UserExample example);


    int deleteByExample(UserExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(User record);


    int insertSelective(User record);


    List<User> selectByExample(UserExample example);


    User selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") User record,
                                 @Param("example") UserExample example);


    int updateByExample(@Param("record") User record,
                        @Param("example") UserExample example);


    int updateByPrimaryKeySelective(User record);


    int updateByPrimaryKey(User record);


    User selectById(@Param("id") Integer id);


    List<User> selectUserByCondition(User record);


    int selectCountByCondition(User record);

    int selectCountUserNameByCondition(String userName);

    int selectCountUserNameByUpdate(User record);
}