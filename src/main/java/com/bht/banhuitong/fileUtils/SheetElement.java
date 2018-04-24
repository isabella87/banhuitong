package com.bht.banhuitong.fileUtils;

import java.util.ArrayList;
import java.util.List;

public class SheetElement {
	private String id;
	private String sheetName;

	private String title;

	private List<String> sqlList = new ArrayList<String>();

	public SheetElement() {
	}

	public SheetElement(String id,String sheetName, String title) {
		this.id =id;
		this.sheetName = sheetName;
		this.title = title;
	}

	public SheetElement(String id,String sheetName, String title, List<String> sqlList) {
		this.id =id;
		this.sheetName = sheetName;
		this.title = title;
		this.sqlList = sqlList;
	}
	public String getId() {
		return id;
	}

	public String getSheetName() {
		return sheetName;
	}

	public List<String> getSqlList() {
		return sqlList;
	}

	public String getTitle() {
		return title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setSqlList(List<String> sqlList) {
		this.sqlList = sqlList;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
