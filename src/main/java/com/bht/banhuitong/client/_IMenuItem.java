package com.bht.banhuitong.client;

import java.util.ArrayList;
import java.util.List;

import com.bht.banhuitong.client.prj.ExportDialog;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * 本系统凡是以下划线开头的组件都是自定义重组的组件
 * 
 * @author Administrator
 *
 */
public class _IMenuItem extends MenuItem {

	public _IMenuItem(String title, String keyTitle) {
		super();
		this.setTitle(title);
		this.setKeyTitle("Alt+" + keyTitle);
		this.setKeys(getKeyIdentifier(keyTitle));

	}

	public void _registerClickHandler(final Object object) {
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if (object instanceof BasePortlet) {
					BasePortlet obj = (BasePortlet) object;
					new BaseFrame().changeMainCanvas(obj.getPortlet());
				} else if (object.toString().equals("XY")) {
					new BaseFrame().changeMainCanvasToCardLayout();
				} else if (object.toString().equals("H")) {
					new BaseFrame().changeMainCanvasToHLayout();
				} else if (object.toString().equals("V")) {
					new BaseFrame().changeMainCanvasToVLayout();
				} else if (object.toString().equals("S")) {
					new BaseFrame().returnInitMainCanvasToLayout();
				} else if (object.toString().equals("L")) {
					new BaseFrame().closeAllPortlets();
				} else if (object.toString().equals("EXPORT")) {
					export();
				} else if (object.toString().equals("PRINT")) {
					print();
				} else if (object.toString().equals("ABOUT")) {
					new AboutSysWindow().init();
				} else if (object.toString().equals("PROGRESSBAR")) {
					new BaseFrame().initProgressbar();
				} else if (object.toString().equals("REFRESH")) {
					/**
					 * bug：非最佳解决方案。此项用来应对同一浏览器出现打开多个窗口登录不同用户，使得服务端只记录最后一个用户登录信息，
					 * 但浏览器客户端先登录但未关闭的浏览器右下角显示的用户名是之前用户而出现的bug（进行各种查询操作时用的是后一登录账户的信息，导致权限设置失效）
					 * 
					 * 此bug要求不高的系统，可不解决。可以通过权限大的用户他出重新登录规避。
					 * 
					 * 可考虑重构成:1，在调用所有方法前都重新构建一遍底端画布；
					 * 或者2,在调用任何非登录服务时，将当前页面登录时记录的登录名传入服务端，核对该用户是否还是登录状态。此时会直接要求用户重新登录；
					 */
					BaseFrame.editEndCanvas();
				} else if (object.toString().equals("RE-LOGIN")) {
					exitToLoginWin();
				}

			}

		});
	}

	protected void exitToLoginWin() {
		final LoginServiceAsync loginService = GWT.create(LoginService.class);

		loginService.loginOut(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				new BasePortlet("").showErrorMessage(caught.getMessage());

			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					SysMenuItem.getInstance().disableIMenuItem();
					LoginWindow.getInstance().destroy();
					LoginWindow.getInstance().init();
				} else {
					SC.say("登出失败！");
				}
			}
		});

	}

	protected void print() {
		if (BaseFrame.portlets == null || BaseFrame.portlets.isEmpty()) {
			SC.say("请打开数据查询列表，并查询打印数据！");
			return;
		}

		BasePortlet portlet = BaseFrame.getTail(BaseFrame.portlets).getValue();
		ListGrid listGrid = portlet.getRetListGrid();
		if (listGrid == null || listGrid.getRecordList() == null || listGrid.getRecordList().getLength() < 1) {
			SC.say("请查询需要打印数据！");
			return;
		}
		Object[] objects = { listGrid };
		Canvas.showPrintPreview(objects, null, portlet.getTitle(), null);

	}

	private KeyIdentifier[] getKeyIdentifier(String keyChar) {
		List<KeyIdentifier> keyIdentifierList = new ArrayList<KeyIdentifier>();
		for (char c : keyChar.toCharArray()) {
			KeyIdentifier key = new KeyIdentifier();
			key.setKeyName(String.valueOf(c));
			key.setAltKey(true);

			keyIdentifierList.add(key);
		}

		return keyIdentifierList.toArray(new KeyIdentifier[keyIdentifierList.size()]);
	}

	private void export() {

		if (BaseFrame.portlets == null || BaseFrame.portlets.isEmpty()) {
			SC.say("请打开数据查询列表，并查询导出数据！");
			return;
		}

		BasePortlet portlet = BaseFrame.getTail(BaseFrame.portlets).getValue();
		ListGrid listGrid = portlet.getRetListGrid();
		if (listGrid == null || listGrid.getRecordList() == null || listGrid.getRecordList().getLength() < 1) {
			SC.say("请查询需要导出数据！");
			return;
		}

		boolean isAll = false;
		if (portlet.getRetListGrid().getSelectedRecords().length <= 0) {
			isAll = true;
		}
		new ExportDialog(isAll, portlet.getRetListGrid());

	}
}
