package com.sybinal.shop.mapper;

import com.sybinal.shop.model.UserRoles;
import com.sybinal.shop.model.UserRolesExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRolesMapper {


    int countByExample(UserRolesExample example);


    int deleteByExample(UserRolesExample example);


    int deleteByPrimaryKey(Integer userRoleId);


    int insert(UserRoles record);


    int insertSelective(UserRoles record);


    List<UserRoles> selectByExample(UserRolesExample example);


    UserRoles selectByPrimaryKey(Integer userRoleId);


    int updateByExampleSelective(@Param("record") UserRoles record,
                                 @Param("example") UserRolesExample example);


    int updateByExample(@Param("record") UserRoles record,
                        @Param("example") UserRolesExample example);


    int updateByPrimaryKeySelective(UserRoles record);

    int updateByPrimaryKey(UserRoles record);
}