package com.bht.banhuitong.server.impl;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import static com.bht.banhuitong.filter.SecurityFilter.userSessionMap;

import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.db.service.impl.AccountDbServiceImpl;
import com.bht.banhuitong.security.AuthenticationToken;
import com.bht.banhuitong.server.AccountService;
import com.bht.banhuitong.shared.fileutils.Export2File;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
public class AccountServiceImpl extends RemoteServiceServlet implements AccountService {

	private final static Logger logger = Logger.getLogger(AccountServiceImpl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param loginName
	 * @return
	 * @throws ServiceException 
	 */
	@Override
	public List<Map<String, String>> getBgAccountInfo() throws Exception {
		AuthenticationToken token = userSessionMap.get(getThreadLocalRequest().getSession().getId());

		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		List<Map<String, String>> results = accountDbService.getBgAccountInfoByUname(token.getUserId());

		for (Map<String, String> map : results) {
			logger.debug("MOBILE:" + map.get("MOBILE"));
			logger.debug("NAME:" + map.get("NAME"));
		}
		return results;
	}
	
	@Override
	public List<Map<String, String>> getAccountInfo() throws Exception {
		AuthenticationToken token = userSessionMap.get(getThreadLocalRequest().getSession().getId());
		long auId = toLong(token.getUserId(),0);

		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		List<Map<String, String>> results = accountDbService.getAccountInfoByAuId(auId);

		for (Map<String, String> map : results) {
			logger.debug("MOBILE:" + map.get("MOBILE"));
			logger.debug("NAME:" + map.get("NAME"));
		}
		return results;
	}

	@Override
	public List<Map<String, String>> exportDataToExcel(Map<String,String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		
		List<Map<String,String>> data = accountDbService.getPullInDataByCon(paramMap);
				//复写excel文件
		if(data != null && !data.isEmpty()) {
			new Export2File().export("luban_yl",Configuration.getString(Configuration.REPORT_PATH), "", "xls", data);
		}
		return data;
	}
			
}
