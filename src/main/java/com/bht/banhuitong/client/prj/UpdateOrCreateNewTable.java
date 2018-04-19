package com.bht.banhuitong.client.prj;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.server.DbModelServiceAsync;
import com.bht.banhuitong.server.DbModelService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridComponent;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class UpdateOrCreateNewTable extends Window {

	private String tableName;
	private Map<String, String> baseParamMap = new HashMap<String, String>();
	public UpdateOrCreateNewTable(String tableName) {
		super();
		this.tableName = tableName;
	}
	private final DbModelServiceAsync dbModelService = GWT.create(DbModelService.class);
	private static int addNumber = 0;
	
	private static Map<String, String> tableFieldItems = new LinkedHashMap<String, String>();
	
	static {
		tableFieldItems.put("COLUMN_NAME", "字段名称");
		tableFieldItems.put("PRIMARY_KEY", "主键");
		tableFieldItems.put("DATA_TYPE", "字段类型");
//		tableFieldItems.put("DATA_LENGTH", "字段长度");
		tableFieldItems.put("CHAR_LENGTH", "数据长度");
//		tableFieldItems.put("DATA_PRECISION", "数据长度");
		tableFieldItems.put("DATA_SCALE", "数据精度");
		tableFieldItems.put("NULLABLE", "可否为空");
		tableFieldItems.put("COMMENTS", "字符描述");
	}
	public void init() {

		addNumber = 0;
		this.setAutoSize(true);
//		this.setTitle("编辑此表/创建新表");
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
		form.setHeight(50);
		form.setWidth(300);
		form.setPadding(10);
		form.setLayoutAlign(Alignment.LEFT);
//		form.setLayoutAlign(VerticalAlignment.CENTER);
		form.setEdgeMarginSize(10);
		final TextItem tableNameItem = new TextItem("TABLE_NAME");
		tableNameItem.setHeight(30);
		tableNameItem.setWidth(220);
		tableNameItem.setTitle("表名");
		tableNameItem.setValue(tableName+1);
		if(tableName != null &&!tableName.isEmpty()) {
//			tableNameItem.setCanEdit(false);
			this.setTitle("以此为模板创建新表");
		}else {
			tableNameItem.setCanEdit(true);
			this.setTitle("创建新表");
		}
		
		tableNameItem.setRequired(true);

		//
		ToolStrip gridEditControls = new ToolStrip();  
        gridEditControls.setWidth100();  
        gridEditControls.setHeight(24);  
          
        Label totalsLabel = new Label();  
        totalsLabel.setPadding(5);  
          
        LayoutSpacer spacer = new LayoutSpacer();  
        spacer.setWidth("*");  
        
		final ListGrid tableListGrid =  new ListGrid(); 
		tableListGrid.setWidth(800);
		tableListGrid.setHeight(400);
		tableListGrid.setCanRemoveRecords(true);
		
		new BaseGridPortlet("").initListGridFields(tableListGrid, tableFieldItems, tableFieldItems.keySet());
		
		tableListGrid.setCanEdit(true); 
//		tableListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);  
		
		
		  //get data
	      if(tableName != null &&!tableName.isEmpty()) {
	      	 Map<String, String> baseParamMap = new HashMap<String, String>();
	           baseParamMap.put("TABLE_NAME", tableName);
	           dbModelService.queryTableColsInfo(baseParamMap, new AsyncCallback<List<Map<String,String>>>() {

	   			@Override
	   			public void onFailure(Throwable caught) {
	   				SC.say(caught.getMessage());
	   			}

	   			@Override
	   			public void onSuccess(List<Map<String, String>> result) {
	   				if (result == null || result.isEmpty()) {
	   					tableListGrid.setData(new ListGridRecord[] {});
//							SC.say("没有符合条件的数据！");
						} else {
							Object rpcExceptionMessage = result.get(0).get("error");
							if (rpcExceptionMessage != null) {
								SC.say(rpcExceptionMessage.toString());
							} else {
								tableListGrid.setData(new ListGridRecord[] {});
								tableListGrid.setData(getRecords(result,tableFieldItems));
//								SC.say("say hi! length is"+records.length);
							}
						}
	   				
	   			}
	           	
	           });
	      }
        
		 ToolStripButton addButton = new ToolStripButton();  
	        addButton.setIcon("actions/add.png");  
	        addButton.setPrompt("add a record");  
	        addButton.addClickHandler(new ClickHandler() {  
	              
	            @Override  
	            public void onClick(ClickEvent event) {  
	            	
	            	ListGridRecord rec = new ListGridRecord();  
	            	int orderNo = tableListGrid.getRecords().length+1;  
	            	 rec.setAttribute("id", orderNo);  
	            	for(String key:tableFieldItems.keySet()) {
	            		if(key.equals("COLUMN_NAME")) {
	            			rec.setAttribute(key, "NAME"+(++addNumber));		
	            		}else if(key.equals("DATA_TYPE")) {
	            			rec.setAttribute(key, "VARCHAR2");	
	            		}else if(key.equals("CHAR_LENGTH")) {
	            			rec.setAttribute(key, "20");	
	            		}else {
	            			rec.setAttribute(key, "");		
	            		}
	            	}
	                tableListGrid.addData(rec); 
	            }  
	        }); 
	        
		ToolStripButton editButton = new ToolStripButton();  
        editButton.setIcon("actions/edit.png");  
        editButton.setPrompt("Edit selected record");  
        editButton.addClickHandler(new ClickHandler() {  
              
            @Override  
            public void onClick(ClickEvent event) {  
                ListGridRecord record = tableListGrid.getSelectedRecord();  
                if (record == null) {
                	record = tableListGrid.getRecords()[tableListGrid.getRecords().length-1];
                }
                tableListGrid.startEditing(tableListGrid.getDataAsRecordList().indexOf(record), 0, false);  
                  
            }  
        });  
          
        ToolStripButton removeButton = new ToolStripButton();  
        removeButton.setIcon("actions/remove.png");  
        removeButton.setPrompt("Remove selected record");  
        removeButton.addClickHandler(new ClickHandler() {  
              
            @Override  
            public void onClick(ClickEvent event) {  
            	
            	if(!tableListGrid.anySelected()) {
            		 ListGridRecord record = tableListGrid.getRecords()[tableListGrid.getRecords().length-1];
            		tableListGrid.removeData(record);
            	}else {
            		tableListGrid.removeSelectedData();  
            	}
            }  
        });  
        
        gridEditControls.setMembers(totalsLabel, spacer, addButton,editButton, removeButton);  
        
        tableListGrid.setGridComponents(new Object[] {  
                ListGridComponent.HEADER,   
                ListGridComponent.FILTER_EDITOR,   
                ListGridComponent.BODY,   
                gridEditControls  
        });  
          
        //
        
		IButton okButton = new IButton("确认");
		okButton.setWidth(50);
		okButton.setHeight(25);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String tableName = tableNameItem.getValueAsString();
				Map<String, String> paramMap = new HashMap<String, String>();
				Map<String, String> columnCommentsParamMap = new HashMap<String, String>();
//				paramMap.put("NAME", "varchar2(40)");
//				paramMap.put("COMMENTS", "varchar2(100)");
				ListGridRecord[] records = tableListGrid.getRecords();
				String primaryKey = "";
				for(ListGridRecord record: records) {
					if(!record.getAttribute("COMMENTS").isEmpty()) {
						columnCommentsParamMap.put(record.getAttribute("COLUMN_NAME"), record.getAttribute("COMMENTS"));
					}
					String charLen = record.getAttribute("CHAR_LENGTH").isEmpty()?"()":"("+record.getAttribute("CHAR_LENGTH")+")";
					if(record.getAttribute("DATA_TYPE").equals("NUMBER")) {
						if(record.getAttribute("CHAR_LENGTH").contains(",")) {
							charLen = "("+record.getAttribute("CHAR_LENGTH").split(",")[0]+","+(record.getAttribute("DATA_SCALE").isEmpty()?"0":record.getAttribute("DATA_SCALE"))+")";
						}else if(!record.getAttribute("CHAR_LENGTH").isEmpty()) {
							charLen ="("+ record.getAttribute("CHAR_LENGTH")+","+ (record.getAttribute("DATA_SCALE").isEmpty()?"0":record.getAttribute("DATA_SCALE"))+")";
						}else {
							charLen = "";
						}
					}else if(record.getAttribute("DATA_TYPE").equals("DATE")) {
						charLen = "";
					}else if(record.getAttribute("DATA_TYPE").equals("TIMESTAMP")) {
						charLen = " (6)";
					}
					
					String nullable = "";
					if(record.getAttributeAsBoolean("NULLABLE")) {
						nullable = " NOT NULL ENABLE";
					}

					if(record.getAttributeAsBoolean("PRIMARY_KEY")) {
						primaryKey +=",\""+ record.getAttribute("COLUMN_NAME")+"\"";
					}
		
					paramMap.put(record.getAttribute("COLUMN_NAME"), record.getAttribute("DATA_TYPE")+charLen+nullable);
				}
				paramMap.put("PRIMARY_KEY", primaryKey.substring(1));
				dbModelService.createTable(tableName, paramMap,columnCommentsParamMap, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());

					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							destroy();
							DatabaseModulePortlet databaseModulePortlet = new DatabaseModulePortlet().getInstance();
							final ListGrid dbTableModelListGrid = databaseModulePortlet.dbTableModelListGrid;
							if(dbTableModelListGrid!=null) {
								dbModelService.queryCurDbUserTablesInfo(baseParamMap, new AsyncCallback<List<Map<String, String>>>(){

									@Override
									public void onFailure(Throwable caught) {
										SC.say(caught.getMessage());
									}

									@Override
									public void onSuccess(List<Map<String, String>> result) {
										if (result == null || result.isEmpty()) {
											dbTableModelListGrid.setData(new ListGridRecord[] {});
//											SC.say("没有符合条件的数据！");
										} else {
											Object rpcExceptionMessage = result.get(0).get("error");
											if (rpcExceptionMessage != null) {
												SC.say(rpcExceptionMessage.toString());
											} else {
												dbTableModelListGrid.setData(new ListGridRecord[] {});
												dbTableModelListGrid.setData(getRecords(result,DatabaseModulePortlet.dbTableModelFieldItems));

											}
										}
										
									}
									
								});
							}
							
							SC.say("创建成功！");
						} else {
							SC.say("创建失敗！");
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
				destroy();
			}
		});

		Label spaceLabel = new Label();

		spaceLabel.setWidth(100);
		HLayout buttonLayout = new HLayout();
		buttonLayout.setTop(10);
		buttonLayout.setLayoutAlign(Alignment.CENTER);
