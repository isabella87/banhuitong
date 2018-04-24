package com.bht.banhuitong.client.prj;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.MainFrame;
import com.bht.banhuitong.server.DbModelService;
import com.bht.banhuitong.server.DbModelServiceAsync;
import com.bht.banhuitong.server.PrjService;
import com.bht.banhuitong.server.PrjServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.TextExportSettings;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.EscapingMode;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class MainPrjPortlet3 extends BasePortlet implements ClickHandler,com.smartgwt.client.widgets.events.ClickHandler{

	private int hBarValue = 0;
	
	private static Window conWind = new Window();
	private final PrjServiceAsync prjService = GWT.create(PrjService.class);
	private final DbModelServiceAsync dbModelService = GWT.create(DbModelService.class);
	private ListGrid grid = new ListGrid();
	private static Map<String, String> prjFieldItems = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> prjTypeItems = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> prjStatusItems = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> prjSignStatusItems = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> timeTypeItems = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> searchKeyItems = new LinkedHashMap<String, String>();
	private static String portletTitleName = "系统建模 -项目1";
	private MainPrjPortlet3 portletInstance;

	public MainPrjPortlet3 getInstance() {
		if (portletInstance == null) {
			portletInstance = new MainPrjPortlet3();
		}
		return portletInstance;
	}

	private class MyDialog extends Dialog implements   
	  
    com.smartgwt.client.widgets.form.fields.events.ClickHandler {  

    private DynamicForm form;  

    private TextAreaItem textArea;  

    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
        this.removeItem(this.form);  
        this.markForDestroy();  
        this.hide();  
    };  

    public MyDialog() {  
          
        final int WIDTH = 525;  
        final int HEIGHT = 300;  

        final String GUIDANCE = "Press Ctrl-C (Command-C on Mac) or right click (Option-click on Mac) on the selected text to copy values, then paste into Excel.  Note that values in columns that are dates or numbers are correctly understood as dates and numbers in Excel.";  

        int[][] cells = grid.getCellSelection().getSelectedCells();  
        if (cells == null || cells.length == 0) return;  

        ArrayList<String> fieldNames = new ArrayList<String>();  

        int firstRow = cells[0][0];  
        for (int i = 0; i < cells.length; i++) {  
            if (cells[i][0] != firstRow) break;  
            fieldNames.add(grid.getFieldName(cells[i][1]));  
        }  

        TextExportSettings settings = new TextExportSettings();  
        settings.setFieldList(fieldNames.toArray(new String[0]));  
        settings.setFieldSeparator("\t");  
        settings.setEscapingMode(EscapingMode.DOUBLE);  

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

        ButtonItem button = new ButtonItem();  
        button.setName("done");  
        button.setAlign(Alignment.CENTER);  
        button.setTitle("Done");  
        button.addClickHandler(this);  
      
        DynamicForm form = new DynamicForm();  
        form.setNumCols(1);  
        form.setWidth(WIDTH);  
        form.setHeight(HEIGHT);  
        form.setAutoFocus(true);  
        form.setFields(new FormItem[]{ label, this.textArea, button });  
        this.form = form;  

        this.setAutoSize(true);  
        this.setShowToolbar(false);  
        this.setCanDragReposition(true);  
        this.setTitle("Copy Cells");  
        this.setShowModalMask(true);  
        this.setIsModal(true);  
        this.addItem(form);  

        StringBuffer sb = new StringBuffer();
        
        Record[] records = grid.getSelectedRecords();  
        
        for (int i = 0; i < records.length; i++) {  
        	
            int index = grid.getRecordIndex((ListGridRecord)records[i]);  
            if (index >= 0) records[i] = grid.getEditedRecord(index);  
            
            if(i==0) {
            	for(int j = 0;j<  fieldNames.size();j++) {
                	
                	sb.append("\"").append(prjFieldItems.get(fieldNames.get(j))).append("\"").append("	");
                }	
            	sb.append("\n");
            }
            
            for(int j = 0;j<  fieldNames.size();j++) {
            	
            	sb.append("\"").append(records[i].getAttributeAsString(fieldNames.get(j))).append("\"").append("	");
            }
            sb.append("\n");
        }  
        this.textArea.setValue(sb.toString());  
        this.textArea.selectValue();  
    }  
};

	/**
	 * items key值与服务返回map中的key一一对应
	 */
	static {
		prjFieldItems.put("pId", "PID");
		prjFieldItems.put("type", "类型");
		prjFieldItems.put("itemNo", "编号");
		prjFieldItems.put("itemName", "名称");
		prjFieldItems.put("status", "状态");
		prjFieldItems.put("signStatus", "签章状态");
		prjFieldItems.put("amt", "金额");
		prjFieldItems.put("investedAmt", "已募集金额");
		prjFieldItems.put("borrowDays", "借款天数");
		prjFieldItems.put("rate", "年化收益");
		prjFieldItems.put("costFee", "买入费率");
		prjFieldItems.put("soldFee", "卖出费率");
		prjFieldItems.put("totalInterest", "总利息");
		prjFieldItems.put("inProxy", "融资项目经理");
		prjFieldItems.put("financier", "融资方");
		prjFieldItems.put("onLineTime", "上线时间");
		prjFieldItems.put("loanTime", "放款时间");
		prjFieldItems.put("capitalRepayTime", "预计还本时间");

		prjTypeItems.put("0", "全部");
		prjTypeItems.put("9", "个人贷");
		prjTypeItems.put("10", "企业贷");
		prjTypeItems.put("5", "票据贷");
		prjTypeItems.put("1", "工程贷");
		prjTypeItems.put("6", "班汇宝");
		prjTypeItems.put("7", "供应贷");
		prjTypeItems.put("8", "分销贷");

		prjStatusItems.put("", "全部");
		prjStatusItems.put("40", "募集中");
		prjStatusItems.put("0", "未提交");
		prjStatusItems.put("1", "待项目经理审批");
		prjStatusItems.put("10", "待风控审批");
		prjStatusItems.put("20", "待评委会审批");
		prjStatusItems.put("30", "待业务副总审批");
		prjStatusItems.put("50", "已募集满标");
		prjStatusItems.put("60", "已确认满标");
		prjStatusItems.put("70", "待复评");
		prjStatusItems.put("80", "已放款");
		prjStatusItems.put("90", "正在还款");
		prjStatusItems.put("999", "已结清");
		prjStatusItems.put("-1", "流标");

		prjSignStatusItems.put("0", "未签章");
		prjSignStatusItems.put("1", "已签章");

		timeTypeItems.put("0", "创建时间");
		timeTypeItems.put("1", "上线时间");
		timeTypeItems.put("2", "放款时间");

		searchKeyItems.put("0", "编号");
		searchKeyItems.put("1", "名称");
		searchKeyItems.put("2", "融资方");
		searchKeyItems.put("3", "创建人");

	}

	public MainPrjPortlet3() {
		super(portletTitleName);
	}

	public void init() {
		final ListGrid listGrid = new ListGrid();

		listGrid.setHeight100();
		listGrid.setWidth100();
		listGrid.setTop(20);
		listGrid.setLeft(5);
		listGrid.setShowAllRecords(true);
		listGrid.setShowEmptyMessage(true);
		listGrid.setEmptyMessage("请点击<b>搜索</b>按钮查询数据！");
		listGrid.setAutoFetchData(true);
		listGrid.setCanSelectCells(true);
		listGrid.setCanDragSelect(true);
		
//		listGrid.setCanEdit(true);
		listGrid.setEditEvent(ListGridEditEvent.CLICK);
		listGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		
		initListGridFields(listGrid,prjFieldItems,emptyArrayList);

		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND,
				new AdvancedCriteria[] { new AdvancedCriteria("itemName", OperatorId.INOT_CONTAINS, "i"),
						new AdvancedCriteria("itemNo", OperatorId.INOT_STARTS_WITH, "p") });
		listGrid.setAllowFilterOperators(true);
		listGrid.setCriteria(criteria);

		listGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {

			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				SC.say("你好！我是美丽的详情编辑界面");
			}

		});

		final ComboBoxItem timeTypeItem = new ComboBoxItem("dateTime");
		timeTypeItem.setTitle("时间类型");
		timeTypeItem.setDefaultToFirstOption(true);
		timeTypeItem.setValueMap(timeTypeItems);

		final DateItem startDateItem = new DateItem("startDate");
		startDateItem.setTitle("开始时间");
		startDateItem.setDefaultValue(addDay(new Date(), -365));

		final DateItem endDateItem = new DateItem("endDate");
		endDateItem.setTitle("结束时间");

		final ComboBoxItem prjTypeItem = new ComboBoxItem("type");
		prjTypeItem.setDefaultToFirstOption(true);
		prjTypeItem.setTitle("项目类型");
		prjTypeItem.setValueMap(prjTypeItems);

		final ComboBoxItem prjStatusItem = new ComboBoxItem("status");
		prjStatusItem.setDefaultValue("全部");
		prjStatusItem.setTitle("项目状态");
		prjStatusItem.setValueMap(prjStatusItems);

		final ComboBoxItem searchKeyTypeItem = new ComboBoxItem("searchKeyType");
		searchKeyTypeItem.setTitle("关键字类型");
		searchKeyTypeItem.setDefaultToFirstOption(true);
		searchKeyTypeItem.setValueMap(searchKeyItems);

		HLayout searchPanel = new HLayout();
		searchPanel.setWidth(MainFrame.window.getWidth() - 12);
		searchPanel.setHeight(40);

		final TextItem searchKeyItem = new TextItem();
		searchKeyItem.setTitle("关键字");

		final DynamicForm searchForm = new DynamicForm();

		searchForm.setWidth(300);
		searchForm.setPadding(10);
		searchForm.setLayoutAlign(VerticalAlignment.BOTTOM);
		searchForm.setEdgeMarginSize(10);
		searchForm.setFields(timeTypeItem, startDateItem, endDateItem, prjTypeItem, prjStatusItem, searchKeyTypeItem,
				searchKeyItem);

		this.addItem(listGrid);

		Menu menu = new Menu();
		MenuItem searchMenu = new MenuItem("搜索");
		MenuItem printerMenu = new MenuItem("打印");
		MenuItem exportExlMenu = new MenuItem("导出Excel");
		exportExlMenu.addClickHandler(this);
		MenuItem createTableMenu = new MenuItem("创建数据库");
		MenuItemSeparator separator = new MenuItemSeparator();
		MenuItem searchCondMenu = new MenuItem("设置搜索条件");
		menu.setItems(searchMenu, printerMenu,exportExlMenu, separator, searchCondMenu,createTableMenu);

		this.setContextMenu(menu);
