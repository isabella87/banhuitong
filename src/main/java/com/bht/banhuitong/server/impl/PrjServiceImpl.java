package com.bht.banhuitong.server.impl;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.db.service.PrjDbService;
import com.bht.banhuitong.db.service.impl.PrjDbServiceImpl;
import com.bht.banhuitong.http.service.RpcService;
import com.bht.banhuitong.http.service.impl.RpcServiceImpl;
import com.bht.banhuitong.server.PrjService;

/**
 * The server-side implementation of the RPC service.
 */
public class PrjServiceImpl extends BaseServiceServlet implements PrjService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<Map<String, String>> queryPrjList2(Map<String, String> paramMap) throws IllegalArgumentException {

		PrjDbService service = new PrjDbServiceImpl();
		
		PrjDbService proxyService = getProxyService(service);
		
		List<Map<String, String>> results = proxyService.queryPrjList(paramMap);
		
		return results;
	}
	
	
	@Override
	public List<Map<String, String>> queryPrjList(Map<String, String> paramMap) throws IllegalArgumentException {
		
		RpcService service = new RpcServiceImpl();
		
		RpcService proxyService = getProxyService(service);
		
		return proxyService.queryProjects(paramMap);
		
	}

}
