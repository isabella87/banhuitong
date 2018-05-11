package com.bht.banhuitong.http;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class MyHttpClient {

	public static final String CODE_IMAGE_PATH = "image/captcha-image.png";
	private static String __AUTH__ = null;
	public static String __COOKIE_VALUE__ = null;
	

	Logger logger = Logger.getLogger(MyHttpClient.class);
	private static MyHttpClient httpClient = null;

	public static MyHttpClient getInstance() {
		if (httpClient == null) {
			httpClient = new MyHttpClient();
		}
		return httpClient;
	}

	public String sendRequest(final boolean isLoginReq, final String submitType, final String url,
			final BasicNameValuePair... parms) throws URISyntaxException,
			UnsupportedEncodingException {
		
		// Create HTTP.
		final HttpClient http = HttpClientBuilder.create().setUserAgent("")
				.setRetryHandler(StandardHttpRequestRetryHandler.INSTANCE)
				.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE).build();

		// Create get.
		final HttpUriRequest get = initRequestBuilder(submitType)
				.setUri(url)
				.addHeader("Accept-Charset", "utf-8")
				.addHeader("Accept-Encoding", "gzip,deflate")
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("Cookie",
						__COOKIE_VALUE__ == null ? Constants.__TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__)
				.addParameters(parms)
				.build();
		logger.info("uri:" + get.getURI());
		logger.info("2PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		logger.info(__COOKIE_VALUE__ == null ? Constants.__TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__);
		logger.info("2PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");

		// Execute
		HttpResponse resp;
		try {
			resp = http.execute(get);
		} catch (IOException e) {
			logger.error("Cannot get http request", e);
			return "";
		}

		final StatusLine sl = resp.getStatusLine();
		if (sl == null) {
			logger.info("Cannot get HTTP status line.");
			return "";
		}

		if (sl.getStatusCode() != 200) {
			logger.info("HTTP Error: (" + sl.getStatusCode() + ")" + sl.getReasonPhrase());
			return "";
		}

		try {
			final String[] lines = IOUtils.readLines(resp.getEntity().getContent(), "utf-8")
					.toArray(new String[0]);
			logger.info("data:" + StringUtils.join(lines));
			System.out.println("data:" + StringUtils.join(lines));

			String returnStr = StringUtils.join(lines);
			if (isLoginReq && returnStr.equals("true")) {
				setCookieValue(resp.getAllHeaders());
			}
			return returnStr;
		} catch (IOException e) {
			logger.error("Cannot read content", e);
			return "";
		}
	}

	private void setCookieValue(Header[] headers) {
		if (__AUTH__ == null || __AUTH__.isEmpty() || __COOKIE_VALUE__ == null
				|| __COOKIE_VALUE__.isEmpty()) {
			for (Header header : headers) {
				System.out.println("header_name:" + header.getName() + ",header_value:"
						+ header.getValue());
				if (header.getName().equals("Set-Cookie")) {
					__COOKIE_VALUE__ = header.getValue();

				}
				for (HeaderElement he : header.getElements()) {
					System.out.println("HeaderElement_name:" + he.getName()
							+ ",HeaderElement_value:" + he.getValue());
					if (he.getName().equals("__auth__")) {
						__AUTH__ = he.getValue();
						break;
					}
				}
			}
		}

	}

	public byte[] sendByteRequest(final String submitType, final String url,
			final BasicNameValuePair... parms) throws URISyntaxException,
			UnsupportedEncodingException {

		// Create HTTP.
		final HttpClient http = HttpClientBuilder.create().setUserAgent("")
				.setRetryHandler(StandardHttpRequestRetryHandler.INSTANCE)
				.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE).build();

		// Create get.
		final HttpUriRequest get = initRequestBuilder(submitType)
				.setUri(url)
				.addHeader("Accept-Charset", "utf-8")
				.addHeader("Accept-Encoding", "gzip,deflate")
				.addHeader("Accept", "image/png")
				.addHeader("Cookie",
						__COOKIE_VALUE__ == null ? Constants.__TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__)
				.addParameters(parms).build();
		logger.info("uri:" + get.getURI());
		logger.info("1PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		logger.info(__COOKIE_VALUE__ == null ? Constants.__TEMP__COOKIE_VALUE__ : __COOKIE_VALUE__);
		logger.info("1PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");

		// Execute
		HttpResponse resp;
		try {
			resp = http.execute(get);
			Header[] headers = resp.getAllHeaders();
			if (__COOKIE_VALUE__ == null || Constants.__TEMP__COOKIE_VALUE__ == null) {
				for (Header header : headers) {
					System.out.println("header_name:" + header.getName() + ",header_value:"
							+ header.getValue());
					if (header.getName().equals("Set-Cookie")) {
						Constants.__TEMP__COOKIE_VALUE__ = header.getValue();
					}
				}
			}
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
		try {
			InputStream is = resp.getEntity().getContent();
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int count = 0;
			while ((count = is.read(bytes)) != -1) {
				bos.write(bytes, 0, count);
			}
			bytes = bos.toByteArray();
			logger.info("******************imagebyte************************************"+bytes);
			// 测试生成文件用
			// testCreateFile(bytes);
			return bytes;

		} catch (IOException e) {
			logger.error("Cannot read content", e);
			return null;
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

	@SuppressWarnings("unused")
	private void testCreateFile(byte[] bytes) {
		File file = new File(CODE_IMAGE_PATH);
		// file.deleteOnExit();
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

	public String testCookieRequest(final String submitType, final String url,
			final BasicNameValuePair... parms) throws URISyntaxException, ParseException,
			IOException {

		// Create HTTP.
		final HttpClient http = HttpClientBuilder.create().setUserAgent("")
				.setRetryHandler(StandardHttpRequestRetryHandler.INSTANCE)
				.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE).build();

		// Create get.
		final HttpUriRequest get = initRequestBuilder(submitType).setUri(url)
				.addHeader("Accept-Charset", "utf-8").addHeader("Accept-Encoding", "gzip,deflate")
				.addHeader("Accept", "application/json")
				// .addHeader("Cookie", "__auth__=" + (__AUTH__ == null ?
				// __TEMP__AUTH__ : __AUTH__))
				.addHeader("Cookie", __COOKIE_VALUE__).addParameters(parms).build();
		logger.info("uri:" + get.getURI());

		// Execute
		HttpResponse resp;
		try {
			resp = http.execute(get);

			System.out.println("******************************************************");
			Header[] headers = resp.getAllHeaders();
			for (Header header : headers) {
				logger.info("header_name:" + header.getName() + ",header_value:"
						+ header.getValue());
				if (header.getName().equals("Set-Cookie")) {
					__COOKIE_VALUE__ = header.getValue();

				}
			}
		} catch (IOException e) {
			logger.error("Cannot get http request", e);
			return "";
		}

		final StatusLine sl = resp.getStatusLine();
		if (sl == null) {
			logger.info("Cannot get HTTP status line.");
			return "";
		}

		if (sl.getStatusCode() != 200) {
			logger.info("HTTP Error: (" + sl.getStatusCode() + ")" + sl.getReasonPhrase());
			return "";
		}

		try {
			final String[] lines = IOUtils.readLines(resp.getEntity().getContent(), "utf-8")
					.toArray(new String[0]);
			logger.info(StringUtils.join(lines));
			return StringUtils.join(lines);
		} catch (IOException e) {
			logger.error("Cannot read content", e);
			return "";
		}

	}

	public static void main(String[] args) throws Exception {

		BasicNameValuePair[] parms = { new BasicNameValuePair("user-name", "admin"),
				new BasicNameValuePair("password", "12345678") };
//		String url0 = "http://localhost/p2psrv/security/captcha-image";

		// XrHttpClient.getInstance().sendByteRequest("GET", url0, parms);

		String url = "http://localhost/p2psrv/security/signin";
		MyHttpClient.getInstance().testCookieRequest("POST", url, parms);

		url = "http://localhost//p2psrv//mgr/crm/relations";
		BasicNameValuePair[] parms1 = { new BasicNameValuePair("user-name", "admin"),
				new BasicNameValuePair("password", "12345678"),
				new BasicNameValuePair("captcha-code", "") };
		MyHttpClient.getInstance().sendRequest(false, "GET", url, parms1);

	}

}
