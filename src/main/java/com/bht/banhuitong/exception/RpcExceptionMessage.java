package com.bht.banhuitong.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RpcExceptionMessage {
	private  String m_exception = null;
	private  Date date = null;
	private  boolean hasException = false;
	private Map<String,Object> errorMap = new HashMap<String,Object>();

	public RpcExceptionMessage(String result) {
		super();
		parserResult(result);
	}
	/**
	 * 解析异常信息
	 * 
	 * @param result
	 * 			@@@ERROR@@@1*您还未登录，请您去登录！
	 */
	public void parserResult(String result) {
		clearExceptionMessage();
		if (result!=null&&result.contains("@@@ERROR@@@")) {
			result = result.substring(result.indexOf("*") + 1);
			this.date = new Date();
			this.m_exception = result;
			this.hasException = true;
			this.errorMap.put("error", this);
		}
	}

	public String getM_exception() {
		return this.m_exception;
	}

	public Date getDate() {
		return this.date;
	}

	public boolean isHasException() {
		return this.hasException;
	}

	/**
	 * 异常信息归位：清空异常信息
	 */
	public void clearExceptionMessage() {
		this.date = null;
		this.m_exception = null;
		this.hasException = false;
	}

	public Map<String, Object> getErrorMap() {
		return this.errorMap;
	}
	
}
