package com.bht.banhuitong.http;

import static com.bht.banhuitong.http.Urls.CAPTCHA_IMAGE_URL;
import static com.bht.banhuitong.http.Urls.LOGIN_URL;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.bht.banhuitong.config.Configuration;
import com.bht.banhuitong.security.impl.KeyStorage;
import com.bht.banhuitong.security.impl.SignHelper;

public class HttpClient {

	private static final String CODE_IMAGE_PATH = "image/captcha-image.png";
	private static String __AUTH__ = null;
	private static String __COOKIE_VALUE__ = null;
	private static String __TEMP__COOKIE_VALUE__ = null;
	private static final String SIGNATURE_KEY = "signature";

	private Logger logger = Logger.getLogger(HttpClient.class);

	private static HttpClient httpClient = null;

	public static HttpClient getInstance() {
		if (httpClient == null) {
			httpClient = new HttpClient();
		}
		return httpClient;
	}

	public InputStream sendRequest(final String submitType, final String url, final Map<String, String> paramMap) {

		if (!submitType.toUpperCase().equals("GET")) {
			// ensureParamsSignature(paramMap);
		}

		// Create HTTP.
		final org.apache.http.client.HttpClient http = HttpClientBuilder.create().setUserAgent("")
				.setRetryHandler(DefaultHttpRequestRetryHandler.INSTANCE)
				.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE).build();

		// Create get.
		final HttpUriRequest get = CAPTCHA_IMAGE_URL.equals(url)
				? initRequestBuilder(submitType).setUri(url).addHeader("Accept-Charset", "utf-8")
						.addHeader("Accept-Encoding", "gzip,deflate").addHeader("Accept", "image/png")
						.addHeader("Cookie", __COOKIE_VALUE__ == null ? __TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__)
						.addParameters(initParams(paramMap)).build()
				:

				initRequestBuilder(submitType).setUri(url).addHeader("Accept-Charset", "utf-8")
						.addHeader("Accept-Encoding", "gzip,deflate").addHeader("Accept", "application/json")
						.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
						.addHeader("Cookie", __COOKIE_VALUE__ == null ? __TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__)
						.addParameters(initParams(paramMap)).build();

		logger.info("uri:" + get.getURI());
		logger.info(__COOKIE_VALUE__ == null ? __TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__);

		// Execute
		HttpResponse resp;
		try {
			resp = http.execute(get);
		} catch (IOException e) {
			logger.error("Cannot get http request", e);
			return null;
		}

		final StatusLine sl = resp.getStatusLine();
		if (sl == null) {
			logger.info("Cannot get HTTP status line.");
			return null;
		}

		if (sl.getStatusCode() != HttpStatus.SC_OK) {
			logger.info("HTTP Error: (" + sl.getStatusCode() + ")" + sl.getReasonPhrase());
			return null;
		}
		InputStream is = null;

