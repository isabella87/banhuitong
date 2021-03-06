package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/investor")
public interface InvestorService extends RemoteService {
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @param captchaCode
	 * @return
	 * @throws IllegalArgumentException
	 */
	List<Map<String,String>> queryInvestorInfoList(Map<String,String> paramMap) throws IllegalArgumentException;
	
}
