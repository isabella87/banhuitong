/**
 * 
 */
package com.bht.banhuitong.filter.security;

/**
 * 定义了令牌加密和解密方法的操作。
 * 
 * @author Haart
 *
 */
public interface AuthenticationCrypto {
	/**
	 * 将明文加密。
	 * 
	 * @param clearText
	 *            待加密的明文。
	 * @return 加密后的密文, 如果明文是{@code null}或者空白字符串则返回空白字符串。
	 */
	String encrypt(final String clearText);

	/**
	 * 将密文解密。
	 * 
	 * @param cipherText
	 *            待解密的密文。
	 * @return 解密后的明文, 如果密文是{@code null}或者空白字符串则返回空白字符串。
	 */
	String decrypt(final String cipherText);
}