//		buttonLayout.setLayoutAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(20);
		buttonLayout.setWidth(150);
		buttonLayout.setHeight(30);
		buttonLayout.addMembers( cancelButton, okButton);

		form.setFields(tableNameItem);
		this.addItem(form);
		this.addItem(tableListGrid);
		this.addItem(buttonLayout);
		this.draw();

	}

	
	/**
	 * 获取数据
	 * @return
	 */
	public  ListGridRecord[] getRecords(List<Map<String, String>> result,Map<String, String> fieldItems) {
		int length = result.size();
		ListGridRecord[] listRecords = new ListGridRecord[length];
		for(int i = 0;i<result.size();i++) {
			Map<String, String> mapItem = result.get(i);
			listRecords[i]= createRecord(mapItem,i,fieldItems);
		}
		
		return listRecords;
	}

	public ListGridRecord createRecord(Map<String, String> mapItem,int i,Map<String, String> fieldItems) {
		ListGridRecord record = new ListGridRecord();  
		record.setAttribute("id", ++i);
		for(String key:fieldItems.keySet()) {
			if(mapItem.get(key)==null||mapItem.get(key).toString().isEmpty()) {
				continue ;
			}
			if(key.equals("NULLABLE")) {
				record.setAttribute(key, mapItem.get(key).trim().equals("N")?true : false);
			}else if(key.equals("PRIMARY_KEY")) {
				record.setAttribute(key, mapItem.get(key).contains(mapItem.get("COLUMN_NAME"))?true : false);
			}else if(key.equals("CHAR_LENGTH")) {
				String value = "";
				if(mapItem.get("DATA_TYPE").equals("NUMBER")) {
					if(!mapItem.get("DATA_PRECISION").isEmpty() || !mapItem.get("DATA_SCALE").isEmpty()) {
						value = mapItem.get("DATA_PRECISION")+","+mapItem.get("DATA_SCALE");
					}
				}
				record.setAttribute(key, value.isEmpty()?mapItem.get(key).equals("0")?"":mapItem.get(key):value);
			}else {
				record.setAttribute(key, mapItem.get(key)); 
			}
		}
		record = addLostAttribute(record,fieldItems);
        return record;  
	}
	
	private ListGridRecord addLostAttribute(ListGridRecord record,Map<String, String> fieldItems) {
		for (String fieldKey : fieldItems.keySet()) {
			if (record.getAttribute(fieldKey) == null) {
				record.setAttribute(fieldKey, "");
			}
		}
		return record;
	}
	
}