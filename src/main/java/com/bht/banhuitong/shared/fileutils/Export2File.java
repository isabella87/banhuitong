package com.bht.banhuitong.shared.fileutils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Export2File {

	private String fileName;

	private String fileAllPath;
	private String rootPath = "d:\\exportdata";

	private String exportFileSytle;// 为xls等

	public Export2File(String fileName) {
		this.fileName = fileName;
	}

	public String initFileName(String endName) {
		String filename = "";
		if (fileName != null && !fileName.isEmpty()) {
			filename = fileName;
		} else {
			filename = new java.sql.Timestamp(new Date().getTime()).toString();
			filename = filename.replace("-", "").replace(":", "").replace(".", "").replace(" ", "");
		}
		File file = new File(rootPath);
		file.mkdirs();
		filename = filename + endName + "." + exportFileSytle;
		fileAllPath = rootPath + File.separator + filename;
		try {
			fileAllPath = new String(fileAllPath.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileAllPath;
	}

	/**
	 * 导出exl
	 * 
	 * @param rootPath
	 * @param moduleAndServiceno
	 * @param exportFileSytle
	 * @param data
	 * @return
	 */
	public String export(String sheetName, String rootPath, String moduleAndServiceno, String exportFileSytle,
			List<Map<String, String>> data) {
		this.rootPath = rootPath;
		this.exportFileSytle = exportFileSytle;
		String filename = initFileName(moduleAndServiceno);

		ExcelUtil excelUtil = new ExcelUtil(moduleAndServiceno.isEmpty() ? sheetName : moduleAndServiceno, data);
		
		// 判断之前是否有该文件，如果有，则将原来的文件保存为备份文件，再复写该文件；如果没有，则直接写该文件
		excelUtil.backup(fileAllPath);
		
		excelUtil.outputExcel(fileAllPath);

		return filename;
	}

	public static void main(String[] args) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		data.clear();
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.clear();
		mapData.put("A1", "V1");
		mapData.put("A11", "V11");
		mapData.put("A111", "V111");
		mapData.put("A1111", "V1111");
		mapData.put("A11111", "V11111");
		Map<String, String> mapData2 = new HashMap<String, String>();
		mapData2.put("A1", "V2");
		mapData2.put("A11", "V22");
		mapData2.put("A111", "V222");
		mapData2.put("A1111", "V2222");
		mapData2.put("A11111", "V22222");

		data.add(mapData);
		data.add(mapData2);

		new Export2File(null).export("sheetName", "d:\\exportdata\\report", "", "xls", data);

		// new Export2File().export(Configuration.getString(Configuration.REPORT_PATH),
		// "", "xls", data);

		System.out.println("done");
	}
}