package com.bht.banhuitong.client;

import java.util.HashMap;
import java.util.Map;

import com.bht.banhuitong.client.common.OkAndCancelBL;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * 注册码验证框（此功能试用于 C/S 端，B/S 端不建议试用）
 * 被动式试用期限制：客户端记录首次安装访问时间，再次访问时当前时间与以上记录首次安装访问的时间之差大于30天，即试用期已过
 * 主动式试用期限制：服务端记录的用户注册时间为首次访问时间，当前时间为与注册时间只差大于30天，即试用期已过
 * 				（服务端增加已认证信息，如果服务端记录了用户输入过正确的注册码，则不用进行试用期判断）
 * @author Administrator
 *
 */
public class LicenceWindow extends Window {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	OkAndCancelBL okAndCancelBL = new OkAndCancelBL(true, this);
	
	private Boolean srcLogin ;
	
	LicenceWindow(Boolean srcLogin){
		this.srcLogin = srcLogin;
	}

	public void init() {

		this.setAutoSize(true);
		this.setTitle("注册码");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setAutoCenter(true);
		this.centerInPage();
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
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

		final TextItem licenceTextItem = new TextItem("licence");
		licenceTextItem.setTitle("注册码验证");
		licenceTextItem.setWidth(200);

		form.setFields(licenceTextItem);

		okAndCancelBL.getOkButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("licence", licenceTextItem.getValueAsString());
				loginService.checkLicence(paramMap, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						okAndCancelBL.getErrorLabel().setContents("注册码错误");
						okAndCancelBL.getErrorLabel().redraw();
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							if(srcLogin!=null && srcLogin) {
								MainFrame.menuLayout.setDisabled(false);
								BaseFrame.editEndCanvas();
								SysMenuItem.getInstance().enableIMenuItem();
								LoginWindow.getInstance().destroy();
							}
							destroy();
						}else {
							okAndCancelBL.getErrorLabel().setContents("注册码错误");
							okAndCancelBL.getErrorLabel().redraw();
						}
					}
				});
			}
		});

		this.addItem(form);
		this.addItem(okAndCancelBL.init());
		this.draw();
	}
}