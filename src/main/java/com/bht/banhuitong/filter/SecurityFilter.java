package com.bht.banhuitong.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.xx.armory.services.ServiceException;

import com.bht.banhuitong.BusinessAnnotation;
import com.bht.banhuitong.common.CommonMethod;
import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.dbservice.impl.PermissionDbServiceImpl;
import com.bht.banhuitong.filter.security.AuthenticationToken;

public class SecurityFilter implements Filter {

	private final static Logger logger = Logger.getLogger(SecurityFilter.class);
	private final static Logger busiLogger = Logger.getLogger("busiLogger");
	final AuthenticationToken newToken = AuthenticationToken.guest();
	public static Map<String,AuthenticationToken>  userSessionMap = new HashMap<String,AuthenticationToken>();
	public static Map<String,String>  captchaSessionMap = new HashMap<String,String>();
	public FilterConfig config;

	@Override
	public void destroy() {
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
		
		int moduleValue = getModuleValue(requestWrapper.getServletPath());
		
		// 设置基本上下文信息，并增加以下判断：增加超时判断
		if(moduleValue!=ModuleType.LOGIN) {
//			commonAssert(requestWrapper);
		}
					
		switch (moduleValue) {
		case ModuleType.LOGIN:
			break;
		case ModuleType.ACCOUNT:
		case ModuleType.PRJ:
		case ModuleType.DBMODEL:
			AuthenticationToken token = userSessionMap.get(requestWrapper.getSession().getId());
			// 1.判断是否登录；
			if (token==null||token.isGuest() || token.getUserId().isEmpty()) {
				throw new ServiceException(CommonMethod.initExceptionDesc(1));
			}
			
			BusinessAnnotation busiAnntation = getBusinessAnnotation(requestWrapper);
			
			// 2，增加权限过滤
			if (busiAnntation!=null && busiAnntation.perm()!=0 && !queryPermValueByUserIdAndperm(busiAnntation.perm(), token.getUserId())) {
				throw new ServiceException(CommonMethod.initExceptionDesc(2));
			}
			
		/* // 3，记录业务日志，已经在数据库访问上层应用代理机制处理
			if(busiAnntation!=null&&busiAnntation.recordLog()) {
				//TODO 
			}*/
			break;
		default:
		}


		chain.doFilter(requestWrapper, response);
	}

	/**
	 * 设置基本上下文信息，并增加以下判断：......
	 * 超时时间设置在web.xml中
	 * @param requestWrapper
	 */
	private void commonAssert(HttpServletRequest requestWrapper) {
		// TODO Auto-generated method stub
		HttpSession httpSession = requestWrapper.getSession();
		
		
		
	}

	/**
	 * 获取注解内容
	 * @param request
	 * @return
	 */
	private BusinessAnnotation getBusinessAnnotation(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String requestPayloadStr = sb.toString();
		requestPayloadStr = requestPayloadStr.substring(requestPayloadStr.indexOf("com.bht.banhuitong.server"));
		String[] clazzAndMethod = requestPayloadStr.split("\\|");
		String clazzName = clazzAndMethod[0];
		String methodName = clazzAndMethod[1];

		logger.debug("**********getRequestPayload:" + sb.toString());
		// 获取方法上的注解值
		try {
			Class<?> clazz = Class.forName(clazzName);
			Method[] methods = clazz.getDeclaredMethods();
			if (methods != null) {
				for (Method method : methods) {
					if (method.getName().equals(methodName)) {
						return method.getAnnotation(BusinessAnnotation.class);
					}
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据权限值和当前登录用户到数据库匹配响应数据，如果有权限返回true，否则返回false。 此方法针对类方法设置了权限值的服务。
	 * 
	 * @param perm
	 * @param userId
	 * @return
	 */
	private boolean queryPermValueByUserIdAndperm(int perm, String userId) {
		List<Map<String, String>> result = new PermissionDbServiceImpl().havePerm(perm, userId);
		if (result != null && !result.isEmpty()) {
			return true;
		}
		return false;
	}

	public Integer getModuleValue(String path) {
		if (!path.contains("/")) {
			return 0;
		}
		Integer moduleValue = Configuration.getInteger(path.substring(path.lastIndexOf("/") + 1));

		return moduleValue == null ? 0 : moduleValue;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public static boolean isContains(String container, String[] regx) {
		boolean result = false;

		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

}
