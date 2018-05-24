/**
 * 
 */
package com.bht.banhuitong.security;

import java.util.TreeMap;

/**
 * 来宾令牌, 来宾令牌的身份ID应当是空字符串({@code ""})。
 * 
 * @author 
 *
 */
public final class GuestAuthenticationToken extends AuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造来宾令牌。
	 */
	public GuestAuthenticationToken() {
		super("", false, new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER));
	}

}
