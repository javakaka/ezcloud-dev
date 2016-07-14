package com.ezcloud.framework.controller.robot;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezcloud.framework.controller.BaseController;
import com.ezcloud.framework.service.robot.SystemRobotDatabaseService;
import com.ezcloud.framework.service.robot.SystemRobotService;
import com.ezcloud.framework.vo.DataSet;

@Controller("frameworkSystemRobotController")
@RequestMapping("/system/robot")
public class SystemRobotController  extends BaseController{

	@Resource(name = "frameworkSystemRobotService")
	private SystemRobotService robotService;
	
	@Resource(name = "frameworkSystemRobotDatabaseService")
	private SystemRobotDatabaseService databaseService;
	

	@RequestMapping(value = "/add")
	public String add(ModelMap model) {
		// 查询db列表
		DataSet dbDs=databaseService.queryAllItems();
		model.addAttribute( "db_list", dbDs );
		return "/system/robot/add";
	}
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String LAN_NO, String LAN_NAME, String LAN_STATE, RedirectAttributes redirectAttributes) {
		robotService.getRow().put("LAN_NO", LAN_NO);
		robotService.getRow().put("LAN_NAME", LAN_NAME);
		robotService.getRow().put("LAN_STATE", LAN_STATE);
		robotService.save();
		return "redirect:Language.do";
	}
}