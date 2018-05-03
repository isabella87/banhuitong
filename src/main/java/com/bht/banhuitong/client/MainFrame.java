package com.bht.banhuitong.client;

import com.bht.banhuitong.client.files.FileModulePortlet;
import com.bht.banhuitong.client.prj.ClientExportDialog;
import com.bht.banhuitong.client.prj.CreditPrjPortlet;
import com.bht.banhuitong.client.prj.DatabaseModulePortlet;
import com.bht.banhuitong.client.prj.ExportDialog;
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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
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
		window.setTitle("");
		window.setHeight100();
		window.setWidth100();
		// window.setMinWidth(width);
		// window.setMinHeight(height);
		// window.setWidth(width);
		// window.setHeight100();
		window.setAlign(Alignment.LEFT);
		// window.setAutoSize(true);
		window.setCanDragReposition(false);
		// window.setCanDragResize(true);
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
		// registerCanvasMainClickEvent();
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
			}
		});

		sysMenu.setItems(exitMenuItem);
		IMenuButton sysMenuButton = new IMenuButton("退出", sysMenu);

		Menu prjMenu = new Menu();
		MenuItem prj1 = new MenuItem("项目1", "", "Ctrl+F");
		prj1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				// window.addItem(new MainPrjPortlet().getInstance().getPortlet());
				changeMainCanvas(new MainPrjPortlet().getInstance().getPortlet());
			}

		});

		MenuItem prj2 = new MenuItem("项目2", "", "Ctrl+C");
		prj2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				// window.addItem(new MainPrjPortlet2().getInstance().getPortlet());
				changeMainCanvas(new MainPrjPortlet2().getInstance().getPortlet());
			}

		});

		MenuItem prj3 = new MenuItem("图形化展示", "", "Ctrl+A");
		prj3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new CreditPrjPortlet().getInstance().getPortlet());

			}

		});

		MenuItem prj4 = new MenuItem("数据库表", "", "Ctrl+B");
		prj4.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new DatabaseModulePortlet().getPortlet());

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
		prjMenu.setItems(prj1, prj2, separator, prj3, prj4, separator, progressBarMenu);
		IMenuButton prjMenuButton = new IMenuButton("系统建模", prjMenu);

		Menu baseMenu = new Menu();
		MenuItem base1 = new MenuItem("担保人", "", "Ctrl+A");
		base1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new GuaranteePersonPortlet().getPortlet());

			}

		});
		MenuItem base2 = new MenuItem("担保机构", "", "Ctrl+G");
		base2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new GuaranteeOrgPortlet().getPortlet());

			}

		});

		MenuItem base3 = new MenuItem("借款人", "", "Ctrl+P");
		MenuItem base4 = new MenuItem("借款机构", "", "Ctrl+O");
		baseMenu.setItems(base1, base2, separator, base3, base4);
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
		clientMenu.setItems(client1, client2, separator, client3, client4);
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

		winMenu.setItems(win1, win2, win3, win4, win5);
		IMenuButton winMenuButton = new IMenuButton("窗口", winMenu);

		//文件系统
		Menu fileMenu = new Menu();
		MenuItem fileMenuItem = new MenuItem("文件系统", "", "Ctrl+F");
		fileMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				changeMainCanvas(new FileModulePortlet().getInstance().getPortlet());
			}
		});
		
		fileMenu.setItems(fileMenuItem);
		IMenuButton fileButtonMenu = new IMenuButton("文件",fileMenu);
		
		Menu helpMenu = new Menu();

		MenuItem aboutMenuItem = new MenuItem("关于...", "", "Ctrl+A");
		aboutMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				new AboutSysWindow().init();
			}
		});

		MenuItem printMenuItem = new MenuItem("打印", "", "Ctrl+P");
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

		MenuItem exportMenuItem = new MenuItem("导出全部", "", "Ctrl+E");
		exportMenuItem.addClickHandler(new ClickHandler() {

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

				boolean isAll = false;
				if (portlet.getRetListGrid().getSelectedRecords().length <= 0) {
					isAll = true;
				}
				new ExportDialog(isAll, portlet.getRetListGrid()).draw();
			}
		});
		
		MenuItem clientExportMenuItem = new MenuItem("客户端导出", "", "Ctrl+C");
		clientExportMenuItem.addClickHandler(new ClickHandler() {

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

				boolean isAll = false;
				if (portlet.getRetListGrid().getSelectedRecords().length <= 0) {
					isAll = true;
				}
				new ClientExportDialog(isAll, portlet.getRetListGrid()).draw();
			}
		});

		helpMenu.setItems(aboutMenuItem, separator, printMenuItem, exportMenuItem,clientExportMenuItem);
		IMenuButton helpMenuButton = new IMenuButton("帮助", helpMenu);
		
		menuLayout.setHeight(20);
		menuLayout.setLayoutAlign(Alignment.LEFT);

		menuLayout.addMembers(sysMenuButton, prjMenuButton, fileButtonMenu, winMenuButton, helpMenuButton);
		return menuLayout;
	}
}
