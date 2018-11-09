package com.bht.banhuitong.client;

import java.util.HashMap;
import java.util.Map;

import com.bht.banhuitong.client.common.OkAndCancelBL;
import com.bht.banhuitong.server.LoginService;
import com.bht.banhuitong.server.LoginServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class LoginWindow extends Window {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private static Img img = null;
	protected static String loginName;
	final TextItem usernameItem = new TextItem("user-name");
	final PasswordItem passwordItem = new PasswordItem("password");
	final TextItem yzmItem = new TextItem("captcha-code");
	private OkAndCancelBL okAndCancelBL ;
	
	private int textItemWidth = 220;
	private int textItemHeight = 30;
	
	public static LoginWindow instance;

	public static LoginWindow getInstance() {
		if (instance == null) {
			instance = new LoginWindow();
		}
		return instance;
	}

	public void init() {
		this.okAndCancelBL = new OkAndCancelBL(true,this);
		
		this.setCanFocus(true);
		this.setAutoSize(true);
		this.setTitle("登录");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setAutoCenter(true);
		this.centerInPage();
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {	
				MainFrame.menuLayout.setDisabled(true);
				SysMenuItem.getInstance().disableIMenuItem();
				destroy();
			}
		});
		DynamicForm form = new DynamicForm();
		form.setHeight(50);
		form.setWidth(300);
		form.setPadding(10);
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.setEdgeMarginSize(10);
		usernameItem.setHeight(textItemHeight);
		usernameItem.setWidth(textItemWidth);
		usernameItem.setTitle("用户名");
		usernameItem.setRequired(true);

		passwordItem.setHeight(textItemHeight);
		passwordItem.setWidth(textItemWidth);
		passwordItem.setTitle("密码");
		passwordItem.setRequired(true);

		yzmItem.setHeight(textItemHeight);
		yzmItem.setWidth(textItemWidth/2);
		yzmItem.setTitle("验证码");
		yzmItem.setRequired(true);

		DynamicForm yzmForm = new DynamicForm();
		yzmForm.setHeight(textItemHeight);
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
		imgLayout.setHeight(textItemHeight);
		imgLayout.setLayoutAlign(Alignment.LEFT);

		final Canvas canvas = new Canvas();
		canvas.setHeight(textItemHeight);
		canvas.setWidth(70);
		canvas.addChild(img);

		imgLayout.addMember(canvas);

		HLayout yzmLayout = new HLayout();
		yzmLayout.setMembersMargin(4);
		yzmLayout.setWidth(250);
		yzmLayout.setHeight(40);
		yzmLayout.setLayoutAlign(Alignment.LEFT);

		yzmLayout.addMembers(yzmForm, imgLayout);

		form.setFields(usernameItem, passwordItem);
		
		this.addItem(form);
		this.addItem(yzmLayout);
		this.addItem(okAndCancelBL.init());
		this.draw();
		
		okAndCancelBL.getOkButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				validateLogin();
			}
		});

		imgLayout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loginService.getImageByte(new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						okAndCancelBL.getErrorLabel().setContents("获取验证码失败！");
						okAndCancelBL.getErrorLabel().redraw();
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
							okAndCancelBL.getErrorLabel().setContents("获取验证码失败！");
							okAndCancelBL.getErrorLabel().redraw();
						}
					}
				});
			}
		});
		
		this.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getKeyName().equals("Enter")) {
					validateLogin();
				}
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
					okAndCancelBL.getErrorLabel().setContents("获取验证码失败！");
					okAndCancelBL.getErrorLabel().redraw();
				} else {
					img.setSrc(result);
					
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
				LoginWindow.getInstance().destroy();
				LoginWindow.getInstance().init();
			} else if (Integer.valueOf(errorCode) == 4 || Integer.valueOf(errorCode) == 5
					|| Integer.valueOf(errorCode) == 6) {
				okAndCancelBL.getErrorLabel().setContents(BaseFrame.exceMap.get(Integer.valueOf(errorCode)));

			} else {
				SC.say(BaseFrame.exceMap.get(Integer.valueOf(errorCode)));
			}
		} else {
			okAndCancelBL.getErrorLabel().setContents("网络连接异常！");
		}
		okAndCancelBL.getErrorLabel().redraw();
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
					MainFrame.menuLayout.setDisabled(false);
					loginName = tempLoginName;
					BaseFrame.editEndCanvas();
					SysMenuItem.getInstance().enableIMenuItem();
					destroy();

				} else {
					okAndCancelBL.getErrorLabel().setContents("登陆失败！");
					okAndCancelBL.getErrorLabel().redraw();
				}
			}

		});
	}
}