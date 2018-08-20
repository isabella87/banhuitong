package com.bht.banhuitong.db.service;

import java.util.List;
import java.util.Map;

/**
 * 投资用户信息相关服务
 * 
 * @author Administrator
 *
 */
public interface InvestorDbService {

	/**
	 * 获取投资用户有效投资额及相关信息
	 * @return
	 */
	public List<Map<String, String>> getInvestorSumAmtInfoList(Map<String,String> param);

}
