package com.bht.banhuitong.dbservice;

import java.util.List;
import java.util.Map;
/**
 * 账户相关服务
 * @author Administrator
 *
 */
public interface AccountDbService {

	
	public List<Map<String,String>> getAccountInfoByLoginName(final String loginName);
	
	public List<Map<String,String>> getAccountInfoByAuId(final long auId);
	
	public long checkLogin(final String loginName,final String pwd) ;
	
	public String checkBgLogin(final String loginName,final String pwd) ;
	
}
