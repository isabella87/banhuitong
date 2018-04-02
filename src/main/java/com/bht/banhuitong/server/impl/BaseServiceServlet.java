package com.bht.banhuitong.server.impl;

import java.lang.reflect.Proxy;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BaseServiceServlet extends RemoteServiceServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4490766298770126264L;

	/*public final static Logger busiLogger = Logger.getLogger("busiLogger");

	private String busiLogStr ;

	protected void writeBusiLog(Object object, String methodName, Map<String, String> paramMap) {
		AuthenticationToken token = (AuthenticationToken) getServletContext().getAttribute("__TOKEN__");
		StringBuffer sb = new StringBuffer();
		sb.append("{\"TIMESTAMP\":").append("\"").append(DateUtils.toStandardStr(new Date())).append("\",");
		sb.append("\"STAGE\":").append("\"").append("start").append("\",");
		sb.append("\"MODULE\":").append("\"").append(object.getClass().getName()).append("\",");
		sb.append("\"ACTION\":").append("\"").append(methodName).append("\",");
		sb.append("\"USER\":").append("\"").append(token.getUserId()).append("\",");
		sb.append("\"TARGET\":").append("\"").append("").append("\",");
		sb.append("\"DETAIL\":").append("\"").append("\n----PARAMETERS----\n");
		if(paramMap!=null && !paramMap.isEmpty()) {
			for (String key : paramMap.keySet()) {
				sb.append(key).append("=").append("\"").append(paramMap.get(key)).append("\"\n");
			}
		}
		sb.append("------------\"}");
		
		busiLogStr = sb.toString();
		busiLogger.debug(busiLogStr);
	}*/
	
	/***
	 * 构建代理服务
	 * @param oriService
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxyService(T oriService) {
		ProxyService serviceProxy = new ProxyService(oriService);
		
		return  (T) Proxy.newProxyInstance(oriService.getClass().getClassLoader(),oriService.getClass().getInterfaces(),serviceProxy);
		
	}

}
