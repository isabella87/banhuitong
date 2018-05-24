/**
 * 
 */
package com.bht.banhuitong.security.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xx.armory.commons.Converter;
import org.xx.armory.commons.CryptographicUtils;
import org.xx.armory.security.AuthenticationCrypto;

/**
 * @author Haart
 *
 */
public final class DefaultAuthenticationCrypto implements AuthenticationCrypto {
	private Log logger = LogFactory.getLog(DefaultAuthenticationCrypto.class);

	private static final Charset CHARSET = Charset.forName("UTF8");

	/**
	 * 
	 */
	public DefaultAuthenticationCrypto() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xx.armory.web.filters.AuthenticationCrypto#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(final String clearText) {
		if (StringUtils.isEmpty(clearText)) {
			return "";
		}

		ByteArrayInputStream bis = new ByteArrayInputStream(clearText.getBytes(CHARSET));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			CryptographicUtils.encrypt(bis, bos);
			return Converter.formatHexString(bos.toByteArray());
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("cannot encrypt text \"" + clearText + "\"", e);
			}
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.xx.armory.web.filters.AuthenticationCrypto#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(final String cipherText) {
		if (StringUtils.isEmpty(cipherText)) {
			return "";
		}

		ByteArrayInputStream bis = new ByteArrayInputStream(Converter.toBytes(cipherText));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			CryptographicUtils.decrypt(bis, bos);
			return new String(bos.toByteArray(), CHARSET);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("cannot decrypt text \"" + cipherText + "\"", e);
			}
			return "";
		}
	}
}
