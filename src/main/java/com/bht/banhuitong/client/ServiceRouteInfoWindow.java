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
 * 用本系统集成的后台服务时，无需另外设置服务器访问地址；
 * 
 * 若用httpclient途径访问后台数据时需要做此设置；
 * 
 * @author Administrator
 *
 */
public class ServiceRouteInfoWindow extends Window {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	OkAndCancelBL okAndCancelBL = new OkAndCancelBL(false, this);

	public void init() {

		this.setAutoSize(true);
		this.setTitle("设置");
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

		final TextItem serviceRoute = new TextItem("service-route");
		serviceRoute.setTitle("服务器地址");
		serviceRoute.setValue("http://localhost:8889");

		final TextItem tempServiceRoute = new TextItem("temp-service-route");
		tempServiceRoute.setTitle("测试服务器地址");
		tempServiceRoute.setValue("http://localhost:8889");
		tempServiceRoute.setCanEdit(false);

		form.setFields(serviceRoute, tempServiceRoute);

		okAndCancelBL.getOkButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("root-url", serviceRoute.getValueAsString());
				loginService.setHttpServiceRootUrl(paramMap, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Boolean result) {
						destroy();

					}

				});
			}
		});

		this.addItem(form);
		this.addItem(okAndCancelBL.init());
		this.draw();

	}
}