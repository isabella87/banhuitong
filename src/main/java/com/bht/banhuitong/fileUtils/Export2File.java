package com.bht.banhuitong.fileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Export2File {

	private String fileName;
	private String rootPath = "d:\\exportdata";

	private String exportSytle;// 为xls等

	public String initFileName(String endName) {
		String filename = new java.sql.Timestamp(new Date().getTime()).toString();
		filename = filename.replace("-", "").replace(":", "").replace(".", "").replace(" ", "");
		File file = new File(rootPath);
		file.mkdir();
		filename = filename +endName + "." + exportSytle;
		fileName = rootPath + File.separator + filename;
		try {
			fileName = new String(fileName.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

	/**
	 * 导出exl
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public String export(String rootPath,String moduleAndServiceno,String exportSytle, List<Map<String, String>> data) {
		this.exportSytle = exportSytle;
		this.rootPath = rootPath;
		String filename = initFileName(moduleAndServiceno);
		OutputStream output;
		try {
			output = new FileOutputStream(fileName, true);

			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
			HSSFWorkbook wb = new HSSFWorkbook();

			restructSheet(wb, new SheetElement("1", moduleAndServiceno, "title"), data);
			bufferedOutPut.flush();
			wb.write(bufferedOutPut);
			bufferedOutPut.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output   is   closed ");
		}
		return filename;
	}

	/**
	 * 
	 * @param wb
	 * @param sheetE
	 * @param data
	 */
	public static void restructSheet(HSSFWorkbook wb, SheetElement sheetE, List<Map<String, String>> data) {

		String sheetName = sheetE.getSheetName();
		String sheetTitle = sheetE.getTitle();

		// 创建单元格样式
		HSSFCellStyle cellStyleTitle = wb.createCellStyle();
		// 指定单元格居中对齐
		cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 指定单元格垂直居中对齐
		cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 指定当单元格内容显示不下时自动换行
		cellStyleTitle.setWrapText(true);
		// ------------------------------------------------------------------
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 指定单元格居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 指定单元格垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 指定当单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyleTitle.setFont(font);

		// 工作表名
		HSSFSheet sheet = wb.createSheet(sheetName);
		ExcelUtil exportExcel = new ExcelUtil(wb, sheet);
		if (data != null && data.size() > 0) {
			Map<String, String> map = data.get(0);

			// 创建报表头部
//			exportExcel.createNormalHead(sheetTitle, map.size()-1);
			// 定义第一行
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);

			// 第一行第一列
			cell1.setCellStyle(cellStyleTitle);
			int j = 1;
			for (String key : map.keySet()) {
				cell1.setCellValue(new HSSFRichTextString(key));
				cell1 = row1.createCell(j);
				cell1.setCellStyle(cellStyleTitle);
				j++;
			}

			// 定义第二行
			for (int i = 0; i < data.size(); i++) {
				Map<String, String> maps = data.get(i);
				j = 0;

				HSSFRow row = sheet.createRow(i + 1);
				for (String value : maps.values()) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(new HSSFRichTextString(value==null||value.isEmpty()?"":value));
					j++;
				}
			}
		}
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

		new Export2File().export("d:\\exportdata","_2_2","xls", data);
	}
}