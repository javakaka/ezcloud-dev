package com.szhq.dm.dal.mapper;

import org.apache.ibatis.annotations.Param;

import com.szhq.dm.dal.entity.User;

public interface UserMapper extends BaseMapper{
  
  User loadByNameAndPass(@Param("username")String username,@Param("password")String password);
  
}
