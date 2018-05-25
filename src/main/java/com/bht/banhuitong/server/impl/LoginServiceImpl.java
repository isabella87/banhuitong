package com.bht.banhuitong.server.impl;

import static com.bht.banhuitong.filter.SecurityFilter.captchaSessionMap;
import static com.bht.banhuitong.filter.SecurityFilter.userSessionMap;
import static com.bht.banhuitong.security.impl.SimpleExpressionCaptchaImageProvider.DEFAULT_IMAGE_HEIGHT;
import static com.bht.banhuitong.security.impl.SimpleExpressionCaptchaImageProvider.DEFAULT_IMAGE_WIDTH;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;

import com.bht.banhuitong.common.CommonMethod;
import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.db.service.impl.AccountDbServiceImpl;
import com.bht.banhuitong.http.service.impl.RpcServiceImpl;
import com.bht.banhuitong.security.AuthenticationToken;
import com.bht.banhuitong.security.impl.SimpleExpressionCaptchaImageProvider;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service. 用户登录相关服务，此类中的服务无需经过权限过滤判断
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private final static Logger logger = Logger.getLogger(LoginServiceImpl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String loginImmediately(Map<String, String> paramMap) throws IllegalArgumentException {

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

		if (captchaCode == null || captchaCode.isEmpty()
				|| !captchaCode.equals(captchaSessionMap.get(getThreadLocalRequest().getSession().getId()))) {
			throw new IllegalArgumentException(CommonMethod.initExceptionDesc(4));
		}

		AccountDbServiceImpl accountDbService = new AccountDbServiceImpl();
		String userId = accountDbService.checkBgLogin(loginName, pwd);
		if (userId!=null &&! userId.isEmpty() && addUserSessionId(userId)) {
			return "true";
		} else {
			throw new IllegalArgumentException(CommonMethod.initExceptionDesc(6));
		}

	}

	/**
	 * 保存token
	 * 
	 * @param servletContext
	 * @param userId
	 * @return
	 */
	private boolean addUserSessionId(String userId) {
		final AuthenticationToken newToken = new AuthenticationToken(userId);

		for (String key : userSessionMap.keySet()) {
			if (userSessionMap.get(key) != null && userSessionMap.get(key).getUserId().equals(userId)) {
				userSessionMap.remove(key);
				// TODO
				// 当由于用户在别处登录而导致之前的登录信息被移除而失效时，(目前只实现到服务端移除对应信息，客户端再次调用服务会弹出登录对话框)后期可以主动推动信息给用户提示用户，“您的账户的别处登录，被迫下线”
				break;
			}
		}

		// key
		userSessionMap.put(getThreadLocalRequest().getSession().getId(), newToken);

		return true;
	}

	/**
	 * 跨越版登录
	 */
	@Override
	public String loginToP2psrv(Map<String, String> paramMap) throws IllegalArgumentException {

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
		String ret = new RpcServiceImpl().login(paramMap);
		if(ret.equals("true")) {
			addUserSessionId(paramMap.get("user-name"));
		}
		return ret;
	}

	/**
	 * 获取验证码
	 */
	public String getImageByte() {

		byte[] result = null;

		final ImmutablePair<String, Integer> ret = SimpleExpressionCaptchaImageProvider.randomCode();
		captchaSessionMap.put(getThreadLocalRequest().getSession().getId(), ret.right.toString());
		try {
			result = SimpleExpressionCaptchaImageProvider.drawImage(ret.left, DEFAULT_IMAGE_WIDTH,
					DEFAULT_IMAGE_HEIGHT);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}

		if (result == null) {
			return null;
		}

		return "data:;base64," + Base64.encodeBase64String(result);
		
	}
	
	public String getP2psrvImageByte() {

		byte[] result = null;

		result = new RpcServiceImpl().getCaptchaImage(new HashMap<String, String>());

		if (result == null) {
			return null;
		}

		return "data:;base64," + Base64.encodeBase64String(result);
	}

	@Override
	public String getExcepStr() {
		return Configuration.excepStr;
	}

	@Override
	public boolean loginOut() throws IllegalArgumentException {
		String key = getThreadLocalRequest().getSession().getId();
		if (userSessionMap.containsKey(key)) {
			userSessionMap.remove(key);
		}
		return true;
	}

}
