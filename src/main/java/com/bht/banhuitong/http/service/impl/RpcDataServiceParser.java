package com.bht.banhuitong.http.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.bht.banhuitong.exception.RpcExceptionMessage;
import com.bht.banhuitong.http.HttpClient;

public class RpcDataServiceParser {

	public Logger logger = Logger.getLogger(RpcDataServiceParser.class);

	private static RpcDataServiceParser ServiceDataParser = null;

	public static RpcDataServiceParser getInstance() {
		if (ServiceDataParser == null) {
			ServiceDataParser = new RpcDataServiceParser();
		}
		return ServiceDataParser;
	}

	public byte[] byteValue(final String submitType, String loginUrl, Map<String, String> paramMap) {
		byte[] bytes = null;
		bytes = HttpClient.getInstance().sendByteRequest(submitType, loginUrl, initParams(paramMap));
		return bytes;
	}

	public String stringValue(final String submitType, String loginUrl, Map<String, String> paramMap) {
		String str = null;
		str = HttpClient.getInstance().sendRequest(submitType, loginUrl, initParams(paramMap));
		logger.info(str);
		return str;
	}

	/**
	 * 
	 * @param isLoginReq
	 * @param submitType
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> listValue(final String submitType, String url, Map<String, String> paramMap) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String str = HttpClient.getInstance().sendRequest(submitType, url, initParams(paramMap));

		RpcExceptionMessage rpcExceptionMessage = new RpcExceptionMessage(str);
		if (rpcExceptionMessage.isHasException()) {
			logger.info("have exception:" + str);
			Map<String, String> retMap = new HashMap<String, String>();
			retMap.put("error", rpcExceptionMessage.getM_exception());
			list.add(retMap);
			return list;
		}

		if (str.startsWith("[") && str.endsWith("]")) {
			str = str.substring(1, str.length() - 1);
		}
		if (str.length() > 2) {
			str = str.substring(1, str.length() - 1);
			str = str.replace("{", "");
			list = new ArrayList<Map<String, String>>();
			for (String s : str.split("},")) {
				Map<String, String> map = new HashMap<String, String>();
				for (String ss : s.split(",")) {
					if (ss.contains(":")) {
						String[] item = ss.split(":");
						String key = checkStr(item[0].trim());
						String value = checkStr(item[1].trim());
						map.put(key, value);
					}
				}
				list.add(map);
			}
		}

		return list;
	}

	private String checkStr(String str) {
		str = str.replace("\r", "").replace("\n", "");

		if (str.startsWith("\"") && str.endsWith("\"")) {
			if (str.length() > 2) {
				str = str.substring(1, str.length() - 1);
			} else {
				str = "";
			}
		}

		if (str == null) {
			str = "";
		}

		return str;
	}

	/**
	 * 初始化服务参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private BasicNameValuePair[] initParams(Map<String, String> paramMap) {
		List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
		for (String key : paramMap.keySet()) {
			paramList.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		BasicNameValuePair[] parms = (BasicNameValuePair[]) paramList.toArray(new BasicNameValuePair[paramList.size()]);
		return parms;
	}

	public static void main(String[] args) {
		String ss = "\"department\":\"销售四部\"";

		if (ss.contains(":")) {
			String[] item = ss.split(":");
			String key = item[0];
			if (key.startsWith("\"") && key.endsWith("\"")) {
				key = key.substring(1, key.length() - 1);
			}
			String value = item[1];
			if (value.startsWith("\"") && value.endsWith("\"")) {
				if (value.length() > 2) {
					value = value.substring(1, value.length() - 1);
				} else {
					value = "";
				}
			}
			System.out.println("key:" + key);
			System.out.println("value:" + value);
		}
	}
}
