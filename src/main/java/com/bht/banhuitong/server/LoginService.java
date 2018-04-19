package com.bht.banhuitong.server;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/login")
public interface LoginService extends RemoteService {
	/**
	 * 验证登录名及密码
	 * @param name
	 * @param pwd
	 * @return 登录名
	 * @throws IllegalArgumentException
	 */
	String login(Map<String,String> paramMap) throws IllegalArgumentException;
	
	String getExcepStr() throws IllegalArgumentException;
	
	String loginImmediately(Map<String,String> paramMap) throws IllegalArgumentException;

	String getImageByte() throws IllegalArgumentException;
}
