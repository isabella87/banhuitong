package com.bht.banhuitong.common;

import com.bht.banhuitong.config.Configuration;

public class CommonMethod {

	public static String initExceptionDesc(int i) {

		return "@@@" + i + "@@@ " + Configuration.getString(String.valueOf(i));
	}
	
	public static void main(String[] args) {
		String errorMsg = "@@@2@@@ 您没有权限，请联系管理员分配权限！";
		String errorCode = errorMsg.substring(errorMsg.indexOf("@@@")+3,errorMsg.lastIndexOf("@@@"));
		
		System.out.println(errorCode);
	}
}
