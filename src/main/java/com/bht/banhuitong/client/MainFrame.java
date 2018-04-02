package com.bht.banhuitong.client;

import com.bht.banhuitong.client.prj.CreditPrjPortlet;
import com.bht.banhuitong.client.prj.DatabaseModulePortlet;
import com.bht.banhuitong.client.prj.GuaranteeOrgPortlet;
import com.bht.banhuitong.client.prj.GuaranteePersonPortlet;
import com.bht.banhuitong.client.prj.MainPrjPortlet;
import com.bht.banhuitong.client.prj.MainPrjPortlet2;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class MainFrame extends BaseFrame implements EntryPoint {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
	public static HLayout menuLayout = new HLayout();
	public static Window window = new Window();

	@Override
	public void onModuleLoad() {

		initMainFrame();

		initExceptionStr();
		
		new LoginWindow().init();
	}

	/**
	 * 初始化异常描述信息
	 */
	private void initExceptionStr() {
		loginService.getExcepStr(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("初始化异常！");
				
			}

			@Override
			public void onSuccess(String result) {
				for(String exce:result.split(";")) {
					String[] exceDesc= exce.split("=");
					if(exceDesc[0].matches("[0-9]+")) {
						exceMap.put(Integer.valueOf(exceDesc[0]), exceDesc[1]);
					}
				}
			}
			
		});
		
	}

	/**
	 * 初始化主窗口
	 */
	private void initMainFrame() {
		window.setTitle("");
		window.setHeight100();
		window.setWidth100();
//		window.setMinWidth(width);
//		window.setMinHeight(height);
//		window.setWidth(width);
//		window.setHeight100();
		window.setAlign(Alignment.LEFT);
//		 window.setAutoSize(true);
		 window.setCanDragReposition(false);
//		 window.setCanDragResize(true);
		window.addMember(initMenuBar()); 
		window.setAlwaysShowScrollbars(false);
		window.setCanDragScroll(false);
		window.setDisableTouchScrollingForDrag(false);
		window.setMinimized(false);
		window.setShowCloseButton(false);
		window.setSnapResizeToGrid(true);
        canvasMain.setTop(10);
        canvasMain.setHeight100();
        canvasMain.setSnapResizeToAlign(true);
        canvasMain.setBackgroundColor("#99ccff");
        
        endCanvas.setBottom(0);
        endCanvas.setHeight("25");
        endCanvas.setBackgroundColor("#DDDDDD");
        
        loginNameLabel.setHeight(25);
        loginNameLabel.setLeft("90%");
        endCanvas.addChild(loginNameLabel);
        
       window.addItem(canvasMain);
       window.addItem(endCanvas);
       
       window.addCloseClickHandler(new CloseClickHandler() {

		@Override
		public void onCloseClick(CloseClickEvent event) {
			new BaseFrame().closeAllPortlets();
		}
    	   
       });
//       registerCanvasMainClickEvent();
       window.draw();
	}
	

	/**
	 * 初始化menuBar
	 */
	private HLayout initMenuBar() {
		Menu sysMenu = new Menu();
		
		MenuItem exitMenuItem = new MenuItem("退出系统", "", "Ctrl+Q");
		exitMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				window.destroy();
			}});
		
		sysMenu.setItems(exitMenuItem);
		IMenuButton sysMenuButton = new IMenuButton("系统", sysMenu);
		
		
		Menu prjMenu = new Menu();
		MenuItem prj1 = new MenuItem("项目1", "", "Ctrl+F");
		prj1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//				window.addItem(new MainPrjPortlet().getInstance().getPortlet());
				 changeMainCanvas(MainPrjPortlet.class,new MainPrjPortlet().getInstance().getPortlet());
			}
			
		});
		
		MenuItem prj2 = new MenuItem("项目2", "", "Ctrl+C");
		prj2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//				window.addItem(new MainPrjPortlet2().getInstance().getPortlet());
				changeMainCanvas(MainPrjPortlet2.class,new MainPrjPortlet2().getInstance().getPortlet());
			}
			
		});
		
		MenuItem prj3 = new MenuItem("图形化展示", "", "Ctrl+A");
		prj3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				 changeMainCanvas(CreditPrjPortlet.class,new CreditPrjPortlet().getInstance().getPortlet());
				
			}
			
		});
		
		MenuItem prj4 = new MenuItem("数据库表", "", "Ctrl+B");
		prj4.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				 changeMainCanvas(DatabaseModulePortlet.class,new DatabaseModulePortlet().getPortlet());
				
			}
			
		});
		
		MenuItem progressBarMenu = new MenuItem("进度条测试", "", "Ctrl+P");
		progressBarMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				initProgressbar();
			}
			
		});
		
		
		MenuItemSeparator separator = new MenuItemSeparator();
		prjMenu.setItems( prj1,prj2, separator, prj3, prj4,separator,progressBarMenu);
		IMenuButton prjMenuButton = new IMenuButton("系统建模", prjMenu);
		
		Menu baseMenu = new Menu();
		MenuItem base1 = new MenuItem("担保人", "", "Ctrl+A");
		base1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				 changeMainCanvas(GuaranteePersonPortlet.class,new GuaranteePersonPortlet().getPortlet());
				
			}
			
		});
		MenuItem base2 = new MenuItem("担保机构", "", "Ctrl+G");
		base2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				 changeMainCanvas(GuaranteeOrgPortlet.class,new GuaranteeOrgPortlet().getPortlet());
				
			}
			
		});
		
		MenuItem base3 = new MenuItem("借款人", "", "Ctrl+P");
		MenuItem base4 = new MenuItem("借款机构", "", "Ctrl+O");
		baseMenu.setItems(base1, base2, separator, base3,base4);
		IMenuButton baseMenuButton = new IMenuButton("基础数据", baseMenu);
		
		Menu accMenu = new Menu();
		MenuItem acc1 = new MenuItem("个人", "", "Ctrl+P");
		MenuItem acc2 = new MenuItem("机构", "", "Ctrl+O");
		MenuItem acc3 = new MenuItem("商户转账", "", "Ctrl+T");
		accMenu.setItems(acc1, acc2, separator, acc3);
		IMenuButton accMenuButton = new IMenuButton("账户管理", accMenu);

		Menu clientMenu = new Menu();
		MenuItem client1 = new MenuItem("分配跟进客户", "", "Ctrl+A");
		MenuItem client2 = new MenuItem("我的跟进客户", "", "Ctrl+C");
		MenuItem client3 = new MenuItem("分配注册客户", "", "Ctrl+R");
		MenuItem client4 = new MenuItem("我的注册客户", "", "Ctrl+B");
		clientMenu.setItems(client1, client2, separator, client3,client4);
		IMenuButton clientMenuButton = new IMenuButton("客户关系管理", clientMenu);
		
		Menu winMenu = new Menu();
		MenuItem win1 = new MenuItem("层叠", "", "Ctrl+C");
		win1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToCardLayout();
				
			}
			
		});
		
		MenuItem win2 = new MenuItem("水平平铺", "", "Ctrl+H");
		win2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToHLayout();
				
			}
			
		});
		
		MenuItem win3 = new MenuItem("垂直平铺", "", "Ctrl+V");
		win3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToVLayout();
				
			}
			
		});
		
		MenuItem win4 = new MenuItem("重新排列", "", "Ctrl+A");
		win4.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				returnInitMainCanvasToLayout();
				
			}
			
		});
		
		MenuItem win5 = new MenuItem("关闭所有", "", "Ctrl+L");
		win5.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				closeAllPortlets();
			}
			
		});
		
		winMenu.setItems(win1, win2, win3, win4,win5);
		IMenuButton winMenuButton = new IMenuButton("窗口", winMenu);
		
		final Img printerImg = new Img("printer.png");
//		printerImg.setRight(0);
		printerImg.setWidth(20);
		printerImg.setHeight(20);
		
		printerImg.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				printerImg.setTitle("单击打印");
			}
			
		});
		
//		Label spaceLable = new Label();
//		spaceLable.setWidth(width-600);
		
		menuLayout.setHeight(20);
		menuLayout.setLayoutAlign(Alignment.LEFT);

		
//		menuLayout.addMembers(prjMenuButton,baseMenuButton, accMenuButton,clientMenuButton,winMenuButton);
		menuLayout.addMembers(sysMenuButton,prjMenuButton,winMenuButton);
//		menuLayout.addMembers(prjMenuButton,baseMenuButton, accMenuButton,clientMenuButton,winMenuButton,spaceLable,printerImg);
//		window.addMembers(prjMenuButton,baseMenuButton, accMenuButton,clientMenuButton,winMenuButton,printerImg);
		return menuLayout;
	}

}
