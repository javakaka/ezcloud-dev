package com.ezcloud.framework.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ezcloud.framework.util.DateEditor;
import com.ezcloud.framework.util.SpringUtils;
import com.ezcloud.framework.vo.IVO;
import com.ezcloud.framework.vo.OVO;

/**
 * 接口Cotroller基类
 * @author TongJianbo
 *
 */
public class ApiBaseController {
	protected ThreadLocal<IVO> ivo ;
	protected ThreadLocal<OVO> ovo;
	
	public IVO getIvo() {
		return ivo.get();
	}

	public void setIvo(IVO ivo) {
		this.ivo.set( ivo );
	}

	public OVO getOvo() {
		return ovo.get();
	}

	public void setOvo(OVO ovo) {
		this.ovo.set( ovo );
	}

	public ApiBaseController() {
		super();
		ivo =new ThreadLocal<IVO>(){
			@Override
			protected IVO initialValue() {
				return new IVO();
			}
		};
		ovo =new ThreadLocal<OVO>(){
			@Override
			protected OVO initialValue() {
				return new OVO(0,"","");
			}
		};
	}

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}
	
	public IVO parseRequest(HttpServletRequest request)
	{
		IVO ivo =(IVO)request.getAttribute("ivo");
		if(ivo == null )
		{
			ivo = new IVO();
		}
		return ivo;
	}
}
