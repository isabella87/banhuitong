package com.bht.banhuitong.client.files;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.BasePortlet;
import com.bht.banhuitong.server.FileService;
import com.bht.banhuitong.server.FileServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CreateDirWindow extends Window {

	private static final FileServiceAsync fileService = GWT.create(FileService.class);

	private Label errorLabel = new Label();

	private String pName;
	private String allPath;

	public CreateDirWindow(String pName, String allPath) {
		this.pName = pName;
		this.allPath = allPath;
	}

	public void init() {

		this.setAutoSize(true);
		this.setTitle("创建节点");
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

		TextItem pTextItem = new TextItem("p-name");
		pTextItem.setTitle("父节点名称");
		pTextItem.setCanEdit(false);
		pTextItem.setDefaultValue(pName);
		pTextItem.setDisabled(false);

		final TextItem textItem = new TextItem("cur-name");
		textItem.setTitle("节点名称");
		textItem.setDefaultValue("");
		
		IButton okButton = new IButton("确认");
		okButton.setWidth(60);
		okButton.setHeight(25);

		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String[] dirs = { allPath, textItem.getValueAsString() };
				fileService.createDir(dirs, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						new BasePortlet("").showErrorMessage(caught.getMessage());
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							destroy();
							
							BasePortlet portlet = BaseFrame.getTail(BaseFrame.portlets).getValue();
							new BaseFrame().delPortlet(portlet.getTitle());
							new BaseFrame().changeMainCanvas(new FileModulePortlet().getPortlet());
							
							SC.say("创建成功！");
						} else {
							errorLabel.setContents("创建失败！");
						}
					}
				});
			}
		});

		/*Label spaceLable = new Label();
		spaceLable.setWidth(220);
		spaceLable.setHeight(20);*/

		errorLabel.setWidth(175);
		errorLabel.setHeight(20);
		errorLabel.setStyleName("serverResponseLabelError");
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(300);
		buttonLayout.setHeight(30);
		buttonLayout.addMembers(errorLabel, okButton);

		form.setItems(pTextItem, textItem);

		VLayout vLayout = new VLayout();
		vLayout.setHeight(100);
		vLayout.addMembers(form, buttonLayout);

		HLayout hLayout = new HLayout();
		hLayout.setLeft(20);
		hLayout.setHeight(100);
		hLayout.setWidth(300);

		hLayout.addMembers(vLayout);

		this.addItem(hLayout);
		this.draw();
	}
}