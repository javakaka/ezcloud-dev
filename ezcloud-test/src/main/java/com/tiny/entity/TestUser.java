package com.tiny.entity; 
import java.util.*;
import javax.persistence.*;
import java.io.*;
@Entity
@Table(name = "test_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "")
public class TestUser implements Serializable {
/** 主键 */
private Long id;

/** 姓名 */
private String name;

/**  */
private int age;

/**  */
private String createTime;

/**  */
private String updateTime;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Long getId() {
     return id;
}

public void setId( Long id ) {
     this.id =id;
}

@Column(name = "name",updatable = true)
public String getName() {
     return name;
}

public void setName( String name ) {
     this.name =name;
}

@Column(name = "age",updatable = true)
public int getAge() {
     return age;
}

public void setAge( int age ) {
     this.age =age;
}

@Column(name = "create_time",updatable = true)
public String getCreateTime() {
     return createTime;
}

public void setCreateTime( String createTime ) {
     this.createTime =createTime;
}

@Column(name = "update_time",updatable = true)
public String getUpdateTime() {
     return updateTime;
}

public void setUpdateTime( String updateTime ) {
     this.updateTime =updateTime;
}

}
