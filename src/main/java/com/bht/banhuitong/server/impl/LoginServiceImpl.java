package com.bht.banhuitong.server.impl;

import static com.bht.banhuitong.filter.SecurityFilter.captchaSessionMap;
import static com.bht.banhuitong.filter.SecurityFilter.userSessionMap;
import static com.bht.banhuitong.filter.security.impl.SimpleExpressionCaptchaImageProvider.DEFAULT_IMAGE_HEIGHT;
import static com.bht.banhuitong.filter.security.impl.SimpleExpressionCaptchaImageProvider.DEFAULT_IMAGE_WIDTH;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.xx.armory.services.ServiceException;

import com.bht.banhuitong.common.CommonMethod;
import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.dbservice.impl.AccountDbServiceImpl;
import com.bht.banhuitong.filter.security.AuthenticationToken;
import com.bht.banhuitong.filter.security.impl.SimpleExpressionCaptchaImageProvider;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.serviceConf.RpcServiceImpl;
import com.bht.banhuitong.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * The server-side implementation of the RPC service.
 * 用户登录相关服务，此类中的服务无需经过权限过滤判断
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private final static Logger logger = Logger.getLogger(LoginServiceImpl.class);
//	private String fileSeparator = java.io.File.separator;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String loginImmediately(Map<String, String> paramMap) throws IllegalArgumentException{

		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(paramMap.get("user-name"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(CommonMethod.initExceptionDesc(6));
		}
		
		if (!FieldVerifier.isValidName(paramMap.get("password"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(CommonMethod.initExceptionDesc(5));
		}
		
		if (!FieldVerifier.isValidCaptchaCode(paramMap.get("captcha-code"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
				throw new IllegalArgumentException(CommonMethod.initExceptionDesc(4));
		}
		
		// 自有版本
		String loginName = paramMap.get("user-name");
		String pwd = paramMap.get("password");
		String captchaCode = paramMap.get("captcha-code");
		
		if(captchaCode==null||captchaCode.isEmpty()||!captchaCode.equals(captchaSessionMap.get(getThreadLocalRequest().getSession().getId()))) {
			throw new ServiceException(CommonMethod.initExceptionDesc(4));
		};
				
		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		long auId = accountDbService.checkLogin(loginName, pwd);
		if (auId>0&&addUserSessionId(String.valueOf(auId))) {
			return "true";
		}else {
			throw new IllegalArgumentException(CommonMethod.initExceptionDesc(6));
		}
		
	}
	
	/**
	 * 保存token
	 * @param servletContext
	 * @param userId
	 * @return
	 */
	private boolean addUserSessionId(String userId){
		final AuthenticationToken newToken = new AuthenticationToken(userId);
		
		for(String key:userSessionMap.keySet()) {
			if(userSessionMap.get(key)!=null && userSessionMap.get(key).getUserId().equals(userId)) {
				userSessionMap.remove(key);
				//TODO 当由于用户在别处登录而导致之前的登录信息被移除而失效时，(目前只实现到服务端移除对应信息，客户端再次调用服务会弹出登录对话框)后期可以主动推动信息给用户提示用户，“您的账户的别处登录，被迫下线”
				break;
			}
		}
		
		//key 
		userSessionMap.put(getThreadLocalRequest().getSession().getId(), newToken);
		
		return true;
	}

	/**
	 * 跨越版登录
	 */
	@Override
	public String login(Map<String, String> paramMap) throws IllegalArgumentException {

		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(paramMap.get("user-name"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new ServiceException(CommonMethod.initExceptionDesc(6));
		}
		
		if (!FieldVerifier.isValidName(paramMap.get("password"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("密码必须包含数字、字母和特殊字符且不能含有空格，其中特殊字符包括!@#$%^&*-_+=?<>,./~`()");
		}
		
		if (!FieldVerifier.isValidName(paramMap.get("captcha-code"))) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new ServiceException(CommonMethod.initExceptionDesc(4));
		}

		String loginName = paramMap.get("user-name");
		String pwd = paramMap.get("password");
		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		String uName = accountDbService.checkBgLogin(loginName, pwd);
		if (!uName.isEmpty()) {
			addUserSessionId(uName);
		}

		return new RpcServiceImpl().login(paramMap);
	}
	
	/**
	 * 获取验证码
	 */
	public String getImageByte() {
		byte[] result = null;
		final ImmutablePair<String, Integer> ret = SimpleExpressionCaptchaImageProvider.randomCode();
		captchaSessionMap.put(getThreadLocalRequest().getSession().getId(), ret.right.toString());
		try {
			result = SimpleExpressionCaptchaImageProvider.drawImage(ret.left, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*Map<String, String> paramMap = new HashMap<String, String>();
		byte[] result = new RpcServiceImpl().getCaptchaImage(paramMap);*/
		if (result == null) {
			return null;
		}
		
		/*
		FileOutputStream fos = null;
		String fileName = getServletContext().getRealPath("/") +fileSeparator+ "images"+fileSeparator+"captcha-image.png";
		try {

			fos = new FileOutputStream(fileName);
			fos.write(result);
			fos.close();*/
			
			String strBase64Img = "data:;base64," + Base64.encodeBase64String(result);
			logger.info("strBase64Img::::" + strBase64Img);
			/*File file = new File("captcha-image.png");
			logger.info("getAbsolutePath::::" + file.getAbsolutePath());*/
			
			return strBase64Img;
		/*} catch (Exception e) {
			e.printStackTrace();
			return null;
		}*/
	}

	@Override
	public String getExcepStr() {
		return Configuration.excepStr;
	}

}
