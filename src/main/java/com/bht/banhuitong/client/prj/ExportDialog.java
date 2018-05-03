package com.bht.banhuitong.client.prj;

import java.util.ArrayList;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.BasePortlet;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class ExportDialog extends Dialog implements

		com.smartgwt.client.widgets.form.fields.events.ClickHandler {

	private DynamicForm form;

	private TextAreaItem textArea;

	private ListGrid grid;

	public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
		this.removeItem(this.form);
		this.markForDestroy();
		this.hide();
	};

	/**
	 * 
	 * @param isAll
	 *            true:输出全部； false:输出选中部分。
	 * @param listGrid
	 */
	public ExportDialog(boolean isAll, ListGrid listGrid) {
		this.grid = listGrid;
		final int WIDTH = 400;
		final int HEIGHT = 300;

		final String GUIDANCE = "Press Ctrl-C (Command-C on Mac) or right click (Option-click on Mac) on the selected text to copy values, then paste into Excel.  Note that values in columns that are dates or numbers are correctly understood as dates and numbers in Excel.";

		int[][] cells = grid.getCellSelection().getSelectedCells();
		if (cells == null || cells.length == 0) {
			// return;
		}
		/*
		 * TextExportSettings settings = new TextExportSettings();
		 * settings.setFieldList(fieldNames.toArray(new String[0]));
		 * settings.setFieldSeparator("\t");
		 * settings.setEscapingMode(EscapingMode.DOUBLE);
		 */

		StaticTextItem label = new StaticTextItem();
		label.setName("label");
		label.setShowTitle(false);
		label.setValue(GUIDANCE);

		TextAreaItem area = new TextAreaItem();
		area.setName("textArea");
		area.setShowTitle(false);
		area.setCanEdit(true);
		area.setHeight("*");
		area.setWidth("*");
		this.textArea = area;

		IButton button = new IButton();
		button.setWidth(130);
		button.setAlign(Alignment.CENTER);
		button.setTitle("Done");
		final Dialog dialog = this;

		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialog.markForDestroy();
				dialog.hide();
			}
		});

		IButton downloadButton = new IButton(); // buttonItem
		downloadButton.setWidth(130);
		downloadButton.setAlign(Alignment.CENTER);
		downloadButton.setTitle("导出");

		downloadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				BasePortlet portlet = BaseFrame.getTail(BaseFrame.portlets).getValue();
				BaseFrame.export(portlet);
			}
		});

		Label spaceLabel = new Label();
		spaceLabel.setWidth(100);

		HLayout hLayout = new HLayout();
		hLayout.setMargin(20);
		hLayout.setWidth(400);
		hLayout.setHeight(30);
		hLayout.addMembers(button, spaceLabel, downloadButton);

		DynamicForm form = new DynamicForm();
		form.setNumCols(1);
		form.setWidth(WIDTH);
		form.setHeight(HEIGHT);
		form.setAutoFocus(true);
		form.setFields(new FormItem[] { label, this.textArea });
		this.form = form;

		this.setAutoSize(true);
		this.setShowToolbar(false);
		this.setCanDragReposition(true);
		this.setTitle("Copy Cells");
		this.setShowModalMask(true);
		this.setIsModal(true);
		this.addItem(form);
		this.addItem(hLayout);

		StringBuffer sb = new StringBuffer();

		Record[] records = grid.getSelectedRecords();

		ArrayList<String> fieldNames = new ArrayList<String>();
		ArrayList<String> fieldTitles = new ArrayList<String>();
		if (isAll) {
			records = grid.getRecords();

			ListGridField[] fields = grid.getFields();
			for (ListGridField field : fields) {
				fieldNames.add(field.getName());
				fieldTitles.add(field.getTitle());
			}
		} else {
			int firstRow = cells[0][0];
			for (int i = 0; i < cells.length; i++) {
				if (cells[i][0] != firstRow)
					break;
				fieldNames.add(grid.getFieldName(cells[i][1]));
				fieldTitles.add(grid.getField(grid.getFieldName(cells[i][1])).getTitle());
			}
		}

		for (int i = 0; i < records.length; i++) {

			int index = grid.getRecordIndex((ListGridRecord) records[i]);
			if (index >= 0)
				records[i] = grid.getEditedRecord(index);

			if (i == 0) {
				for (int j = 0; j < fieldTitles.size(); j++) {

					sb.append("\"").append(fieldTitles.get(j)).append("\"").append("	");
				}
				sb.append("\n");
			}

			for (int j = 0; j < fieldNames.size(); j++) {

				sb.append("\"").append(records[i].getAttributeAsString(fieldNames.get(j))).append("\"").append("	");
			}
			sb.append("\n");
		}
		this.textArea.setValue(sb.toString());
		this.textArea.selectValue();
		
	}
	
	/**
	 * 刚方法不好用，ActiveXObject仅能在IE浏览器使用。
	 */
	public static native void saveAs() /*-{
		var strFile = "c:\\liehuo.net.txt";
		var objFSO = new ActiveXObject("Scripting.FileSystemObject");
		// 检查文件是否存在
		if (!objFSO.FileExists(strFile)){
		// 创建文本文件
		var objStream = objFSO.CreateTextFile(strFile, true);
		document.write("创建文本文件: " + strFile + "<br>");
		objStream.Close();  // 关闭文件
		}else{
			document.write("文本文件: " + strFile + "已经存在<br>");
		}
	}-*/;

}
