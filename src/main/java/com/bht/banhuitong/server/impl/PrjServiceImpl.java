package com.bht.banhuitong.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.dbservice.PrjDbService;
import com.bht.banhuitong.dbservice.impl.PrjDbServiceImpl;
import com.bht.banhuitong.exception.RpcExceptionMessage;
import com.bht.banhuitong.server.PrjService;
import com.bht.banhuitong.serviceConf.RpcService;
import com.bht.banhuitong.serviceConf.RpcServiceImpl;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PrjServiceImpl extends BaseServiceServlet implements PrjService {

	private final static Logger logger = Logger.getLogger(PrjServiceImpl.class);
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
	public List<Map<String, Object>> queryPrjList(Map<String, String> paramMap) throws IllegalArgumentException {
		
		RpcService service = new RpcServiceImpl();
		
		RpcService proxyService = getProxyService(service);
		
		String result = proxyService.queryProjects(paramMap);
		
		/*String result = service.queryProjects(paramMap);*/   //采用非代理形式直接获取数据
				
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		returnMap.clear();
		//TODO 加入error信息
		RpcExceptionMessage rpcExceptionMessage = new RpcExceptionMessage(result);
		if(rpcExceptionMessage.isHasException()) {
			logger.info("have exception:" + result);
			Map<String,Object> retMap = new HashMap<String,Object>();
			retMap.put("error", rpcExceptionMessage.getM_exception());
			returnMap.add(retMap);
			return returnMap;
		}
		result.replace("\r", "").replace("\n", "").replace(" ", "");
//		logger.info("result:" + result);
		returnMap = parserResultStrToListMap(result);
		/*for(Map<String, Object> item :returnMap) {
			logger.info("**********");
			for(String key:item.keySet()) {
				logger.info(key+":"+item.get(key));
			}
		}*/
		return returnMap;
	}

	/**
	 * 解析result成list
	 * 
	 * @param result
	 * @return
	 */
	private List<Map<String, Object>> parserResultStrToListMap(String result) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (!result.contains("}")) {
			return resultList;
		}
		result = result.substring(result.indexOf("{") + 1, result.lastIndexOf("}"));
		result = result.replace(", {", "");
		for (String list : result.split("}")) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (String item : list.split(",")) {
				String[] mapItem = item.split(":");
				String key = checkStr(mapItem[0]);
				String value = checkStr(mapItem[1]);
				map.put(key, value);
			}
			resultList.add(map);
		}
		return resultList;
	}

	private static String checkStr(String str) {
		str = str.replace("\r", "").replace("\n", "").replace(" ", "");
		if (str.startsWith("\"")){
			str = str.substring(1, str.length() - 1);
		}
		if(str ==null) {
			str = "";
		}
		return str;
	}

}
