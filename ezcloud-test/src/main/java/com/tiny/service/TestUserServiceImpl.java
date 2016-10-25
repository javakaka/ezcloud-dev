package com.tiny.service; 
import com.tiny.entity.TestUser;
import com.tiny.dao.TestUserDao;
import com.ezcloud.framework.service.JpaBaseServiceImpl;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import javax.persistence.*;
import javax.annotation.Resource;
@Repository("TestUserJpaService") 
public class TestUserServiceImpl extends JpaBaseServiceImpl<TestUser, Long> implements TestUserService{
 @Resource(name = "TestUserJpaDao") 
 private TestUserDao testUserDao; 
 @Resource(name = "TestUserJpaDao") 
 public void setBaseDao(TestUserDao testUserDao) { 
 super.setBaseDao(testUserDao); 
 } 
 } 
