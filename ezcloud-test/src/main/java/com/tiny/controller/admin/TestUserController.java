package com.tiny.controller.admin; 
import com.tiny.entity.TestUser;
import com.tiny.service.TestUserService;
import javax.annotation.*;
import org.springframework.stereotype.Controller;
 import org.springframework.ui.*;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.mvc.support.*;
 import com.ezcloud.framework.page.orm.Page;
import com.ezcloud.framework.page.orm.Pageable;
 import com.ezcloud.framework.controller.BaseController; 
 import com.ezcloud.framework.util.*;
import org.apache.log4j.*;
@Controller("TestUserJpaAdminController") 
@RequestMapping("/admin/test/user")
public class TestUserController extends BaseController {

 @Resource(name = "TestUserService") 
 private TestUserService testUserService; 

private static Logger logger =Logger.getLogger( TestUserController.class ); 
	@RequestMapping(value = "/list") 
	public String list ( Pageable pageable, ModelMap model )  { 
		Page page = testUserService.findPage( pageable ); 
		model.addAttribute("page", page); 
		return "/cxhlpage/platform/test//list"; 
	}
@RequestMapping(value = "/add")
public String add(ModelMap model) {
	return "/cxhlpage/platform/test//add";
}
@RequestMapping(value = "/save", method = RequestMethod.POST)
public String save (TestUser testUser, RedirectAttributes redirectAttributes) {
	logger.info("save TestUser...............");
	testUser.setCreateTime(DateUtil.getCurrentDateTime()) ;
	testUserService.save(testUser) ;
	addFlashMessage( redirectAttributes, Message.success( "success") );
	return "redirect:list.jhtml";
}	
@RequestMapping(value = "/edit")
public String edit(Long id, ModelMap model) {
model.addAttribute("entity", testUserService.find(id));

return "/cxhlpage/platform/test//edit";
}

@RequestMapping(value = "/update")
public String update(TestUser testUser, ModelMap model, RedirectAttributes redirectAttributes) {
	testUser.setUpdateTime(DateUtil.getCurrentDateTime()) ;
	testUserService.update(testUser);
	addFlashMessage( redirectAttributes, Message.success( "success") );
	return "redirect:list.jhtml";
}	
@RequestMapping(value = "/delete")
public @ResponseBody Message delete(Long[] ids) {
	testUserService.delete( ids );
	return SUCCESS_MESSAGE ;
	}	
}	
