package com.bht.banhuitong.db.service;

import java.util.List;
import java.util.Map;
/**
 * 全部服务
 * @author Administrator
 *
 */
public interface BaseDbServiceFactory {

	//account
	public List<Map<String,String>> getAccountInfoByLoginName(final String loginName);
	
	public List<Map<String,String>> getAccountInfoByAuId(final long auId);
	
	public long checkLogin(final String loginName,final String pwd) ;
	
	public String checkBgLogin(final String loginName,final String pwd) ;
	
	//prj
	public List<Map<String,String>> queryPrjList(Map<String,String> param);
	
}
