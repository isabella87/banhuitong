package com.bht.banhuitong.filter.security;

import org.xx.armory.services.ServiceContext;
import org.xx.armory.web.TempFileObject;

/**
 * 验证码图片的提供程序。
 * 
 * <p>
 * 提供程序需要生成验证码图片, 并且把对应的验证码保存到令牌中。
 * </p>
 * 
 * @author Haart
 *
 */
public interface CaptchaImageProvider {
	/**
	 * 在令牌中保存验证码的键名。
	 */
	public static final String CAPTCHA_KEY = "__captcha__";

	/**
	 * 执行提供程序。
	 * 
	 * @param ctx
	 *            服务上下文。
	 * @return 包含了验证码图片内容的临时文件对象。
	 */
	public abstract TempFileObject execute(final ServiceContext ctx);

}