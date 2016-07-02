package com.ezcloud.framework.vo;

import java.sql.Connection;

/**
 * 输入交互类
 * @author Tong
 *
 */
public class IVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 718471496235159702L;
	
	private Connection _$1;
	
	//服务类型：静态服务或者动态服务
	private int _$2;

	public IVO() {
		this._$2 = 0;
	}

	public IVO(int paramInt) {
		this._$2 = paramInt;
	}

	public IVO(int paramInt, String sServiceName) {
		super(sServiceName);
		this._$2 = paramInt;
	}

	public IVO(String sServiceName) {
		this(0, sServiceName);
	}

	public Connection getConnection() {
		return this._$1;
	}

	public int getType() {
		return this._$2;
	}

	public void release() {
		if (this.oForm != null) {
			this.oForm.clear();
			this.oForm = null;
		}
		this.sService = null;
		this._$1 = null;
	}

	public void setConnection(Connection oConnection) {
		this._$1 = oConnection;
	}

	public void setService(String sServiceName) {
		this.sService = sServiceName;
	}

	public void setType(int paramInt) {
		this._$2 = paramInt;
	}
}