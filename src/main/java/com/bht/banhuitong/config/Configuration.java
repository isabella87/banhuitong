/**
 * 
 */
package com.bht.banhuitong.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 包含所有配置信息的类
 *
 */
public final class Configuration {
	
	public static final Map<String, String> PROPERTIES = new HashMap<String, String>();

	public static String excepStr;
	public static String rootPath;

	/**
	 * 
	 */
	private Configuration() {
		assert false;
	}

	/**
	 * 设置配置信息, 应当在系统启动时执行, 并且只执行一次
	 * 
	 * @param properties
	 */
	static void setProperties(final Map<String, String> properties) {
		synchronized (PROPERTIES) {
			PROPERTIES.clear();
			for (final Map.Entry<String, String> entry : properties.entrySet()) {
				PROPERTIES.put(entry.getKey().trim(), entry.getValue().trim());
			}
		}
	}

	/**
	 * 获取字符串类型的配置信息
	 * 
	 * @param key
	 *            配置字段名(区分大小写)
	 * @return
	 *         配置信息的值, 不存在则返回空字符串
	 */
	public static String getString(final String key) {
		final String s = PROPERTIES.get(key);

		return s == null ? "" : s.trim();
	}

	/**
	 * 获取整数类型的配置信息
	 * 
	 * @param key
	 *            配置字段名(区分大小写)
	 * @return
	 *         配置信息的值, 不存在则返回{@code 0}。
	 */
	public static Integer getInteger(final String key) {
		final String s = PROPERTIES.get(key);

		return s == null ? 0 : Integer.parseInt(s);
	}

	/**
	 * 获取字节数组类型的配置信息
	 * 
	 * @param key
	 *            配置字段名(区分大小写)
	 * @return
	 *         配置信息的值, 不存在则返回长度为0的字节数组
	 */
	/*public static byte[] getBytes(final String key) {
		final String s = PROPERTIES.get(key);

		return s == null ? new byte[0] : Base64.getDecoder().decode(s);
	}*/
}
