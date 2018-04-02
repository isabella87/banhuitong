package com.bht.banhuitong.dbservice;

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
