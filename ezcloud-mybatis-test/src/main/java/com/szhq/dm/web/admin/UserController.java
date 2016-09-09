package com.szhq.dm.web.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezcloud.framework.plugin.mybatis.MapContainer;
import com.ezcloud.framework.util.IdGenerator;
import com.ezcloud.framework.util.StringUtils;
import com.szhq.dm.dal.entity.User;
import com.szhq.dm.service.UserService;
import com.szhq.dm.web.admin.form.validator.UserFormValidator;

@Controller
@RequestMapping("/backend/users")
public class UserController{
  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", userService.list(page, 15));
    return "/mybatispage/platform/user/list";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String insert(User user, String repass, Model model){
    MapContainer form = UserFormValidator.validateInsert(user, repass);
    if(!form.isEmpty()){
      model.addAllAttributes(form);
      return "backend/user/edit";
    }

    user.setId(IdGenerator.uuid19());
    user.setCreateTime(new Date());
    user.setLastUpdate(user.getCreateTime());

    userService.insert(user);
    return "redirect:/backend/users";
  }

  @RequestMapping(method = RequestMethod.PUT)
  public String update(User user, String repass, Model model){
    MapContainer form = UserFormValidator.validateUpdate(user, repass);
    if(!form.isEmpty()){
      model.addAllAttributes(form);
      model.addAttribute("user", user);
      return "backend/user/edit";
    }

    user.setLastUpdate(new Date());
    userService.update(user);
    return "redirect:/backend/users";
  }

  @ResponseBody
  @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("userid") String userid){
    userService.deleteById(userid);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(String uid, Model model){
    if(!StringUtils.isEmptyOrNull(uid))
      model.addAttribute("row", userService.loadById(uid));
    return "/mybatispage/platform/user/edit";
  }

  @RequestMapping(value = "/my", method = RequestMethod.GET)
  public String my(Model model){
    model.addAttribute("my", null);
    return "backend/user/my";
  }

  @RequestMapping(value = "/my", method = RequestMethod.PUT)
  public String upmy(User user, String repass, Model model){
    MapContainer form = UserFormValidator.validateMy(user, repass);
    if(!form.isEmpty()){
      model.addAllAttributes(form);
      model.addAttribute("my", user);
      return "backend/user/my";
    }
    user.setLastUpdate(new Date());
    userService.update(user);
    return "redirect:/backend/users";
  }

}
