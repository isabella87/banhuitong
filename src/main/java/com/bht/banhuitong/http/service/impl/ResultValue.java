package com.bht.banhuitong.http.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bht.banhuitong.exception.RpcExceptionMessage;

public class ResultValue {

	public Logger logger = Logger.getLogger(ResultValue.class);

	private InputStream stream;

	public ResultValue(InputStream stream) {
		this.stream = stream;
	}

	public byte[] byteValue() {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int count = 0;
		try {
			while ((count = this.stream.read(bytes)) != -1) {
				bos.write(bytes, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.flush();
				bos.close();
				this.stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bos.toByteArray();

	}

	public String stringValue() {
		String[] lines = null;
		try {
			lines = IOUtils.readLines(this.stream, "utf-8").toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String returnStr = StringUtils.join(lines);
		logger.info("*********************************************" + returnStr);
		return returnStr;
	}

	/**
	 * 
	 * @param isLoginReq
	 * @param submitType
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> listValue() {

		String[] lines = null;
		try {
			lines = IOUtils.readLines(this.stream, "utf-8").toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String str = StringUtils.join(lines);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		RpcExceptionMessage rpcExceptionMessage = new RpcExceptionMessage(str);
		if (rpcExceptionMessage.isHasException()) {
			logger.info("have exception:" + str);
			Map<String, String> retMap = new HashMap<String, String>();
			retMap.put("error", rpcExceptionMessage.getM_exception());
			list.add(retMap);
			return list;
		}

		if (str.startsWith("[") && str.endsWith("]")) {
			str = str.substring(1, str.length() - 1);
		}
		if (str.length() > 2) {
			str = str.substring(1, str.length() - 1);
			str = str.replace("{", "");
			list = new ArrayList<Map<String, String>>();
			for (String s : str.split("},")) {
				Map<String, String> map = new HashMap<String, String>();
				for (String ss : s.split(",")) {
					if (ss.contains(":")) {
						String[] item = ss.split(":");
						String key = checkStr(item[0].trim());
						String value = checkStr(item[1].trim());
						map.put(key, value);
					}
				}
				list.add(map);
			}
		}

		return list;
	}

	private String checkStr(String str) {
		str = str.replace("\r", "").replace("\n", "");

		if (str.startsWith("\"") && str.endsWith("\"")) {
			if (str.length() > 2) {
				str = str.substring(1, str.length() - 1);
			} else {
				str = "";
			}
		}

		if (str == null) {
			str = "";
		}

		return str;
	}

}
