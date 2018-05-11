package com.bht.banhuitong.db.service;

import java.util.List;
import java.util.Map;
/**
 * 项目相关服务
 * @author Administrator
 *
 */
public interface PrjDbService{

	public List<Map<String,String>> queryPrjList(Map<String,String> param);
	
}
