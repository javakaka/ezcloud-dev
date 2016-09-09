package com.szhq.dm.web.admin.form.validator;

import com.ezcloud.framework.plugin.mybatis.MapContainer;
import com.ezcloud.framework.util.StringUtils;
import com.szhq.dm.dal.entity.User;

public class UserFormValidator{

  public static MapContainer validateInsert(User user, String repass){
    MapContainer form = validateUser(user);
    if(StringUtils.isEmptyOrNull(user.getPassword()) || StringUtils.isEmptyOrNull(repass)){
      form.put("password", "需填写用户密码");
    }
    if(!user.getPassword().equals(repass) ){
      form.put("password", "两次密码不一致或者密码格式不对");
    }

    return form;
  }

  public static MapContainer validateUpdate(User user, String repass){
    MapContainer form = validateUser(user);
    if(!StringUtils.isEmptyOrNull(user.getPassword())
        && (!user.getPassword().equals(repass) )){
      form.put("password", "两次密码不一致或者密码格式不对");
    }else if(StringUtils.isEmptyOrNull(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

  private static MapContainer validateUser(User user){
    MapContainer form = new MapContainer();
    if(StringUtils.isEmptyOrNull(user.getNickName())){
      form.put("nickName", "需填写用户名称");
    }
    if(StringUtils.isEmptyOrNull(user.getEmail()) ){
      form.put("email", "邮箱格式不正确");
    }
    if(StringUtils.isEmptyOrNull(user.getRealName())){
      form.put("realName", "需填写用户真实名称");
    }

    return form;
  }
  
  public static MapContainer validateMy(User user, String repass){
    MapContainer form = new MapContainer();
    if(StringUtils.isEmptyOrNull(user.getEmail()) ){
      form.put("email", "邮箱格式不正确");
    }
    if(StringUtils.isEmptyOrNull(user.getRealName())){
      form.put("realName", "需填写用户真实名称");
    }
    
    if(!StringUtils.isEmptyOrNull(user.getPassword())
        && (!user.getPassword().equals(repass) )){
      form.put("password", "两次密码不一致或者密码格式不对");
    }else if(StringUtils.isEmptyOrNull(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

}
