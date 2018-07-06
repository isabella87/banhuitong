package com.bht.banhuitong.client.prj;

import com.bht.banhuitong.client.BasePortlet;

public class GraphicStatisticPortlet extends BasePortlet{
	public static String portletTitleName = "系统建模 -图形分析";
	private GraphicStatisticPortlet portletInstance;

	public GraphicStatisticPortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new GraphicStatisticPortlet();
		}
		return portletInstance;
	}
	public GraphicStatisticPortlet() {
		super(portletTitleName);
	}

	public void init() {


	}
	
}
