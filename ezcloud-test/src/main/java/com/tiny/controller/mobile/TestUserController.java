package com.tiny.controller.mobile; 
import com.tiny.entity.TestUser;
import com.tiny.service.TestUserService;
 import javax.annotation.*; 
  import javax.servlet.http.*; 
  import org.apache.log4j.*;
  import org.springframework.stereotype.*;
   import org.springframework.web.bind.annotation.*; 
   import com.ezcloud.framework.util.*; 
  import com.ezcloud.framework.vo.*; 
  import com.ezcloud.framework.controller.ApiBaseController; 
  import com.ezcloud.framework.exp.*; 
  import java.sql.*; 
  import java.util.*; 
  import java.text.*; 
 @Controller("TestUserJpaApiController") 
@RequestMapping("/api/test/user")
public class TestUserController extends ApiBaseController {

 @Resource(name = "TestUserService") 
 private TestUserService testUserService; 

private static Logger logger =Logger.getLogger( TestUserController.class ); 
	@RequestMapping(value = "/list") 
	public @ResponseBody String list ( HttpServletRequest request ) throws JException  { 
		OVO ovo =new OVO(); 
		IVO ivo =parseRequest(request); 
		String page =ivo.getString("page","1"); 
		String page_size =ivo.getString("page_size","15"); 
		Integer count =Integer.parseInt( page_size );
		Integer first =(Integer.parseInt( page ) -1) * count; 
		List<TestUser> list =testUserService.findList(first, count, null, null); 
		DataSet ds =new DataSet(); 
		for(int i=0; i< list.size(); i++ ){ 
		 	Row row =new Row();
		 	TestUser item =(TestUser)list.get(i);
		 	row.put("id", String.valueOf( item.getName() ));
		 	row.put("id", String.valueOf( item.getAge() ));
		 ds.add(row);
		 }//end of for loop
		 ovo =new OVO(0,"操作成功","");
		 ovo.set("list", ds);
		 return AesUtil.encode(VOConvert.ovoToJson(ovo));
	}
@RequestMapping(value = "/add")
public @ResponseBody String add(HttpServletRequest request) throws JException {
	OVO ovo =new OVO();
	IVO ivo =parseRequest(request);
	TestUser entity =new TestUser();
	String name =ivo.getString("name",null);
	entity.setName(name);
	String createTime =ivo.getString("createTime",null);
	entity.setCreateTime(createTime);
	testUserService.save( entity );
	ovo =new OVO(0,"操作成功","");
	return AesUtil.encode(VOConvert.ovoToJson(ovo));
}
@RequestMapping(value = "/edit")
public @ResponseBody String edit( HttpServletRequest request ) throws JException {
	OVO ovo =new OVO(0,"","");
	IVO ivo =parseRequest(request);
	String id=ivo.getString("id",null);
	TestUser testUser =testUserService.find(Long.parseLong(id));
	if(testUser == null){
		ovo =new OVO(-1,"记录不存在","");
		return AesUtil.encode(VOConvert.ovoToJson(ovo));
	}
	String name=testUser.getName();
	ovo.set("name", name);
	int age=testUser.getAge();
	ovo.set("age", age);
		return AesUtil.encode(VOConvert.ovoToJson(ovo));
}
/**
*方法说明:备注
**/
@RequestMapping(value = "/delete")
public @ResponseBody String delete(HttpServletRequest request) throws JException {
	OVO ovo =new OVO();
	IVO ivo =parseRequest(request);
	String id =ivo.getString("id",null);
	testUserService.delete(Long.parseLong(id));
	ovo =new OVO(0,"操作成功","");
	return AesUtil.encode(VOConvert.ovoToJson(ovo));
	}	
}	
