package com.bht.banhuitong.client.prj;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.BasePortlet;
import com.bht.banhuitong.client.MainFrame;
import com.bht.banhuitong.server.PrjService;
import com.bht.banhuitong.server.PrjServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MainPrjPortlet2 extends BasePortlet{

	private int hBarValue = 0;
	
	private final PrjServiceAsync prjService = GWT.create(PrjService.class);
	private static Map<String,String> prjFieldItems = new LinkedHashMap<String,String>();
	
	private static LinkedHashMap<String, String> prjTypeItems = new LinkedHashMap<String, String>();  
	private static LinkedHashMap<String, String> prjStatusItems = new LinkedHashMap<String, String>();  
	private static LinkedHashMap<String, String> prjSignStatusItems = new LinkedHashMap<String, String>();  
	private static LinkedHashMap<String, String> timeTypeItems = new LinkedHashMap<String, String>();  
	private static LinkedHashMap<String, String> searchKeyItems = new LinkedHashMap<String, String>();  
	public static String portletTitleName = "系统建模 -项目2";
	private MainPrjPortlet2 portletInstance;
	public  MainPrjPortlet2 getInstance() {
		if(portletInstance==null) {
			portletInstance = new MainPrjPortlet2();
		}
		return portletInstance;
	}
     /**
	 * items key值与服务返回map中的key一一对应
	 */
	static {
		prjFieldItems.put("P_ID", "PID");
		prjFieldItems.put("TYPE", "类型");
		prjFieldItems.put("ITEM_NO", "编号");
		prjFieldItems.put("ITEM_NAME", "名称");
		prjFieldItems.put("STATUS", "状态");
		prjFieldItems.put("SIGN_STATUS", "签章状态");
		prjFieldItems.put("AMT", "金额");
		prjFieldItems.put("INVESTED_AMT", "已募集金额");
		prjFieldItems.put("BORROW_DAYS", "借款天数");
		prjFieldItems.put("RATE", "年化收益");
		prjFieldItems.put("COST_FEE", "买入费率");
		prjFieldItems.put("SOLD_FEE", "卖出费率");
		prjFieldItems.put("TOTAL_INTEREST", "总利息");
		prjFieldItems.put("IN_PROXY", "融资项目经理");
		prjFieldItems.put("FINANCIER", "融资方");
		prjFieldItems.put("ON_LINE_TIME", "上线时间");
		prjFieldItems.put("LOAN_TIME", "放款时间");
		prjFieldItems.put("CAPITAL_REPAY_TIME", "预计还本时间");
		
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
	public MainPrjPortlet2() {
		super(portletTitleName);
	}
	
	public void init() {
		final ListGrid countryGrid = new ListGrid();  
		countryGrid.setHeight100(); 
        countryGrid.setWidth100();  
        countryGrid.setTop(50);  
        countryGrid.setLeft(5);
        countryGrid.setCanSelectCells(true);
        countryGrid.setShowAllRecords(true);  
        countryGrid.setShowEmptyMessage(true);  
        countryGrid.setCanDragSelect(true);
        countryGrid.setEmptyMessage("请点击<b>搜索</b>按钮查询数据！");  
        countryGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		
        ListGridField idField = new ListGridField("id", "序号", 50);  
        ListGridField pIdField = new ListGridField("P_ID", "PID", 50);  
        ListGridField typeField = new ListGridField("TYPE", "类型");  
        ListGridField itemNoField = new ListGridField("ITEM_NO", "编号");  
        ListGridField itemNameField = new ListGridField("ITEM_NAME", "名称");  
        ListGridField statusField = new ListGridField("STATUS", "状态");  
        ListGridField signStatusField = new ListGridField("SIGN_STATUS", "签章状态");  
        ListGridField amtField = new ListGridField("AMT", "金额");  
        ListGridField investedAmtField = new ListGridField("INVESTED_AMT", "已募集金额");  
        ListGridField borrowDaysField = new ListGridField("BORROW_DAYS", "借款天数");  
        ListGridField rateField = new ListGridField("RATE", "年化收益");  
        ListGridField costFeeField = new ListGridField("COST_FEE", "买入费率");  
        ListGridField soldFeeField = new ListGridField("SOLD_FEE", "卖出费率");  
        ListGridField totalInterestField = new ListGridField("TOTAL_INTEREST", "总利息");  
        ListGridField inProxyField = new ListGridField("IN_PROXY", "融资项目经理");  
        ListGridField financierField = new ListGridField("FINANCIER", "融资方");  
        ListGridField onLineTimeField = new ListGridField("ON_LINE_TIME", "上线时间");  
        ListGridField loanTimeField = new ListGridField("LOAN_TIME", "放款时间");  
        ListGridField capitalRepayTimeField = new ListGridField("CAPITAL_REPAY_TIME", "预计还本时间");  

        countryGrid.setFields(idField,pIdField, typeField, itemNoField, itemNameField,statusField,signStatusField,
        		amtField,investedAmtField,borrowDaysField,rateField,costFeeField,soldFeeField,totalInterestField,
        		inProxyField,financierField,onLineTimeField,loanTimeField,capitalRepayTimeField);  
  
        IButton searchDataButton = new IButton("搜索");  
        searchDataButton.setLeft(0);  
        
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
        prjStatusItem.setDefaultToFirstOption(true);
        prjStatusItem.setTitle("项目状态"); 
        prjStatusItem.setValueMap(prjStatusItems);
        
        final ComboBoxItem searchKeyTypeItem = new ComboBoxItem("searchKeyType"); 
        searchKeyTypeItem.setTitle("关键字类型");
        searchKeyTypeItem.setDefaultToFirstOption(true);
        searchKeyTypeItem.setValueMap(searchKeyItems);
        
        HLayout searchPanel= new HLayout();
        searchPanel.setWidth(MainFrame.window.getWidth()-12);
        searchPanel.setHeight(80);
        
		final TextItem searchKeyItem = new TextItem();
		searchKeyItem.setTitle("关键字");
		
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.setHeight("68px");
		sPanel.setWidth("700px");
		sPanel.setAlwaysShowScrollBars(true);
		Canvas scrollCanvas = new Canvas();
		scrollCanvas.setWidth100();
		scrollCanvas.setHeight(60);
		scrollCanvas.addChild(sPanel);
		
		DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(1200);
		searchForm.setHeight(50);
//		searchForm.setWidth(searchForm.getParent().getOffsetWidth());
		searchForm.setNumCols(8);
		searchForm.setFields(timeTypeItem,startDateItem,endDateItem,prjTypeItem,prjStatusItem,searchKeyTypeItem,searchKeyItem);
		
		searchPanel.addMembers(searchForm,searchDataButton);
		sPanel.add(searchPanel);
		
        this.addMember(searchPanel);
        this.addItem(countryGrid);
		
		searchDataButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	final Map<String,String> paramMap = new HashMap<String,String>();
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
							
			            	prjService.queryPrjList2(paramMap, new AsyncCallback<List<Map<String,String>>>() {
			
								@Override
								public void onFailure(Throwable caught) {
									showErrorMessage(caught.getMessage());
									hBarValue = 100; 
								}
			

								@Override
								public void onSuccess(List<Map<String, String>> result) {
									if (result == null || result.isEmpty()) {
										countryGrid.setData(new ListGridRecord[] {});
										SC.say("没有符合条件的数据！");
									} else {
										Object rpcExceptionMessage = result.get(0).get("error");
										if (rpcExceptionMessage != null) {
											SC.say(rpcExceptionMessage.toString());
										} else {
											countryGrid.setData(new ListGridRecord[] {});
											countryGrid.setData(getRecords(result));
											retListGrid = countryGrid;
											paramMapOfRetListGrid = paramMap;
										}
									}
									hBarValue=100;
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
		                	BaseFrame.headerHBarLabel.setContents("就绪");
		                	BaseFrame.hBar.destroy();
		                	BaseFrame.hBarLabel.destroy();
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
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public  ListGridRecord[] getRecords(List<Map<String, String>> result) {
		int length = result.size();
		ListGridRecord[] listRecords = new ListGridRecord[length];
		for(int i = 0;i<result.size();i++) {
			Map<String, String> mapItem = result.get(i);
			listRecords[i]= createRecord(mapItem,i);
		}
		
		return listRecords;
	}

	public ListGridRecord createRecord(Map<String, String> mapItem,int i) {
		ListGridRecord record = new ListGridRecord();  
		record.setAttribute("id", ++i);
		for(String key:prjFieldItems.keySet()) {
			if(mapItem.get(key)==null||mapItem.get(key).toString().isEmpty()) {
				continue ;
			}
			if(key.equals("TYPE")) {
				record.setAttribute(key, prjTypeItems.get(mapItem.get(key))); 
			}else if(key.equals("STATUS")){
				record.setAttribute(key, prjStatusItems.get(mapItem.get(key))); 
			}else if(key.equals("SIGN_STATUS")) {
				record.setAttribute(key, prjSignStatusItems.get(mapItem.get(key))); 
			}else if(key.equals("onLineTime")||key.equals("capitalRepayTime")||key.equals("loanTime")) {
				record.setAttribute(key,formatDateStr(mapItem.get(key),"yyyy-MM-dd") ); 
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
}
