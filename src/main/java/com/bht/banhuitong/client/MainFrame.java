package com.bht.banhuitong.client;

import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class MainFrame extends BaseFrame implements EntryPoint {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	public static HLayout menuLayout = new HLayout();
	public static Window window = new Window();

	@Override
	public void onModuleLoad() {

		initMainFrame();
		
		SysMenuItem.getInstance().disableIMenuItem();
		
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

		window.addMember(initMenuBar());
		window.addItem(canvasMain);
		window.addItem(endCanvas);

		window.addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				new BaseFrame().closeAllPortlets();
			}

		});
		window.draw();
	}

	/**
	 * 初始化menuBar
	 */
	private HLayout initMenuBar() {

		menuLayout.setHeight(20);
		menuLayout.setLayoutAlign(Alignment.LEFT);

		menuLayout.addMembers(SysMenuItem.getInstance().getiMenuButtonListAsArray());
		return menuLayout;
	}

}
