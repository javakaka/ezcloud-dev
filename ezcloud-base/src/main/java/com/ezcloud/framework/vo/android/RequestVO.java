package com.ezcloud.framework.vo.android;

import com.ezcloud.framework.vo.IVO;

public class RequestVO extends IVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestVO() {
		super();
	}

	public RequestVO(int paramInt, String sServiceName) {
		super(paramInt, sServiceName);
	}

	public RequestVO(int paramInt) {
		super(paramInt);
	}

	public RequestVO(String sServiceName) {
		super(sServiceName);
	}
}
