/**
 * 
 */
package com.bht.banhuitong.filter.security.impl;

import static javax.imageio.ImageIO.write;
import static org.xx.armory.commons.Validators.greaterThan;
import static org.xx.armory.commons.Validators.notBlank;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xx.armory.security.CaptchaImageProvider;
import org.xx.armory.security.impl.SimpleGimpy;
import org.xx.armory.services.ServiceContext;
import org.xx.armory.web.TempFileObject;

/**
 * 验证码提供程序的一个简单实现。
 * 
 * @author Haart
 *
 */
public final class SimpleExpressionCaptchaImageProvider implements CaptchaImageProvider {
	private Log log = LogFactory.getLog(SimpleExpressionCaptchaImageProvider.class);

	public static final int DEFAULT_IMAGE_WIDTH = 108;

	public static final int DEFAULT_IMAGE_HEIGHT = 32;

	/**
	 * 
	 * @author Haart
	 *
	 */
	private static class ConfigHolder {
		/**
		 * 缺省配置实例
		 */
		static final Properties INSTANCE = createDefaultConfig();
	}

	/**
	 * 
	 */
	public SimpleExpressionCaptchaImageProvider() {
	}

	/**
	 * 创建一个缺省的配置
	 * 
	 * @return
	 */
	private static Properties createDefaultConfig() {
		final Properties props = new Properties();
		props.setProperty("kaptcha.border", "no");
		props.setProperty("kaptcha.textproducer.font.names", "Arial");
		props.setProperty("kaptcha.textproducer.font.size", "24");
		props.setProperty("kaptcha.textproducer.char.space", "1");
		// props.setProperty("kaptcha.obscurificator.impl",
		// "com.google.code.kaptcha.impl.ShadowGimpy");
		props.setProperty("kaptcha.obscurificator.impl", SimpleGimpy.class.getName());
		props.setProperty("kaptcha.noise.impl", "org.xx.armory.security.impl.SimpleNoise");
		return props;
	}

	/* (non-Javadoc)
	 * @see org.xx.armory.security.CaptchaImageProvider#execute(org.xx.armory.services.ServiceContext)
	 */
	@Override
	public final TempFileObject execute(final ServiceContext ctx) {
		// Generate random code
		final ImmutablePair<String, Integer> ret = randomCode();

		ctx.getToken().put(CAPTCHA_KEY, ret.right.toString());

		// Draw image
		try {
			return new TempFileObject(
					"captcha-image.png",
					drawImage(ret.left, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT));
		} catch (IOException e) {
			log.error("Cannot draw captcha image", e);
			return TempFileObject.NONE;
		}
	}

	/**
	 * 生成随机验证码。
	 * 
	 * @return 生成的随机验证码内容和验证码的结果。
	 */
	public static ImmutablePair<String, Integer> randomCode() {
		final Random rnd = new Random();

		final int a = (int) (rnd.nextDouble() * 90) + 11;
		final int b = (int) (rnd.nextDouble() * 90) + 11;
		final boolean f = rnd.nextInt(100) <= 50;

		final int c;
		final String exp;
		if (f) {
			exp = String.format("%d + %d =", a, b);
			c = a + b;
		} else {
			if (a > b) {
				exp = String.format("%d - %d =", a, b);
				c = a - b;
			} else {
				exp = String.format("%d - %d =", b, a);
				c = b - a;
			}
		}

		return ImmutablePair.of(exp, c);
	}

	/**
	 * 生成验证码图片。
	 * 
	 * @param captchaCode
	 *            随机验证码。
	 * @param width
	 *            图片宽度(单位是像素)。
	 * @param height
	 *            图片高度(单位是像素)。
	 * @return 表示图片内容的字节数组。
	 * @throws IOException
	 *             如果在内存中绘制图像时出现错误，或者将图像内容转化为字节数组时出现错误。
	 * @throws NullPointerException
	 *             如果参数{@code captchaCode}是{@code null}。
	 * @throws IllegalArgumentException
	 *             如果参数{@code captchCode}只包含空白字符, 或者参数{@code width}小于等于{@code 10}, 或者参数{@code height}
	 *             小于等于{@code 10}。
	 */
	public static byte[] drawImage(final String captchaCode, final int width, final int height)
			throws IOException {
		notBlank(captchaCode, "captchaCode");
		greaterThan(width, "width", 10);
		greaterThan(height, "height", 10);

		final com.google.code.kaptcha.Producer producer = new com.google.code.kaptcha.impl.DefaultKaptcha();
		final Properties props = new Properties(ConfigHolder.INSTANCE);
		props.setProperty("kaptcha.image.width", String.valueOf(width));
		props.setProperty("kaptcha.image.height", String.valueOf(height));
		((com.google.code.kaptcha.util.Configurable) producer).setConfig(
				new com.google.code.kaptcha.util.Config(props));

		try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			write(producer.createImage(captchaCode), "PNG", output);
			return output.toByteArray();
		}
	}
}
