package com.bht.banhuitong.client.common;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class OkAndCancelWithErrorMsgBL {

	private Label errorLabel = new Label();
	private IButton okButton = new IButton("确认");
	private IButton cancelButton = new IButton("取消");

	private int layoutWidth = 300;
	private int layoutHeight = 30;
	private int buttonHeight = 25;
	private int buttonWidth = 50;
	private int errorMsgWidth = 70;
	private int errorMsgHeight = 20;

	public HLayout init() {

		errorLabel.setWidth(errorMsgWidth);
		errorLabel.setHeight(errorMsgHeight);
		errorLabel.setStyleName("serverResponseLabelError");

		okButton.setWidth(buttonWidth);
		okButton.setHeight(buttonHeight);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO
			}
		});

		cancelButton.setWidth(buttonHeight);
		cancelButton.setHeight(buttonWidth);

		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}
		});

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(layoutWidth);
		buttonLayout.setHeight(layoutHeight);
		buttonLayout.addMembers(errorLabel, cancelButton, okButton);

		return buttonLayout;
	}

	public void showLoginErrorMessage(final String errorMsg) {
		errorLabel.redraw();
	}
}