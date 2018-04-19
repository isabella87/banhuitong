package com.bht.banhuitong.client.prj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.LoginWindow;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.Portlet;

public class BaseGridPortlet extends Portlet {
	
	protected static List<String> emptyArrayList = new ArrayList<String>();
	ListGrid retListGrid = new ListGrid();	
	
	
	public ListGrid getRetListGrid() {
		return retListGrid;
	}

	public BaseGridPortlet(String title) {
		this.setTitle(title);
		this.setShowCloseConfirmationMessage(false);
		init();
		registClickEvent();
	}
	
	private void registClickEvent() {
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Portlet portlet = (Portlet)event.getSource();
				portlet.bringToFront();
			}
		});
		this.addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				BaseGridPortlet obj = (BaseGridPortlet) event.getSource();
				new BaseFrame().delPortlet(obj.getTitle());
			}
			
		});
	}

	public void init() {

	}
	
	public BaseGridPortlet getPortlet() {
		return this;
	}
	
	/**
	 * 
	 * @param dateStr  为时间毫秒数的字符串
	 * @return
	 */
	public String formatDateStr(Object dateStr, String format) {
		if(dateStr==null||dateStr.toString().trim().isEmpty()) {
			return "";
		}
		return formatDate(new Date(Long.valueOf(dateStr.toString().trim())),format);
	}

	public String formatDate(Date d, String format){
		if (d==null){
			return "";
		}
		DateTimeFormat formater = DateTimeFormat.getFormat(format);
		return formater.format(d);
	}
	
	@SuppressWarnings("unused")
	public static Date addDay(Date date, int addDay){
		date.setTime(date.getTime()-4*365*24*60*60*1000);
		if(date == null){
			date = new Date();
		}
		return date;
	}
	
	/**
	 * 初始化ListGrid基础结构
	 * 
	 * @param listGrid
	 * @return
	 */
	public ListGrid initListGridFields(ListGrid listGrid,Map<String, String> fieldItems,Collection<String> canEditFields) {
		ListGridField idField = new ListGridField("id", "序号", 50);
		ArrayList<ListGridField> fields = new ArrayList<ListGridField>();
		fields.add(idField);

		for (String key : fieldItems.keySet()) {
			ListGridField field = new ListGridField(key, fieldItems.get(key));
			
			if(canEditFields.contains(key)) {
				field.setCanEdit(true);
			}
			if(key.equals("NULLABLE")||key.equals("PRIMARY_KEY")) {
				field.setType(ListGridFieldType.BOOLEAN); 
				field.setDefaultValue(false);
			}
			
			if(key.equals("PRIMARY_KEY")) {
				field.setType(ListGridFieldType.BOOLEAN); 
				field.setDefaultValue(false);
				field.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						// TODO Auto-generated method stub
						SC.say("hi");
						ListGridField thisField = (ListGridField) event.getSource();
						if(thisField.getValueField().equals("true")) {
							thisField.setIcon("actions/tablekey.png");
						}else {
							thisField.setIcon("");
						}
					}
					
				});
			}
			
			if(key.equals("DATA_TYPE")){
				String[] valueMap = {"VARCHAR2","NUMBER","DATE","CLOB","BLOB","BFILE","DECIMAL","FLOAT","INT","INTEGER","TIMESTAMP"};
				field.setValueMap(valueMap);
			}
			
			fields.add(field);
		}
		listGrid.setFields(fields.toArray(new ListGridField[fields.size()]));
		return listGrid;
	}
	
	public void showErrorMessage(final String errorMsg) {
		String errorCode = errorMsg.substring(errorMsg.indexOf("@@@")+3).trim();
		errorCode = errorCode.substring(0,errorCode.indexOf("@@@")).trim();
		if(errorCode.matches("[0-9]+")) {
			if(Integer.valueOf(errorCode)==1) {//如果是未登录状态，则重新打开登录页面
				new LoginWindow().init(); 
			}else {
				SC.say(BaseFrame.exceMap.get(Integer.valueOf(errorCode)));
			}
		}else {
			SC.say(BaseFrame.exceMap.get(0));
		}
	}
	
}
