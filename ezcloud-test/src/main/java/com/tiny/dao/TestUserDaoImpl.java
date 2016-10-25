package com.tiny.dao; 
import com.ezcloud.framework.dao.JpaBaseDaoImpl;
import org.springframework.stereotype.*;
import javax.persistence.*;import com.tiny.entity.TestUser;
@Repository("TestUserJpaDao") 
public class TestUserDaoImpl extends JpaBaseDaoImpl<TestUser, Long> implements TestUserDao{
}
