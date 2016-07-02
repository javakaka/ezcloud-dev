package com.ezcloud.framework.vo;

import com.ezcloud.framework.exp.JException;
/**
 * 输出交互类
 * @author Tong
 *
 */
public class OVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3182839617282164868L;
	/**
	 * 状态码，小于0表示处理时有异常
	 */
	public int iCode;
	/**
	 * 提示信息
	 */
	public String sMsg="";
	/**
	 * 异常信息
	 */
	public String sExp="";

	public OVO() {
	}

	public OVO(int iCode1, String sMsg1, Object excepObj) {
		this.iCode = iCode1;
		this.sMsg = sMsg1;
		if (excepObj != null) {
			if ((excepObj instanceof Throwable))
				this.sExp = JException.getStackTrace((Throwable) excepObj);
			else
				this.sExp = excepObj.toString();
		}
	}

	public void release() {
		if (this.oForm != null) {
			this.oForm.clear();
			this.oForm = null;
		}
		this.sService = null;
	}

	/**
	 * 查看执行状态
	 * @return
	 */
	public String result() {
		return "执行结果：" + this.iCode + "；提示信息："
				+ (this.sMsg == null ? "无" : this.sMsg) + "；异常信息："
				+ (this.sExp == null ? "无" : this.sExp);
	}
}