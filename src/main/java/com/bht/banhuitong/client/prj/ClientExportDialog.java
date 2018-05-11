package com.bht.banhuitong.client.prj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class ClientExportDialog extends Dialog implements

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
	public ClientExportDialog(boolean isAll, ListGrid listGrid) {
		this.grid = listGrid;
		final int WIDTH = 400;
		final int HEIGHT = 300;

		final String GUIDANCE = "Press Ctrl-C (Command-C on Mac) or right click (Option-click on Mac) on the selected text to copy values, then paste into Excel.  Note that values in columns that are dates or numbers are correctly understood as dates and numbers in Excel.";

		int[][] cells = grid.getCellSelection().getSelectedCells();
		if (cells == null || cells.length == 0) {
//			return;
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
		
		IButton downloadButton = new IButton();  //buttonItem
		downloadButton.setWidth(130);
		downloadButton.setAlign(Alignment.CENTER);
		downloadButton.setTitle("导出");
		
		Label spaceLabel = new Label();
		spaceLabel.setWidth(100);
		
		HLayout hLayout = new HLayout();
		hLayout.setMargin(20);
		hLayout.setWidth(400);
		hLayout.setHeight(30);
		hLayout.addMembers(button,spaceLabel,downloadButton);
		
		DynamicForm form = new DynamicForm();
		form.setNumCols(1);
		form.setWidth(WIDTH);
		form.setHeight(HEIGHT);
		form.setAutoFocus(true);
		form.setFields(new FormItem[] { label, this.textArea});
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

		final ArrayList<String> fieldNames = new ArrayList<String>();
		if (isAll) {
			records = grid.getRecords();
			for (int j = 0; j < records[0].getAttributes().length; j++) {
				String name = records[0].getAttributes()[j];
				if(name.startsWith("__")) {
					continue;
				}
				fieldNames.add(name);
				
//				fieldNames.addAll(Arrays.asList(records[0].getAttributes()));
			}
		} else {
			int firstRow = cells[0][0];
			for (int i = 0; i < cells.length; i++) {
				if (cells[i][0] != firstRow)
					break;
				fieldNames.add(grid.getFieldName(cells[i][1]));
			}
		}

		for (int i = 0; i < records.length; i++) {

			int index = grid.getRecordIndex((ListGridRecord) records[i]);
			if (index >= 0)
				records[i] = grid.getEditedRecord(index);

			if (i == 0) {
				for (int j = 0; j < fieldNames.size(); j++) {

					sb.append("\"").append(fieldNames.get(j)).append("\"").append("	");
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
		
		downloadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<Map<String,String>> dataLists = new ArrayList<Map<String,String>>();
				Record[] records = grid.getSelectedRecords();
				for(Record record: records) {
					Map<String,String> dataMap = new LinkedHashMap<String,String>();
					for(String fieldName : fieldNames) {
						dataMap.put(fieldName, record.getAttributeAsString(fieldName));
					}
					dataLists.add(dataMap);
				}
				//TODO 调用客户端，写入到excel的服务。
//				String filepath = "E:\\datas";
//				new Export2File().export(filepath, "xls", dataLists);
				
			}
		});
	}
	
}
