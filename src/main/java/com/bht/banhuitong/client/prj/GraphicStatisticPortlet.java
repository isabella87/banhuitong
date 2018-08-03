package com.bht.banhuitong.client.prj;

import com.bht.banhuitong.client.BasePortlet;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Frame;

public class GraphicStatisticPortlet extends BasePortlet {
	public static String portletTitleName = "数据分析 -报表分析";
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

		String url = GWT.getHostPageBaseURL() + "frameset?__report=report.rptdesign&__showtitle=false&__title=loan amt";

		Frame frame = new Frame(url);

		frame.setSize("99.9%", "99.7%");

		this.addItem(frame);
	}

}
