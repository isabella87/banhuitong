package com.bht.banhuitong.client.prj;

import com.bht.banhuitong.client.BasePortlet;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class GuaranteeOrgPortlet extends BasePortlet{
	private static String portletTitleName = "基础数据 -项担保机构";
	private GuaranteeOrgPortlet portletInstance;

	public GuaranteeOrgPortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new GuaranteeOrgPortlet();
		}
		return portletInstance;
	}
	public GuaranteeOrgPortlet() {
		super(portletTitleName);
		
	}

	public void init() {

		TextItem yzmItem = new TextItem();
		yzmItem.setHeight(30);
		yzmItem.setWidth(110);
		yzmItem.setTitle("验证码");
		yzmItem.setRequired(true);
		
		DynamicForm yzmForm = new DynamicForm();
		yzmForm.setTop(30);
		yzmForm.setHeight(30);
		yzmForm.setWidth(180);
		yzmForm.setPadding(0);
		yzmForm.setLayoutAlign(VerticalAlignment.TOP);
		yzmForm.setEdgeMarginSize(1);
		
		yzmForm.setFields(yzmItem);
		
		this.addChild(yzmForm);
        
	}
	
}
