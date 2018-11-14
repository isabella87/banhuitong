package com.bht.banhuitong.client.common;

import com.bht.banhuitong.client.MainFrame;
import com.bht.banhuitong.client.SysMenuItem;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class OkAndCancelBL {
	private Label errorLabel = new Label();
	private IButton okButton = new IButton("确认");
	
	private IButton cancelButton = new IButton("取消");
	
	public Label getErrorLabel() {
		return errorLabel;
	}

	public IButton getOkButton() {
		return okButton;
	}
	
	public IButton getCancelButton() {
		return cancelButton;
	}

	private int layoutWidth = 300;
	private int layoutHeight = 30;
	private int buttonHeight = 25;
	private int buttonWidth = 50;
	private int errorMsgWidth = 70;
	private int errorMsgHeight = 20;
	private boolean errorMsgFlag = false;
	private boolean disableAllByCancel = false;
	private Window parentWin;

	public <T> OkAndCancelBL(boolean disableAllByCancel,boolean errorMsgFlag, T window) {
		this.disableAllByCancel = disableAllByCancel;
		this.errorMsgFlag = errorMsgFlag;
		this.parentWin = (Window) window;
	}

	public HLayout init() {

		okButton.setWidth(buttonWidth);
		okButton.setHeight(buttonHeight);

		cancelButton.setWidth(buttonWidth);
		cancelButton.setHeight(buttonHeight);

		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(disableAllByCancel) {
					MainFrame.menuLayout.setDisabled(true);
					SysMenuItem.getInstance().disableIMenuItem();
				}	
				parentWin.destroy();
			}
		});

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(layoutWidth);
		buttonLayout.setHeight(layoutHeight);

		if (errorMsgFlag) {
			errorLabel.setWidth(errorMsgWidth);
			errorLabel.setHeight(errorMsgHeight);
			errorLabel.setStyleName("serverResponseLabelError");
			buttonLayout.addMembers(errorLabel);
		} else {
			buttonLayout.setAlign(Alignment.CENTER);
		}
		buttonLayout.addMembers(cancelButton, okButton);

		return buttonLayout;
	}
	
	public void closeByOk() {
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parentWin.destroy();
			}
		});
	}
	
	public void close() {
		parentWin.destroy();
	}
}