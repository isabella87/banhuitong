package com.bht.banhuitong.client;

import java.util.HashMap;
import java.util.Map;

import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class LoginWindow extends Window {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private static Img img = null;
	protected static String loginName;
	private Label errorLabel = new Label();

	private static LoginWindow instance;

	public static LoginWindow getInstance() {
		if (instance == null) {
			instance = new LoginWindow();
		}

		return instance;
	}

	public void init() {

		this.setAutoSize(true);
		this.setTitle("登录");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setAutoCenter(true);
		this.centerInPage();
		this.addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClickEvent event) {
				destroy();
				MainFrame.menuLayout.setDisabled(true);
			}
		});
		DynamicForm form = new DynamicForm();
		form.setHeight(50);
		form.setWidth(300);
		form.setPadding(10);
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.setEdgeMarginSize(10);
		final TextItem usernameItem = new TextItem("user-name");
		usernameItem.setHeight(30);
		usernameItem.setWidth(220);
		usernameItem.setTitle("用户名");
		usernameItem.setRequired(true);

		final PasswordItem passwordItem = new PasswordItem("password");
		passwordItem.setHeight(30);
		passwordItem.setWidth(220);
		passwordItem.setTitle("密码");
		passwordItem.setRequired(true);

		final TextItem yzmItem = new TextItem("captcha-code");
		yzmItem.setHeight(30);
		yzmItem.setWidth(110);
		yzmItem.setTitle("验证码");
		yzmItem.setRequired(true);

		errorLabel.setHeight(20);
		errorLabel.setStyleName("serverResponseLabelError");

		IButton okButton = new IButton("确认");
		okButton.setWidth(50);
		okButton.setHeight(25);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				validateLogin();
			}

			private void validateLogin() {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(usernameItem.getFieldName(), usernameItem.getValueAsString());
				paramMap.put(passwordItem.getFieldName(), passwordItem.getValueAsString());
				paramMap.put(yzmItem.getFieldName(), yzmItem.getValueAsString());
				final String tempLoginName = usernameItem.getValueAsString();

				loginService.loginImmediately(paramMap, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						showLoginErrorMessage(caught.getMessage());

					}

					@Override
					public void onSuccess(String result) {
						if (result.equals("true")) {
							loginName = tempLoginName;
							destroy();

						} else {
							errorLabel.setContents("登陆失败！");
							errorLabel.redraw();
						}
					}

				});
			}
		});

		IButton cancelButton = new IButton("取消");
		cancelButton.setWidth(50);
		cancelButton.setHeight(25);

		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MainFrame.menuLayout.setDisabled(true);
				destroy();
			}
		});

		DynamicForm yzmForm = new DynamicForm();
		yzmForm.setHeight(30);
		yzmForm.setWidth(180);
		yzmForm.setPadding(0);
		yzmForm.setLayoutAlign(VerticalAlignment.TOP);
		yzmForm.setEdgeMarginSize(1);
		yzmForm.setFields(yzmItem);

		img = new Img();
		initImg();// 加载初始验证码图片
		img.setHeight(28);

		final HLayout imgLayout = new HLayout();
		imgLayout.setMembersMargin(4);
		imgLayout.setWidth(70);
		imgLayout.setHeight(30);
		imgLayout.setLayoutAlign(Alignment.LEFT);

		final Canvas canvas = new Canvas();
		canvas.setHeight(30);
		canvas.setWidth(70);
		canvas.addChild(img);

		imgLayout.addMember(canvas);

		HLayout yzmLayout = new HLayout();
		yzmLayout.setMembersMargin(4);
		yzmLayout.setWidth(250);
		yzmLayout.setHeight(40);
		yzmLayout.setLayoutAlign(Alignment.LEFT);

		yzmLayout.addMembers(yzmForm, imgLayout);

		Label spaceLabel = new Label();

		spaceLabel.setWidth(100);
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(200);
		buttonLayout.setHeight(30);
		buttonLayout.addMembers(spaceLabel, cancelButton, okButton);

		form.setFields(usernameItem, passwordItem);
		this.addItem(form);
		this.addItem(yzmLayout);
		this.addItem(errorLabel);
		this.addItem(buttonLayout);
		this.draw();

		imgLayout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loginService.getImageByte(new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						errorLabel.setContents("获取验证码失败！");
						errorLabel.redraw();
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						if (result != null && !result.isEmpty()) {
							img.removeFromParent();
							img = new Img(result);
							img.setHeight(28);
							img.redraw();
							canvas.addChild(img);
							canvas.redraw();

							imgLayout.redraw();
							redraw();
						} else {
							errorLabel.setContents("获取验证码失败！");
							errorLabel.redraw();
						}
					}
				});
			}
		});
	}

	public void initImg() {
		loginService.getImageByte(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say(caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				if (result == null || result.isEmpty()) {
					errorLabel.setContents("获取验证码失败！");
					errorLabel.redraw();
				} else {
					img.setSrc(result);
					;
					img.setHeight(28);
					img.redraw();
				}
			}
		});
	}

	public void showLoginErrorMessage(final String errorMsg) {
		String errorCode = errorMsg.substring(errorMsg.indexOf("@@@") + 3).trim();
		errorCode = errorCode.substring(0, errorCode.indexOf("@@@")).trim();
		if (errorCode.matches("[0-9]+")) {
			if (Integer.valueOf(errorCode) == 1) {// 如果是未登录状态，则重新打开登录页面
				LoginWindow.getInstance().init();
			} else if (Integer.valueOf(errorCode) == 4 || Integer.valueOf(errorCode) == 5
					|| Integer.valueOf(errorCode) == 6) {
				errorLabel.setContents(BaseFrame.exceMap.get(Integer.valueOf(errorCode)));

			} else {
				SC.say(BaseFrame.exceMap.get(Integer.valueOf(errorCode)));
			}
		} else {
			errorLabel.setContents("网络连接异常！");
		}
		errorLabel.redraw();
	}
}