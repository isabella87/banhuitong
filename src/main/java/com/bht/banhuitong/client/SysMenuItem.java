package com.bht.banhuitong.client;

import java.util.ArrayList;
import java.util.List;

import com.bht.banhuitong.client.files.FileModulePortlet;
import com.bht.banhuitong.client.prj.ExcavateXmlDataPortlet;
import com.bht.banhuitong.client.prj.DatabaseModulePortlet;
import com.bht.banhuitong.client.prj.MainPrjPortlet;
import com.bht.banhuitong.client.prj.MainPrjPortlet2;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;

public class SysMenuItem {

	private static SysMenuItem instance;

	public static SysMenuItem getInstance() {
		if (instance == null) {
			instance = new SysMenuItem();
		}
		return instance;
	}

	private List<IMenuButton> iMenuButtonList = new ArrayList<IMenuButton>();
	private _IMenuItem exitMenuItem = new _IMenuItem("登出系统", "Q");
	private _IMenuItem refreshMenuItem = new _IMenuItem("刷新用户", "R");

	private _IMenuItem prj1 = new _IMenuItem("项目1", "1");
	private _IMenuItem prj2 = new _IMenuItem("项目2", "2");
	private _IMenuItem prj3 = new _IMenuItem("图形化展示", "3");
	private _IMenuItem prj4 = new _IMenuItem("数据库表", "4");
	
	private _IMenuItem fileMenuItem = new _IMenuItem("文件系统", "F");
	private _IMenuItem progressBarMenu = new _IMenuItem("进度条测试", "J");

	private _IMenuItem cascadeWins = new _IMenuItem("层叠", "XY");
	private _IMenuItem horizontalWins = new _IMenuItem("水平平铺", "H");
	private _IMenuItem verticalWins = new _IMenuItem("垂直平铺", "V");
	private _IMenuItem rearrangeWins = new _IMenuItem("重新排列", "S");
	private _IMenuItem closeAllWins = new _IMenuItem("关闭所有", "L");

	private _IMenuItem aboutMenuItem = new _IMenuItem("关于...", "A");
	private _IMenuItem printMenuItem = new _IMenuItem("打印", "P");
	private _IMenuItem exportMenuItem = new _IMenuItem("导出", "E");

	public SysMenuItem() {
		init();
		registerClickHandlers();

	}

	public void disableIMenuItem() {
		exitMenuItem.setEnabled(false);
		refreshMenuItem.setEnabled(false);

		prj1.setEnabled(false);
		prj2.setEnabled(false);
		prj3.setEnabled(false);
		prj4.setEnabled(false);

		fileMenuItem.setEnabled(false);
		progressBarMenu.setEnabled(false);

		cascadeWins.setEnabled(false);
		horizontalWins.setEnabled(false);
		verticalWins.setEnabled(false);
		rearrangeWins.setEnabled(false);
		closeAllWins.setEnabled(false);

		aboutMenuItem.setEnabled(false);
		printMenuItem.setEnabled(false);
		exportMenuItem.setEnabled(false);
	}

	public void enableIMenuItem() {
		exitMenuItem.setEnabled(true);
		refreshMenuItem.setEnabled(true);

		prj1.setEnabled(true);
		prj2.setEnabled(true);
		prj3.setEnabled(true);
		prj4.setEnabled(true);

		fileMenuItem.setEnabled(true);
		progressBarMenu.setEnabled(true);

		cascadeWins.setEnabled(true);
		horizontalWins.setEnabled(true);
		verticalWins.setEnabled(true);
		rearrangeWins.setEnabled(true);
		closeAllWins.setEnabled(true);

		aboutMenuItem.setEnabled(true);
		printMenuItem.setEnabled(true);
		exportMenuItem.setEnabled(true);
	}

	public IMenuButton[] getiMenuButtonListAsArray() {
		return iMenuButtonList.toArray(new IMenuButton[iMenuButtonList.size()]);
	}

	private void init() {
		MenuItemSeparator separator = new MenuItemSeparator();

		Menu sysMenu = new Menu();
		sysMenu.setItems(exitMenuItem, separator, refreshMenuItem);
		
		iMenuButtonList.add(new IMenuButton("系统(S)", sysMenu));

		Menu prjMenu = new Menu();
		prjMenu.setItems(prj1, prj2, separator, prj3, prj4);
		iMenuButtonList.add(new IMenuButton("建模", prjMenu));

		Menu fileMenu = new Menu();
		fileMenu.setItems(fileMenuItem, separator, progressBarMenu);
		iMenuButtonList.add(new IMenuButton("文件", fileMenu));

		Menu winMenu = new Menu();
		winMenu.setItems(cascadeWins, horizontalWins, verticalWins, rearrangeWins, separator, closeAllWins);
		iMenuButtonList.add(new IMenuButton("窗口", winMenu));

		Menu helpMenu = new Menu();
		helpMenu.setItems(aboutMenuItem, separator, printMenuItem, exportMenuItem);
		iMenuButtonList.add(new IMenuButton("帮助", helpMenu));

	}

	public void registerClickHandlers() {
		exitMenuItem._registerClickHandler("RE-LOGIN");
		refreshMenuItem._registerClickHandler("REFRESH");

		prj1._registerClickHandler(new MainPrjPortlet().getInstance().getPortlet());
		prj2._registerClickHandler(new MainPrjPortlet2().getInstance().getPortlet());
		prj3._registerClickHandler(new ExcavateXmlDataPortlet().getInstance().getPortlet());
		prj4._registerClickHandler(new DatabaseModulePortlet().getInstance().getPortlet());
		fileMenuItem._registerClickHandler(new FileModulePortlet().getInstance().getPortlet());

		progressBarMenu._registerClickHandler("PROGRESSBAR");
		cascadeWins._registerClickHandler("XY");
		horizontalWins._registerClickHandler("H");
		verticalWins._registerClickHandler("V");
		rearrangeWins._registerClickHandler("S");
		closeAllWins._registerClickHandler("L");
		aboutMenuItem._registerClickHandler("ABOUT");
		printMenuItem._registerClickHandler("PRINT");
		exportMenuItem._registerClickHandler("EXPORT");
	}
}