		try {
			is = resp.getEntity().getContent();

			if (CAPTCHA_IMAGE_URL.equals(url)) {

				setTempCookie(resp.getAllHeaders());

			} else if (LOGIN_URL.equals(url)) {
				// final String[] lines = IOUtils.readLines(is, "utf-8").toArray(new String[0]);

				// if(StringUtils.join(lines).equals("true")) {
				setCookieValue(resp.getAllHeaders());
				// }

			}
			return is;
		} catch (IOException e) {
			logger.error("Cannot read content", e);
			return null;
		}
	}

	private BasicNameValuePair[] initParams(Map<String, String> paramMap) {
		List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
		for (String key : paramMap.keySet()) {
			paramList.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		BasicNameValuePair[] parms = paramList.toArray(new BasicNameValuePair[paramList.size()]);
		return parms;
	}

	private void setCookieValue(Header[] headers) {
		if (__AUTH__ == null || __AUTH__.isEmpty() || __COOKIE_VALUE__ == null || __COOKIE_VALUE__.isEmpty()) {
			for (Header header : headers) {
				if (header.getName().equals("Set-Cookie")) {
					__COOKIE_VALUE__ = header.getValue();

				}
				for (HeaderElement he : header.getElements()) {
					if (he.getName().equals("__auth__")) {
						__AUTH__ = he.getValue();
						break;
					}
				}
			}
		}
	}

	private void setTempCookie(Header[] headers) {
		if (__COOKIE_VALUE__ == null || __TEMP__COOKIE_VALUE__ == null) {
			for (Header header : headers) {
				if (header.getName().equals("Set-Cookie")) {
					__TEMP__COOKIE_VALUE__ = header.getValue();
				}
			}
		}
	}

	private RequestBuilder initRequestBuilder(String submitType) {
		switch (submitType.toUpperCase()) {
		case "GET":
			return RequestBuilder.get();
		case "POST":
			return RequestBuilder.post();
		case "PUT":
			return RequestBuilder.put();
		case "DELETE":
			return RequestBuilder.delete();
		}
		return null;
	}

	public String sendRequest(final String submitType, final String url, final BasicNameValuePair... parms) {

		// Create HTTP.
		final org.apache.http.client.HttpClient http = HttpClientBuilder.create().setUserAgent("")
				.setRetryHandler(DefaultHttpRequestRetryHandler.INSTANCE)
				.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE).build();

		// Create get.
		final HttpUriRequest get = initRequestBuilder(submitType).setUri(url).addHeader("Accept-Charset", "utf-8")
				.addHeader("Accept-Encoding", "gzip,deflate").addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("Cookie", __COOKIE_VALUE__ == null ? __TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__)
				.addParameters(parms).build();
		logger.info("uri:" + get.getURI());
		logger.info(__COOKIE_VALUE__ == null ? __TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__);

		// Execute
		HttpResponse resp;
		try {
			resp = http.execute(get);
		} catch (IOException e) {
			logger.error("Cannot get http request", e);
			return "@@@ERROR@@@* Cannot get http request";
		}

		final StatusLine sl = resp.getStatusLine();
		if (sl == null) {
			logger.info("Cannot get HTTP status line.");
			return "@@@ERROR@@@* Cannot get HTTP status line.";
		}

		if (sl.getStatusCode() != 200) {
			logger.info("HTTP Error: (" + sl.getStatusCode() + ")" + sl.getReasonPhrase());
			return "@@@ERROR@@@* " + "HTTP Error: (" + sl.getStatusCode() + ")" + sl.getReasonPhrase();
		}

		try {
			final String[] lines = IOUtils.readLines(resp.getEntity().getContent(), "utf-8").toArray(new String[0]);
			logger.info("data:" + StringUtils.join(lines));

			String returnStr = StringUtils.join(lines);

			if (LOGIN_URL.equals(url) && returnStr.equals("true")) {

				setCookieValue(resp.getAllHeaders());

			}
			return returnStr;
		} catch (IOException e) {
			logger.error("Cannot read content", e);
			return "@@@ERROR@@@* Cannot read content";
		}
	}

	@SuppressWarnings("unused")
	private static void testCreateFile(byte[] bytes) {
		File file = new File(CODE_IMAGE_PATH);
		file.deleteOnExit();
		if (bytes != null && bytes.length != 0) {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes); // 将b作为输入流；
			try {
				BufferedImage image = ImageIO.read(in);
				ImageIO.write(image, "png", new File(CODE_IMAGE_PATH));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	protected void ensureParamsSignature(Map<String, String> params) {
		if (params != null) {
			StringBuffer sb = new StringBuffer();
			for (String key : params.keySet()) {
				if (!key.equals(SIGNATURE_KEY)) {
					sb.append(params.get(key));
				}
			}
			SignHelper signHelper = SignHelper.getInstance();
			try {
				params.put(SIGNATURE_KEY, signHelper.signBase64(sb.toString(),
						KeyStorage.getInstance().loadRSAPrivateKey(Configuration.VERIFY_PARAMS_PRIVATE)));
			} catch (GeneralSecurityException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	public static void main(String[] args) throws Exception {

		BasicNameValuePair[] parms = { new BasicNameValuePair("user-name", "admin"),
				new BasicNameValuePair("password", "12345678") };

		String baseUrl = "http://localhost:8889/";

		String loginUrl = baseUrl + "p2psrv/security/signin";

		String loginRet = HttpClient.getInstance().sendRequest("POST", loginUrl, parms);

		System.out.println(loginRet);

		BasicNameValuePair[] parms1 = { new BasicNameValuePair("user-name", "admin"),
				new BasicNameValuePair("password", "12345678"), new BasicNameValuePair("captcha-code", "") };
		String ret1 = HttpClient.getInstance().sendRequest("POST", loginUrl, parms1);

		System.out.println(ret1);
	}

}
