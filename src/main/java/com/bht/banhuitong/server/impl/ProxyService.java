package com.bht.banhuitong.server.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.common.DateUtils;

/**
 * 业务操作动态代理，用于在调用真实业务操作方法前和之后记录业务操作日志
 * （可以在此处对参数进行统一加密处理）
 * @author Isabella
 *
 */
public class ProxyService implements InvocationHandler {

	public final static Logger busiLogger = Logger.getLogger("busiLogger");
	
	private Object obj;
	
	public ProxyService(Object object){
		this.obj = object;
	}
	
	/**
	 * 调用业务操作方法之前记录操作日志
	 * @param args
	 * @param method
	 */
	public void beforeMethod(Object[] args, Method method) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"TIMESTAMP\":").append("\"").append(DateUtils.toStandardStr(new Date())).append("\",");
		sb.append("\"STAGE\":").append("\"").append("start").append("\",");
		sb.append("\"MODULE\":").append("\"").append(method.getDeclaringClass().getName()).append("\",");
		sb.append("\"ACTION\":").append("\"").append(method.getName()).append("\",");
		sb.append("\"USER\":").append("\"").append("admin").append("\",");
		sb.append("\"TARGET\":").append("\"").append("").append("\",");
		sb.append("\"DETAIL\":").append("\"").append("----PARAMETERS----");
		for(Object o:args) {
			if (o instanceof Map) {
				Map<String, String> p = (Map) o;

				int i = 0;
				for (String key : p.keySet()) {
					sb.append((i==0?"":",")+key).append("=").append("\"").append(p.get(key)).append("\"");
					i++;
				}
			}
		}
		
		sb.append("------------\"}");
		
		busiLogger.debug(sb.toString());
	}
	
	/**
	 * 调用业务操作方法之后记录操作日志
	 * @param args
	 * @param method
	 */
	public void afterMethod(Object[] args, Method method) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"TIMESTAMP\":").append("\"").append(DateUtils.toStandardStr(new Date())).append("\",");
		sb.append("\"STAGE\":").append("\"").append("end").append("\",");
		sb.append("\"MODULE\":").append("\"").append(method.getDeclaringClass().getName()).append("\",");
		sb.append("\"ACTION\":").append("\"").append(method.getName()).append("\",");
		sb.append("\"USER\":").append("\"").append("admin").append("\",");
		sb.append("\"TARGET\":").append("\"").append("").append("\",");
		sb.append("\"DETAIL\":").append("\"").append("----PARAMETERS----");
		for(Object o:args) {
			if (o instanceof Map) {
				Map<String, String> p = (Map) o;

				int i = 0;
				for (String key : p.keySet()) {
					sb.append((i==0?"":",")+key).append("=").append("\"").append(p.get(key)).append("\"");
					i++;
				}
			}
		}
		
		sb.append("------------\"}");
		
		busiLogger.debug(sb.toString());
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		beforeMethod(args,method);
		Object o = method.invoke(obj,args);
		afterMethod(args,method);
		return o;
	}

}
