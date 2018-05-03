package com.bht.banhuitong.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UploadWindow extends Window {

	private Label errorLabel = new Label();

	private String path ;
	
	public UploadWindow(String path) {
		this.path = path;
	}
	public void init() {

		this.setAutoSize(true);
		this.setTitle("上传");
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

		final FileUpload fileUpload = new FileUpload();
		fileUpload.setHeight("40px");
		final FormPanel formpanel = new FormPanel();

		IButton okButton = new IButton("确认");
		okButton.setWidth(50);
		okButton.setHeight(25);

		fileUpload.setName("uploadFormElement");
		formpanel.setHeight("40px");
		formpanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formpanel.setMethod(FormPanel.METHOD_POST);
		formpanel.setAction(GWT.getModuleBaseURL() + "upload?filepath="+path);
		formpanel.setWidget(fileUpload);

		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent sender) {
				formpanel.submit();
			}
		});
		formpanel.addSubmitHandler(new SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				if (fileUpload.getFilename().length() == 0) {
					errorLabel.setContents("请选择一个文件上传！");
					event.cancel();
				}

			}
		});
		formpanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				destroy();
				SC.say("上传成功！");
			}
		});

		Label spaceLable = new Label();
		spaceLable.setWidth(220);
		spaceLable.setHeight(20);
		
		errorLabel.setWidth(150);
		errorLabel.setHeight(30);
		errorLabel.setStyleName("serverResponseLabelError");
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(300);
		buttonLayout.setHeight(30);
		buttonLayout.addMembers(spaceLable, okButton);

		form.addChild(formpanel);

		VLayout vLayout = new VLayout();
		vLayout.setHeight(100);
		vLayout.addMembers(form, errorLabel,buttonLayout);

		HLayout hLayout = new HLayout();
		hLayout.setLeft(20);
		hLayout.setHeight(100);
		hLayout.setWidth(300);
		
		Img img = new Img("ban_logo.png");
		
		img.setTop(20);
		img.setWidth(60);
		img.setHeight(60);

		hLayout.addMembers(vLayout);

		this.addItem(hLayout);
		this.draw();

	}
}