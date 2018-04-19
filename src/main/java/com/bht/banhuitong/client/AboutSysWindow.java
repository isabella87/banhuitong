package com.bht.banhuitong.client;

import com.google.gwt.user.client.ui.TextArea;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AboutSysWindow extends Window {

	public void init() {

		this.setAutoSize(true);
		this.setTitle("关于  班汇通数据分析系统");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setAutoCenter(true);
		this.centerInPage();
		this.addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClickEvent event) {
				destroy();
			}
		});
		DynamicForm form = new DynamicForm();
		form.setHeight(40);
		form.setWidth(300);
		form.setPadding(10);
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.setEdgeMarginSize(10);

		
		Label label1 = new Label("班汇通数据分析系统");
		label1.setHeight(30);
		Label label2 = new Label("版本 1.0.1");
		label2.setHeight(30);
		Label label3 = new Label("版权所有 ·上海鲁班金融信息服务有限公司 2014-2018");
		label3.setHeight(30);
		Label label4 = new Label("上海鲁班金融信息服务有限公司");
		label4.setHeight(30);
		
		TextArea textArea = new TextArea();
		textArea.setWidth("300px");
		textArea.setHeight("80px");
		textArea.setText("班汇通网站相关项目数据分析，投资人投资偏好分析。包括：投资人投资人年龄、性别、受教育程度等与投资之间的关系.");
		textArea.setEnabled(false);
		
		
		form.addChild(textArea);
		

		VLayout vLayout = new VLayout();
		vLayout.setHeight(200);
		vLayout.setWidth(300);
		vLayout.addMembers(label1,label2,label3,label4,form);
		
		
		HLayout hLayout = new HLayout();
		hLayout.setLeft(20);
		hLayout.setHeight(200);
		hLayout.setWidth(360);
		
		Img img = new Img("ban_logo.png");
		img.setWidth(60);
		img.setHeight(60);

		VLayout imgLayout = new VLayout();
		Label spaceL = new Label();
		spaceL.setHeight(70);
		imgLayout.addMembers(spaceL,img);
		
		hLayout.addMembers(imgLayout,vLayout);

		this.addItem(hLayout);
		this.draw();

	}
}