//		listGrid.setContextMenu(menu);
		conWind.addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				conWind.setVisible(false);
			}
		});

		searchCondMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				conWind = new Window();
				conWind.setTitle("设置搜索条件");
				conWind.setAutoSize(true);
				conWind.setShowMinimizeButton(false);
				conWind.setIsModal(true);
				conWind.setShowModalMask(true);
				conWind.setAutoCenter(true);
				conWind.centerInPage();

				conWind.clear();
				conWind.addItem(searchForm);
				conWind.setVisible(true);
				conWind.bringToFront();
				conWind.draw();
			}
		});

		printerMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Canvas.showPrintPreview(listGrid);
				Object[] objects = { listGrid };
				Canvas.showPrintPreview(objects, null, portletTitleName, null);
			}
		});
		
		
		exportExlMenu.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				//TODO 
				new MyDialog().draw();
			}
		});

		/*createTableMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				String tableName = "ABC_BAO";
				Map<String,String> tableFieldsMap = new HashMap<String,String>();
				tableFieldsMap.put("ID", "NUMBER(12)");
				tableFieldsMap.put("NAME", "VARCHAR2(40)");
				tableFieldsMap.put("AMT", "NUMBER(18,2)");
				tableFieldsMap.put("DATEPOINT", "DATE");
				dbModelService.createTable(tableName, tableFieldsMap, new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say("网络连接异常！");
						
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result){
							SC.say("表创建成功！");
						}else {
							SC.say("表创建失败！");	
						}
					}
					
				});
				
			}
		});*/
		
		searchMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final Map<String, String> paramMap = new HashMap<String, String>();
				if (timeTypeItem.getValueAsString() != null) {
					if (timeTypeItem.getValueAsString().equals("0")) {
						paramMap.put("start-time", String.valueOf(((Date) startDateItem._getValue()).getTime()));
						paramMap.put("end-time", String.valueOf(((Date) endDateItem._getValue()).getTime()));
					} else if (timeTypeItem.getValueAsString().equals("1")) {
						paramMap.put("on-line-start-time", startDateItem.getValueField());
						paramMap.put("on-line-end-time", endDateItem.getValueField());
					} else if (timeTypeItem.getValueAsString().equals("2")) {
						paramMap.put("loan-start-time", startDateItem.getValueField());
						paramMap.put("loan-end-time", endDateItem.getValueField());
					}

					if (searchKeyTypeItem.getValueAsString().equals("0")) {
						paramMap.put("item-no-key", searchKeyItem.getValueAsString());
					} else if (searchKeyTypeItem.getValueAsString().equals("1")) {
						paramMap.put("item-name-key", searchKeyItem.getValueAsString());
					} else if (searchKeyTypeItem.getValueAsString().equals("2")) {
						paramMap.put("financier-key", searchKeyItem.getValueAsString());
					} else if (searchKeyTypeItem.getValueAsString().equals("3")) {
						paramMap.put("creator-key", searchKeyItem.getValueAsString());
					}

					paramMap.put(prjTypeItem.getName(),
							prjTypeItem.getValueAsString().equals("0") ? "" : prjTypeItem.getValueAsString());
					paramMap.put(prjStatusItem.getName(),
							prjStatusItem.getValueAsString().equals("全部") ? "" : prjStatusItem.getValueAsString());

				}
				
				//TODO 
				hBarValue = 0;
				BaseFrame.hBar.setPercentDone(hBarValue);

				new Timer() {  
		             public void run() {  
		            	 hBarValue += 1; 
		            	 if (hBarValue >= 100) {  
		                	 hBarValue = 100; 
		                 }else if(hBarValue > 90){
		                	 hBarValue = 90; 
		                 }
		            	 //*******
						if(hBarValue==1) {
			            	 prjService.queryPrjList(paramMap, new AsyncCallback<List<Map<String, Object>>>() {
								@Override
								public void onFailure(Throwable caught) {
									showErrorMessage(caught.getMessage());
									hBarValue = 100; 
								}
			
								@Override
								public void onSuccess(List<Map<String, Object>> result) {
									if (result == null || result.isEmpty()) {
										listGrid.setData(new ListGridRecord[] {});
										SC.say("没有符合条件的数据！");
									} else {
										Object rpcExceptionMessage = result.get(0).get("error");
										if (rpcExceptionMessage != null) {
											SC.say(rpcExceptionMessage.toString());
										} else {
											listGrid.setData(new ListGridRecord[] {});
											listGrid.setData(getRecords(result));
											retListGrid = listGrid;
										grid = listGrid;
										}
									}
									
									hBarValue = 100; 
								}
							});
						}
						
						BaseFrame.headerHBarLabel.setContents("");
						BaseFrame.headerHBarLabel.setContents("正在接收数据......");
						BaseFrame.hBar.setPercentDone(hBarValue); 
						BaseFrame.hBarLabel.setContents(hBarValue+"%");
		                 
						if(hBarValue!=100)  {
		                     schedule(5 + (int) (1 * Math.random()));  
		                 }else {
		                	 BaseFrame.hBar.destroy();
		                	 BaseFrame.hBarLabel.destroy();
		                	 BaseFrame.headerHBarLabel.setContents("就绪");
		                 }
				//*********
		         	}  
		         }.schedule(100); 
		         
		        VLayout vlayout = new VLayout(4);  
		  		vlayout.setWidth(300); 
		  		vlayout.setTop(3);
		  		vlayout.setHeight(22); 
		  		HLayout hLayout = new HLayout();
		  		hLayout.addMember(BaseFrame.headerHBarLabel);
		  		hLayout.addMember(BaseFrame.hBar);
		  		hLayout.addMember(BaseFrame.hBarLabel);
		  		vlayout.addMember(hLayout);
		  		
		  		BaseFrame.endCanvas.addChild(vlayout);
			}
		});
		listGrid.redraw();
	}
	
	
	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public ListGridRecord[] getRecords(List<Map<String, Object>> result) {
		int length = result.size();
		ListGridRecord[] listRecords = new ListGridRecord[length];
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> mapItem = result.get(i);
			listRecords[i] = createRecord(mapItem, i);
		}

		return listRecords;
	}

	public ListGridRecord createRecord(Map<String, Object> mapItem, int i) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("id", ++i);
		record.setCanSelect(true);
		for (String key : prjFieldItems.keySet()) {
			if(mapItem.get(key)==null||mapItem.get(key).toString().isEmpty()) {
				continue ;
			}
			if (key.equals("type")) {
				record.setAttribute(key, prjTypeItems.get(mapItem.get(key)));
			} else if (key.equals("status")) {
				record.setAttribute(key, prjStatusItems.get(mapItem.get(key)));
			} else if (key.equals("signStatus")) {
				record.setAttribute(key, prjSignStatusItems.get(mapItem.get(key)));
			} else if (key.equals("onLineTime") || key.equals("capitalRepayTime") || key.equals("loanTime")) {
				record.setAttribute(key, formatDateStr(mapItem.get(key), "yyyy-MM-dd"));
			} else if(key.equals("amt")||key.equals("investedAmt")||key.equals("totalInterest")||key.equals("costFee")||key.equals("soldFee")){
				record.setAttribute(key, Double.valueOf(mapItem.get(key).toString()));
			}else if(key.equals("borrowDays")){
				record.setAttribute(key, Integer.valueOf(mapItem.get(key).toString()));
			}else {
				record.setAttribute(key, mapItem.get(key));
			}
		}
		record = addLostAttribute(record);
		return record;
	}

	private ListGridRecord addLostAttribute(ListGridRecord record) {
		for (String fieldKey : prjFieldItems.keySet()) {
			if (record.getAttribute(fieldKey) == null) {
				record.setAttribute(fieldKey, "");
			}
		}
		return record;
	}
	
	/*public void onClick(MenuItemClickEvent event) {  
//		if(event.getItem().getTitle().equals("导出Excel")) {
			
			this.new MyDialog().draw();  
//		}
    } */
	
	 
    public void onClick(MenuItemClickEvent event) {  
        new MyDialog().draw();  
    }  
  
    public void onCellContextClick(CellContextClickEvent event) {  
        Menu menu = this.grid.getContextMenu();  
        menu.showContextMenu();  
    }

	@Override
	public void onClick(ClickEvent event) {
		new MyDialog().draw();  
	}  
    
}
