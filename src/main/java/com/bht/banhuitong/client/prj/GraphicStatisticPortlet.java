package com.bht.banhuitong.client.prj;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BasePortlet;
import com.bht.banhuitong.client.MainFrame;
import com.bht.banhuitong.server.AccountService;
import com.bht.banhuitong.server.AccountServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class GraphicStatisticPortlet extends BasePortlet {
	public static String portletTitleName = "数据分析 -报表分析";
	private GraphicStatisticPortlet portletInstance;
	private final AccountServiceAsync accountService = GWT.create(AccountService.class);
	
	public GraphicStatisticPortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new GraphicStatisticPortlet();
		}
		return portletInstance;
	}

	public GraphicStatisticPortlet() {
		super(portletTitleName);
	}

	@Override
	public void init() {

		IButton searchDataButton = new IButton("搜索");  
        searchDataButton.setLeft(0);  
        
        IButton loadReportButton = new IButton("加载报表");  
        loadReportButton.setLeft(0);  
        
        final ComboBoxItem timeTypeItem = new ComboBoxItem("dateTime"); 
        timeTypeItem.setTitle("时间类型");
        timeTypeItem.setDefaultToFirstOption(true);
        
        final DateItem startDateItem = new DateItem("startDate"); 
        startDateItem.setTitle("开始时间");
        startDateItem.setDefaultValue(addDay(new Date(), -365));
        
        final DateItem endDateItem = new DateItem("endDate");  
        endDateItem.setTitle("结束时间");
        
        final ComboBoxItem prjTypeItem = new ComboBoxItem("type");
        prjTypeItem.setDefaultToFirstOption(true);
        prjTypeItem.setTitle("项目类型"); 
        
        final ComboBoxItem prjStatusItem = new ComboBoxItem("status"); 
        prjStatusItem.setDefaultToFirstOption(true);
        prjStatusItem.setTitle("项目状态"); 
        
        final ComboBoxItem searchKeyTypeItem = new ComboBoxItem("searchKeyType"); 
        searchKeyTypeItem.setTitle("关键字类型");
        searchKeyTypeItem.setDefaultToFirstOption(true);
        
        HLayout searchPanel= new HLayout();
        searchPanel.setWidth(MainFrame.window.getWidth()-12);
        searchPanel.setHeight(80);
        
		final TextItem searchKeyItem = new TextItem();
		searchKeyItem.setTitle("关键字");
		
		/*ScrollPanel sPanel = new ScrollPanel();
		sPanel.setHeight("68px");
		sPanel.setWidth("700px");
		sPanel.setAlwaysShowScrollBars(true);
		Canvas scrollCanvas = new Canvas();
		scrollCanvas.setWidth100();
		scrollCanvas.setHeight(60);
		scrollCanvas.addChild(sPanel);
		*/
		DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(1200);
		searchForm.setHeight(50);
//		searchForm.setWidth(searchForm.getParent().getOffsetWidth());
		searchForm.setNumCols(8);
		searchForm.setFields(timeTypeItem,startDateItem,endDateItem,prjTypeItem,prjStatusItem,searchKeyTypeItem,searchKeyItem);
		
		searchPanel.addMembers(searchForm,searchDataButton,loadReportButton);
//		sPanel.add(searchPanel);
		
        this.addMember(searchPanel);
        //    
//		final String url = GWT.getHostPageBaseURL() + "frameset?__report=report.rptdesign&__showtitle=false&__title=loan amt&__toolbar=false";
		
		final Frame frame = new Frame();
		frame.setStyleName("birtFrame");  //此种样式设置不起作用
		frame.setSize("99.8%", "99%");   //此处设置具体数值不起作用，只能设置比例
		
		searchDataButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Map<String,String> paramMap = new HashMap<String,String>();
				
				accountService.exportDataToExcel(paramMap, new AsyncCallback<List<Map<String, String>>>(){

					@Override
					public void onFailure(Throwable caught) {
						showErrorMessage(caught.getMessage());
					}

					@Override
					public void onSuccess(List<Map<String, String>> result) {
						if(result == null ||result.isEmpty() ) {
							SC.say("没有符合条件的数据！！！");
						}else {
							SC.say("数据准备就绪：共"+result.size()+"条！");
						}
						/*InvestorInfo investorInfo = new InvestorInfo();
						investorInfo.setList(result);
//						String strData = "" ;
						StringBuffer sb = new StringBuffer();
						for(Map<String,String> map: result) {
							for(String key:map.keySet()) {
								sb.append(key).append("=").append(map.get(key)).append(",");
							}
							sb.append("},");
						}
						DataFactory.strData=sb.toString();*/
						
//						List<InvestorInfo> list = new DataFactory().getLazyInstance().getInvestorSumAmtInfoList(investorInfo.getList());
						
//						SC.say("size="+list.size()+",result[1].get(\"NAME\")="+list.get(1).getName()+",result[1].get(\"DATEPOINT\")="+list.get(1).getDatepoint());
						// TODO GWT 与js 交互，将数据再页面加载前存入页面
						
//						SC.say("数据准备就绪：共"+new DataFactory().getLazyInstance().getInvestorSumAmtInfoList().size()+"条！");
						
					}
				});
			}
		});
		
		loadReportButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				final String url = GWT.getHostPageBaseURL() + "frameset?__report=luban_yl.rptdesign&__showtitle=false&__title=investorSumAmt&__toolbar=false";

				frame.setUrl(url);
			}
			
		});
		
		this.addItem(frame);
	}
}
