package com.bht.banhuitong.server.impl;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.db.service.InvestorDbService;
import com.bht.banhuitong.db.service.impl.InvestorDbServiceImpl;
import com.bht.banhuitong.server.InvestorService;

public class InvestorServiceImpl extends BaseServiceServlet implements InvestorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<Map<String, String>> queryInvestorInfoList(Map<String, String> paramMap)
			throws IllegalArgumentException {
		InvestorDbService service = new InvestorDbServiceImpl();

		InvestorDbService proxyService = getProxyService(service);

		List<Map<String, String>> results = proxyService.getInvestorSumAmtInfoList(paramMap);

		return results;
	}

}
