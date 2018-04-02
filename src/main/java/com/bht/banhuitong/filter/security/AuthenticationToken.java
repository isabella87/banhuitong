/**
 * 
 */
package com.bht.banhuitong.filter.security;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;
import static org.xx.armory.commons.Converter.toValueEnum;
import static org.xx.armory.commons.Validators.notBlank;
import static org.xx.armory.commons.Validators.notNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.xx.armory.commons.ValueEnum;

/**
 * 身份认证令牌。
 * 
 * <p>
 * 令牌可以以多种方式保存, 比如在{@literal cookie}, {@literal session}等等。
 * </p>
 * 
 * @author Haart
 *
 */
public class AuthenticationToken implements Map<String, String>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String userId;

	private Map<String, String> accessories;

	private final boolean guest;

	private final boolean persist;

	/**
	 * 表示不代表任何身份的令牌。
	 * 
	 * @return 不代表任何身份的令牌。
	 */
	public static AuthenticationToken guest() {
		return new GuestAuthenticationToken();
	}

	/**
	 * 构造身份认证令牌。
	 * 
	 * @param userId
	 *            帐户ID, 表示唯一确认身份的ID。
	 */
	public AuthenticationToken(final String userId) {
		this(userId, false);
	}

	/**
	 * 构造身份认证令牌。
	 * 
	 * @param userId
	 *            帐户ID, 表示唯一确认身份的ID。
	 * @param persist
	 *            该令牌是否应当持久保存。
	 */
	public AuthenticationToken(final String userId, final boolean persist) {
		this(userId, persist, new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER));
	}

	/**
	 * 构造身份认证令牌。
	 * 
	 * @param userId
	 *            帐户ID, 表示唯一确认身份的ID。
	 * @param persist
	 *            该令牌是否应当持久保存。
	 * @param accessories
	 *            令牌包含的附加信息。
	 * @throws NullPointerException
	 *             如果参数{@code accessories}是{@code null}。
	 */
	protected AuthenticationToken(final String userId, final boolean persist,
			final Map<String, String> accessories) {
		notNull(accessories, "accessories");

		this.userId = trimToEmpty(userId);
		this.persist = persist;
		this.guest = isBlank(userId);
		this.accessories = accessories;
	}

	/**
	 * 获取用户ID。
	 * 
	 * @return 用户ID。
	 */
	public final String getUserId() {
		return this.userId;
	}

	/**
	 * 判断是否持久有效令牌。
	 * 
	 * @return 如果是持久有效令牌则返回{@code true}, 否则返回{@code false}。
	 */
	public final boolean isPersist() {
		return this.persist;
	}

	/**
	 * 判断是否来宾令牌。
	 * 
	 * @return 如果是来宾令牌则返回{@code true}, 否则返回{@code false}。
	 */
	public final boolean isGuest() {
		return this.guest;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public final int size() {
		return this.accessories.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public final boolean isEmpty() {
		return this.accessories.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public final boolean containsKey(final Object key) {
		notNull(key, "key");

		return this.accessories.containsKey(regularKey(key.toString()));
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public final boolean containsValue(final Object value) {
		return this.accessories.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public final String get(final Object key) {
		notNull(key, "key");

		final String s = this.accessories.get(regularKey(key.toString()));
		return s == null ? "" : s.trim();
	}

	/**
	 * 根据键从令牌中获取值。
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 *         令牌中指定键对应的值，如果不存在则返回{@code defaultValue}。
	 */
	public final String get(final String key, final String defaultValue) {
		final String v = this.get(key);
		return !v.isEmpty() ? v : defaultValue;
	}

	/**
	 * 根据键从令牌中获取整数值。
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 *         令牌中指定键对应的整数值，如果不存在则返回{@code defaultValue}。
	 * 
	 * @see #get(String, String)
	 */
	public final int getInt(final String key, final int defaultValue) {
		return toInt(this.get(key), defaultValue);
	}

	/**
	 * 根据键从令牌中获取长整数值。
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 *         令牌中指定键对应的整数值，如果不存在则返回{@code defaultValue}。
	 * 
	 * @see #get(String, String)
	 */
	public final long getLong(final String key, final long defaultValue) {
		return toLong(this.get(key), defaultValue);
	}

	/**
	 * 根据键从令牌中获取{@code org.json.ValueEnum}对象。
	 * 
	 * @param <T>
	 *            {@code org.json.ValueEnum}对象的类型。
	 * @param clazz
	 *            {@code org.json.ValueEnum}对象的类型。
	 * @param key
	 *            键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 *         令牌中指定键对应的{@code org.json.ValueEnum}对象，如果不存在则返回{@code defaultValue}。
	 * 
	 * @see #get(Object)
	 * @see org.xx.armory.commons.Converter#toValueEnum(Class, int)
	 */
	public final <T extends Enum<T> & ValueEnum> T getValueEnum(final Class<T> clazz,
			final String key, final T defaultValue) {
		final String str = this.get(key);
		if (str.isEmpty()) {
			return defaultValue;
		}

		try {
			final T ret = toValueEnum(clazz, Integer.parseInt(str));
			return ret != null ? ret : defaultValue;
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 根据键从令牌中获取{@code java.util.Date}值。
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 *         令牌中指定键对应的{@code java.util.Date}值，如果不存在则返回{@code defaultValue}。
	 * 
	 * @see #getLong(String, long)
	 * @see Date#Date(long)
	 */
	public final Date getDate(final String key, final Date defaultValue) {
		final String str = this.get(key);
		if (str.isEmpty()) {
			return defaultValue;
		}

		final long ts = this.getLong(key, 0L);
		return ts == 0L ? defaultValue : new Date(ts);
	}

	/**
	 * 向令牌中加入一个整数值。
	 * 
	 * @param key
	 *            键名。
	 * @param v
	 *            键值。
	 * @return 字符串形式的键值。
	 */
	public final String putInt(final String key, final int v) {
		return this.put(key, String.valueOf(v));
	}

	/**
	 * 向令牌中加入一个长整数值。
	 * 
	 * @param key
	 *            键名。
	 * @param v
	 *            键值。
	 * @return 字符串形式的键值。
	 */
	public final String putLong(final String key, final long v) {
		return this.put(key, String.valueOf(v));
	}

	/**
	 * 向令牌中加入一个{@code org.json.ValueEnum}对象。
	 * 
	 * @param <T>
	 *            {@code org.json.ValueEnum}对象的类型。
	 * @param key
	 *            键名。
	 * @param v
	 *            键值。
	 * @return 字符串形式的键值。
	 */
	public final <T extends Enum<T> & ValueEnum> String putValueEnum(final String key, final T v) {
		return this.putInt(key, v.value());
	}

	/**
	 * 向令牌中加入一个日期值。
	 * 
	 * <p>
	 * 日期值以时间戳的形式添加。
	 * </p>
	 * 
	 * @param key
	 *            键名。
	 * @param d
	 *            键值。
	 * @return 字符串形式的键值。
	 * @see java.util.Date#getTime()
	 * @see #putLong(String, long)
	 */
	public final String putDate(final String key, final Date d) {
		return this.putLong(key, d.getTime());
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public final String put(final String key, final String value) {
		if (value != null) {
			return this.accessories.put(regularKey(key), value);
		} else {
			return this.accessories.remove(regularKey(key));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public final String remove(final Object key) {
		notNull(key, "key");

		return this.accessories.remove(regularKey(key.toString()));
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public final void putAll(Map<? extends String, ? extends String> m) {
		this.accessories.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public final void clear() {
		this.accessories.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public final Set<String> keySet() {
		return this.accessories.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public final Collection<String> values() {
		return this.accessories.values();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public final Set<java.util.Map.Entry<String, String>> entrySet() {
		return this.accessories.entrySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return this.userId == null ? 0 : this.userId.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AuthenticationToken)) {
			return false;
		}
		AuthenticationToken other = (AuthenticationToken) obj;
		if (this.userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!this.userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthenticationToken [userId=" + this.userId + ", accessories["
				+ this.accessories.size() + "]]";
	}

	/**
	 * 将键名正规化。
	 * 
	 * @param key
	 *            键名。
	 * @return 正规化并且转为小写的键名。
	 * @throws NullPointerException
	 *             如果参数{@code key}是{@code null}。
	 * @throws IllegalArgumentException
	 *             如果参数{@code key}只包含空白字符。
	 */
	private static String regularKey(final String key) {
		return notBlank(key, "key").trim().toLowerCase();
	}
}
