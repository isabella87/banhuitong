package com.bht.banhuitong.client;

import com.bht.banhuitong.client.files.FileModulePortlet;
import com.bht.banhuitong.client.prj.CreditPrjPortlet;
import com.bht.banhuitong.client.prj.DatabaseModulePortlet;
import com.bht.banhuitong.client.prj.ExportDialog;
import com.bht.banhuitong.client.prj.MainPrjPortlet;
import com.bht.banhuitong.client.prj.MainPrjPortlet2;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
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

		LoginWindow.getInstance().init();
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
				for (String exce : result.split(";")) {
					String[] exceDesc = exce.split("=");
					if (exceDesc[0].matches("[0-9]+")) {
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
		
		window.setTitle(""); //window.setHeaderIcon("favicon.ico");
		 
		window.setHeight100();
		window.setWidth100();
		window.setAlign(Alignment.LEFT);
		window.setCanDrag(false);
		window.setCanDragReposition(false);
		window.setCanDragResize(false);
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
//		 registerCanvasMainClickEvent();
		window.draw();
	}

	/**
	 * 初始化menuBar
	 */
	private HLayout initMenuBar() {
		MenuItemSeparator separator = new MenuItemSeparator();

		Menu sysMenu = new Menu();

		IMenuItem exitMenuItem = new IMenuItem("登出系统", "Q");

		exitMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//				window.destroy();
				LoginWindow.getInstance().destroy();
				LoginWindow.getInstance().init();
			}

		});
		
		IMenuItem refreshMenuItem = new IMenuItem("刷新用户", "R");

		refreshMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				/**
				 * bug：非最佳解决方案。此项用来应对同一浏览器出现打开多个窗口登录不同用户，使得服务端只记录最后一个用户登录信息，
				 * 但浏览器客户端先登录但未关闭的浏览器右下角显示的用户名是之前用户而出现的bug（进行各种查询操作时用的是后一登录账户的信息，导致权限设置失效）
				 * 
				 * 此bug要求不高的系统，可不解决。可以通过权限大的用户他出重新登录规避。
				 * 
				 * 可考虑重构成:1，在调用所有方法前都重新构建一遍底端画布；
				 * 或者2,在调用任何非登录服务时，将当前页面登录时记录的登录名传入服务端，核对该用户是否还是登录状态。此时会直接要求用户重新登录；
				 */
				editEndCanvas();
			}

		});

		sysMenu.setItems(exitMenuItem,separator,refreshMenuItem);
		
		IMenuButton sysMenuButton = new IMenuButton("系统", sysMenu);

		Menu prjMenu = new Menu();

		IMenuItem prj1 = new IMenuItem("项目1", "1");

		prj1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				// window.addItem(new MainPrjPortlet().getInstance().getPortlet());
				changeMainCanvas(new MainPrjPortlet().getInstance().getPortlet());
			}

		});

		IMenuItem prj2 = new IMenuItem("项目2", "2");

		prj2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new MainPrjPortlet2().getInstance().getPortlet());
			}

		});

		IMenuItem prj3 = new IMenuItem("图形化展示", "3");
		prj3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new CreditPrjPortlet().getInstance().getPortlet());

			}

		});

		IMenuItem prj4 = new IMenuItem("数据库表", "4");
		prj4.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new DatabaseModulePortlet().getPortlet());

			}

		});

		prjMenu.setItems(prj1, prj2, separator, prj3, prj4);
		IMenuButton prjMenuButton = new IMenuButton("建模", prjMenu);

		// 文件系统
		Menu fileMenu = new Menu();
		IMenuItem fileMenuItem = new IMenuItem("文件系统", "F");
		fileMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new FileModulePortlet().getInstance().getPortlet());
			}
		});

		IMenuItem progressBarMenu = new IMenuItem("进度条测试", "J");
		progressBarMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				initProgressbar();
			}

		});

		fileMenu.setItems(fileMenuItem, separator, progressBarMenu);
		IMenuButton fileButtonMenu = new IMenuButton("文件", fileMenu);

		Menu winMenu = new Menu();
		IMenuItem stackUpWins = new IMenuItem("层叠", "S");
		stackUpWins.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToCardLayout();

			}

		});

		IMenuItem horizontalWins = new IMenuItem("水平平铺", "H");
		horizontalWins.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToHLayout();

			}

		});

		IMenuItem verticalWins = new IMenuItem("垂直平铺", "V");
		verticalWins.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvasToVLayout();

			}

		});

		IMenuItem rearrangeWins = new IMenuItem("重新排列", "S");
		rearrangeWins.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				returnInitMainCanvasToLayout();

			}

		});

		IMenuItem closeAllWins = new IMenuItem("关闭所有", "L");
		closeAllWins.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				closeAllPortlets();
			}

		});

		winMenu.setItems(stackUpWins, horizontalWins, verticalWins, rearrangeWins, separator, closeAllWins);

		IMenuButton winMenuButton = new IMenuButton("窗口", winMenu);

		Menu helpMenu = new Menu();

		IMenuItem aboutMenuItem = new IMenuItem("关于...", "A");
		aboutMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				new AboutSysWindow().init();
			}
		});

		IMenuItem printMenuItem = new IMenuItem("打印", "P");
		printMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if (portlets == null || portlets.isEmpty()) {
					SC.say("请打开数据查询列表，并查询打印数据！");
				}

				BasePortlet portlet = getTail(portlets).getValue();
				ListGrid listGrid = portlet.getRetListGrid();
				if (listGrid == null || listGrid.getRecordList() == null || listGrid.getRecordList().getLength() <= 1) {
					SC.say("请查询需要打印数据！");
				}
				Object[] objects = { listGrid };
				Canvas.showPrintPreview(objects, null, portlet.getTitle(), null);
			}
		});

		IMenuItem exportMenuItem = new IMenuItem("导出", "E");
		exportMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if (portlets == null || portlets.isEmpty()) {
					SC.say("请打开数据查询列表，并查询导出数据！");
				}

				BasePortlet portlet = getTail(portlets).getValue();
				ListGrid listGrid = portlet.getRetListGrid();
				if (listGrid == null || listGrid.getRecordList() == null || listGrid.getRecordList().getLength() <= 1) {
					SC.say("请查询需要导出数据！");
				}

				boolean isAll = false;
				if (portlet.getRetListGrid().getSelectedRecords().length <= 0) {
					isAll = true;
				}
				new ExportDialog(isAll, portlet.getRetListGrid());
			}
		});

		helpMenu.setItems(aboutMenuItem, separator, printMenuItem, exportMenuItem);
		IMenuButton helpMenuButton = new IMenuButton("帮助", helpMenu);

		menuLayout.setHeight(20);
		menuLayout.setLayoutAlign(Alignment.LEFT);

		menuLayout.addMembers(sysMenuButton, prjMenuButton, fileButtonMenu, winMenuButton, helpMenuButton);
		return menuLayout;
	}
}
