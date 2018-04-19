package com.bht.banhuitong.client.prj;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class LoanApplyPortlet extends BasePortlet{

	public LoanApplyPortlet(String title) {
		super(title);
//		portlet.setTitle(title);
//		init();
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
		
		portlet.addChild(yzmForm);
        
	}

}
