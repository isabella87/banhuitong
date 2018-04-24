package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.BusinessAnnotation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/prj")
public interface PrjService extends RemoteService {
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @param captchaCode
	 * @return
	 * @throws IllegalArgumentException
	 */
	@BusinessAnnotation(serviceno=1,perm=60012,recordLog= true)
	List<Map<String,Object>> queryPrjList(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(serviceno=2,perm=10002 ,recordLog= true)
	List<Map<String,String>> queryPrjList2(Map<String,String> paramMap) throws IllegalArgumentException;
}
