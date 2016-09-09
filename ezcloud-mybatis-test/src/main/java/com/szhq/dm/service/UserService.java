package com.szhq.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezcloud.framework.plugin.mybatis.PageModel;
import com.szhq.dm.dal.entity.User;
import com.szhq.dm.dal.mapper.BaseMapper;
import com.szhq.dm.dal.mapper.UserMapper;

@Service
public class UserService extends BaseService{
  @Autowired
  private UserMapper userMapper;

  public PageModel<User> list(int pageIndex, int pageSize){
    PageModel<User> page = new PageModel<>(pageIndex, pageSize);
    super.list(page);
    return page;
  }
  
  public User login(String username,String password){
    return userMapper.loadByNameAndPass(username, password);
  }

  @Override
  protected BaseMapper getMapper(){
    return userMapper;
  }

}
