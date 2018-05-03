package com.bht.banhuitong.client.prj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BasePortlet;
import com.bht.banhuitong.server.DbModelService;
import com.bht.banhuitong.server.DbModelServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class DatabaseModulePortlet extends BasePortlet{

	public static Map<String, String> dbTableModelFieldItems = new LinkedHashMap<String, String>();
	private static List<String> candbTableModelEditFieldItems = new ArrayList<String>();
	private static Map<String, String> dbTableColsModelFieldItems = new LinkedHashMap<String, String>();
	
	private static Map<String, String> sysModuleFieldItems = new LinkedHashMap<String, String>();
	private static List<String> cansysModuleEditFieldItems = new ArrayList<String>();
	private static Map<String, String> sysTableFieldItems = new LinkedHashMap<String, String>();
	private static List<String> cansysTableEditFieldItems = new ArrayList<String>();
	private static final DbModelServiceAsync dbModelService = GWT.create(DbModelService.class);
	private String  lastClickSmiIds = "";

	private ListGridRecord  lastDbTableModelListGridRecord;
	private ListGridRecord  lastsysModuleListGridRecord;
	private ListGridRecord  lastsysTableListGridRecord;
	
	private static String portletTitleName = "系统建模 -数据库表操作";
	private DatabaseModulePortlet portletInstance;

	static {
		dbTableModelFieldItems.put("TABLE_NAME", "表名");
//		dbTableModelFieldItems.put("TABLESPACE_NAME", "表空间名");
//		dbTableModelFieldItems.put("LAST_ANALYZED", "最后一次分析时间");
		dbTableModelFieldItems.put("COMMENTS", "注释");
//		dbTableModelFieldItems.put("TABLE_TYPE", "表类型");
		
		candbTableModelEditFieldItems.add("COMMENTS");
		
		
		dbTableColsModelFieldItems.put("COLUMN_NAME", "列名");
		dbTableColsModelFieldItems.put("DATA_TYPE", "数据类型");
		dbTableColsModelFieldItems.put("DATA_LENGTH", "数据长度");
		dbTableColsModelFieldItems.put("DATA_PRECISION", "数据精度");
		dbTableColsModelFieldItems.put("DATA_SCALE", "数据规模");
		dbTableColsModelFieldItems.put("NULLABLE", "可否为空");
		dbTableColsModelFieldItems.put("CHAR_LENGTH", "字符长度");
		dbTableColsModelFieldItems.put("COMMENTS", "注释");
		
		
		sysModuleFieldItems.put("SMI_ID", "ID");
		sysModuleFieldItems.put("MODULE_NAME", "模块名");
		sysModuleFieldItems.put("MODULE_NO", "模块代码");
		sysModuleFieldItems.put("MUDULE_SHORT_NAME", "模块名简称");
		sysModuleFieldItems.put("MUDULE_DESC", "模块描述");
		sysModuleFieldItems.put("CREATOR", "创建人");
		sysModuleFieldItems.put("CREATE_TIME", "创建时间");
		
		cansysModuleEditFieldItems.add("MODULE_NAME");
		cansysModuleEditFieldItems.add("MUDULE_DESC");
		
		sysTableFieldItems.put("STI_ID", "ID");
		sysTableFieldItems.put("TABLE_NAME", "表名");
		sysTableFieldItems.put("SMI_ID", "对应模块ID");
		sysTableFieldItems.put("TABLE_COMMENTS", "表注释");
		sysTableFieldItems.put("REMARK", "备注");
		sysTableFieldItems.put("CREATOR", "创建人");
		sysTableFieldItems.put("CREATE_TIME", "创建时间");
	
		cansysTableEditFieldItems.add("TABLE_COMMENTS");
		cansysTableEditFieldItems.add("REMARK");
	}
	
	public DatabaseModulePortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new DatabaseModulePortlet();
		}
		return portletInstance;
	}
	
	public DatabaseModulePortlet() {
		super(portletTitleName);
	}
	
	public void init() {
		
		HLayout mainLayout = new HLayout();  
        mainLayout.setWidth100();  
        mainLayout.setHeight100();
        
        HLayout leftLayout = new HLayout();  
        leftLayout.setWidth("30%");
        leftLayout.setOverflow(Overflow.HIDDEN);
        leftLayout.setShowResizeBar(true);
        
		VLayout vLayout = new VLayout();  
        vLayout.setWidth("70%");
        vLayout.setOverflow(Overflow.HIDDEN);
        
        HLayout topLayout = new HLayout();  
        topLayout.setHeight("30%");
        topLayout.setOverflow(Overflow.HIDDEN);
        topLayout.setShowResizeBar(true);
        
        HLayout bottomLayout = new HLayout();  
        bottomLayout.setHeight("70%");
        bottomLayout.setOverflow(Overflow.HIDDEN);
        bottomLayout.setShowResizeBar(true);
        
		final ListGrid dbTableModelListGrid = new ListGrid();
//        dbTableModelListGrid = new ListGrid();
        
		dbTableModelListGrid.setHeight100();
		dbTableModelListGrid.setWidth100();
		dbTableModelListGrid.setTop(20);
		dbTableModelListGrid.setLeft(5);
		dbTableModelListGrid.setShowAllRecords(true);
		dbTableModelListGrid.setShowEmptyMessage(true);
		dbTableModelListGrid.setEmptyMessage("请点击<b>搜索</b>按钮查询数据！");
		dbTableModelListGrid.setAutoFetchData(true);
//		dbTableModelListGrid.setCanEdit(true);
		dbTableModelListGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
//		dbTableModelListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		
		initListGridFields(dbTableModelListGrid,dbTableModelFieldItems,candbTableModelEditFieldItems);
		
		leftLayout.addMember(dbTableModelListGrid);
		
		//
		final Map<String, String> baseParamMap = new HashMap<String, String>();
		dbModelService.queryCurDbUserTablesInfo(baseParamMap, new AsyncCallback<List<Map<String, String>>>(){

			@Override
			public void onFailure(Throwable caught) {
				SC.say(caught.getMessage());
			}

			@Override
			public void onSuccess(List<Map<String, String>> result) {
				if (result == null || result.isEmpty()) {
					dbTableModelListGrid.setData(new ListGridRecord[] {});
//					SC.say("没有符合条件的数据！");
				} else {
					Object rpcExceptionMessage = result.get(0).get("error");
					if (rpcExceptionMessage != null) {
						SC.say(rpcExceptionMessage.toString());
					} else {
						dbTableModelListGrid.setData(new ListGridRecord[] {});
						dbTableModelListGrid.setData(getRecords(result,dbTableModelFieldItems));

					}
				}
				
			}
			
		});
		
		//
		
		final ListGrid moduleListGrid = new ListGrid();
		
		moduleListGrid.setHeight100();
		moduleListGrid.setWidth100();
		moduleListGrid.setTop(20);
		moduleListGrid.setLeft(5);
		moduleListGrid.setShowAllRecords(true);
		moduleListGrid.setShowEmptyMessage(true);
		moduleListGrid.setEmptyMessage("请点击<b>搜索</b>按钮查询数据！");
		moduleListGrid.setAutoFetchData(true);
//		moduleListGrid.setCanEdit(true);
		moduleListGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		moduleListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		
		initListGridFields(moduleListGrid,sysModuleFieldItems,cansysModuleEditFieldItems);
		
		
		dbModelService.querySysModuleInfo(baseParamMap, new AsyncCallback<List<Map<String, String>>>(){

			@Override
			public void onFailure(Throwable caught) {
				SC.say(caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<Map<String, String>> result) {
				if (result == null || result.isEmpty()) {
					moduleListGrid.setData(new ListGridRecord[] {});
//					SC.say("没有符合条件的数据！");
				} else {
					Object rpcExceptionMessage = result.get(0).get("error");
					if (rpcExceptionMessage != null) {
						SC.say(rpcExceptionMessage.toString());
					} else {
						moduleListGrid.setData(new ListGridRecord[] {});
						moduleListGrid.setData(getRecords(result,sysModuleFieldItems));

					}
				}
				
			}
			
		});
		
		//
		final ListGrid tableListGrid = new ListGrid();

		tableListGrid.setHeight100();
		tableListGrid.setWidth100();
		tableListGrid.setTop(20);
		tableListGrid.setLeft(5);
		tableListGrid.setShowAllRecords(true);
		tableListGrid.setShowEmptyMessage(true);
		tableListGrid.setEmptyMessage("请点击<b>搜索</b>按钮查询数据！");
		tableListGrid.setAutoFetchData(true);
//		tableListGrid.setCanEdit(true);
		tableListGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		tableListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		
		initListGridFields(tableListGrid,sysTableFieldItems,cansysTableEditFieldItems);
		
		Menu dbTableModelMenu = new Menu();
		MenuItem dbTableModelSearchMenu = new MenuItem("搜索");
		MenuItem dbTableModelInsertIntoModuleTableMenu = new MenuItem("一键插入模块信息表");
		MenuItem dbTableModelPrinterMenu = new MenuItem("打印");
		MenuItemSeparator separator = new MenuItemSeparator();
		MenuItem addTableModelMenu = new MenuItem("编辑此表/创建新表");
		MenuItem updateTableModelMenu = new MenuItem("更新");
		MenuItem delTableModelMenu = new MenuItem("删除");
		dbTableModelMenu.setItems(dbTableModelSearchMenu, dbTableModelPrinterMenu,dbTableModelInsertIntoModuleTableMenu,separator,addTableModelMenu,updateTableModelMenu);
		
		Menu moduleMenu = new Menu();
		MenuItem moduleSearchMenu = new MenuItem("搜索");
		MenuItem modulePrinterMenu = new MenuItem("打印");
//		MenuItem moduleSearchCondMenu = new MenuItem("设置搜索条件");
//		MenuItem addModuleMenu = new MenuItem("新增");
		MenuItem updateModuleMenu = new MenuItem("更新");
		MenuItem delModuleMenu = new MenuItem("删除");
		moduleMenu.setItems(moduleSearchMenu, modulePrinterMenu, separator,updateModuleMenu,delModuleMenu);

		Menu tableMenu = new Menu();
		MenuItem tableSearchMenu = new MenuItem("搜索");
		MenuItem tablePrinterMenu = new MenuItem("打印");
		MenuItem tableSearchCondMenu = new MenuItem("设置搜索条件");
		MenuItem updateTableMenu = new MenuItem("更新");
		MenuItem delTableMenu = new MenuItem("删除");
		tableMenu.setItems(tableSearchMenu, tablePrinterMenu, tableSearchCondMenu, separator,updateTableMenu,delTableMenu);

		
		dbTableModelListGrid.setContextMenu(dbTableModelMenu);
		moduleListGrid.setContextMenu(moduleMenu);
		tableListGrid.setContextMenu(tableMenu);
//		leftLayout.addMember(l1);
//		topLayout.addMember(l2);
//		bottomLayout.addMember(l3);
		topLayout.addMember(moduleListGrid);
		bottomLayout.addMember(tableListGrid);
		
        vLayout.addMembers(topLayout,bottomLayout);
        mainLayout.addMembers(leftLayout,vLayout);
        
        dbTableModelPrinterMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Canvas.showPrintPreview(dbTableModelListGrid);
				Object[] objects = { dbTableModelListGrid };
				Canvas.showPrintPreview(objects, null, "系统表信息", null);
			}
		});
        modulePrinterMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Canvas.showPrintPreview(moduleListGrid);
				Object[] objects = { moduleListGrid };
				Canvas.showPrintPreview(objects, null, "模块信息", null);
			}
		});
        
        
        tablePrinterMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Canvas.showPrintPreview(tableListGrid);
				Object[] objects = { tableListGrid };
				Canvas.showPrintPreview(objects, null, "表信息", null);
			}
		});
        
        
        addTableModelMenu.addClickHandler(new ClickHandler() {
        				@Override
        				public void onClick(MenuItemClickEvent event) {
        					ListGridRecord record = dbTableModelListGrid.getSelectedRecord();
        					String tableName = record !=null ? record.getAttribute("TABLE_NAME"):"";
        					new UpdateOrCreateNewTable(tableName).init();
        				}
        	        });
        
        updateTableModelMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("TABLE_NAME", lastDbTableModelListGridRecord.getAttribute("TABLE_NAME"));
				paramMap.put("COMMENTS", lastDbTableModelListGridRecord.getAttribute("COMMENTS"));
				
				dbModelService.updateTableInfo(paramMap, new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Integer result) {
						if(result>0) {
							dbModelService.queryCurDbUserTablesInfo(baseParamMap, new AsyncCallback<List<Map<String, String>>>(){

								@Override
								public void onFailure(Throwable caught) {
									SC.say(caught.getMessage());
								}

								@Override
								public void onSuccess(List<Map<String, String>> result) {
									if (result == null || result.isEmpty()) {
										dbTableModelListGrid.setData(new ListGridRecord[] {});
//										SC.say("没有符合条件的数据！");
									} else {
										Object rpcExceptionMessage = result.get(0).get("error");
										if (rpcExceptionMessage != null) {
											SC.say(rpcExceptionMessage.toString());
										} else {
											dbTableModelListGrid.setData(new ListGridRecord[] {});
											dbTableModelListGrid.setData(getRecords(result,dbTableModelFieldItems));

										}
									}
									
								}
								
							});
							SC.say("更新成功！");
						}else {
							SC.say("更新失敗！");
						}
					}
				});
				
			}
        });
        
        delTableModelMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("TABLE_NAME", lastDbTableModelListGridRecord.getAttribute("TABLE_NAME"));
				
				dbModelService.dropTableInfo(paramMap, new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(Integer result) {
						if(result>0) {
							dbTableModelListGrid.removeSelectedData();
							SC.say("刪除成功！");
						}else {
							SC.say("刪除失敗！");
						}
					}
				});
			}
        });
        
        //
        updateModuleMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("SMI_ID", lastsysModuleListGridRecord.getAttribute("SMI_ID"));
				paramMap.put("MODULE_NAME", lastsysModuleListGridRecord.getAttribute("MODULE_NAME"));
				paramMap.put("MUDULE_DESC", lastsysModuleListGridRecord.getAttribute("MUDULE_DESC"));
				
				dbModelService.updateSysModuleInfo(paramMap, new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(Integer result) {
						if(result>0) {
							SC.say("更新成功！");
						}else {
							SC.say("更新失敗！");
						}
					}
				});
			}
        });
        
        delModuleMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				/*Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("SMI_ID", lastsysModuleListGridRecord.getAttribute("SMI_ID"));*/
				ListGridRecord[] records = moduleListGrid.getSelectedRecords();
				for(final ListGridRecord record :records) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("SMI_ID", record.getAttribute("SMI_ID"));
					dbModelService.delSysModuleInfo(paramMap, new AsyncCallback<Integer>(){
	
						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.getMessage());
							
						}
	
						@Override
						public void onSuccess(Integer result) {
							if(result>0) {
								moduleListGrid.removeData(record);
								SC.say("刪除成功！");
							}else {
								SC.say("刪除失敗！");
							}
						}
					});
				}
			}
        });
        updateTableMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("STI_ID", lastsysTableListGridRecord.getAttribute("STI_ID"));
				paramMap.put("TABLE_COMMENTS", lastsysTableListGridRecord.getAttribute("TABLE_COMMENTS"));
				paramMap.put("REMARK", lastsysTableListGridRecord.getAttribute("REMARK"));
				
				dbModelService.updateSysTableInfo(paramMap, new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(Integer result) {
						if(result>0) {
							SC.say("更新成功！");
						}else {
							SC.say("更新失敗！");
						}
					}
				});
			}
        });
        
        delTableMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				/*Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("STI_ID", lastsysTableListGridRecord.getAttribute("STI_ID"));*/
				
				ListGridRecord[] records = tableListGrid.getSelectedRecords();
				for(final ListGridRecord record :records) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("STI_ID", record.getAttribute("STI_ID"));
					dbModelService.delSysTableInfo(paramMap, new AsyncCallback<Integer>(){

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.getMessage());
							
						}

						@Override
						public void onSuccess(Integer result) {
							if(result>0) {
								tableListGrid.removeData(record);
								SC.say("刪除成功！");
							}else {
								SC.say("刪除失敗！");
							}
						}
					});
				}
				
			}
        });
        
        //TODO 
        dbTableModelSearchMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				dbModelService.queryCurDbUserTablesInfo(paramMap, new AsyncCallback<List<Map<String, String>>>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<Map<String, String>> result) {
						if (result == null || result.isEmpty()) {
							dbTableModelListGrid.setData(new ListGridRecord[] {});
							SC.say("没有符合条件的数据！");
						} else {
							Object rpcExceptionMessage = result.get(0).get("error");
							if (rpcExceptionMessage != null) {
								SC.say(rpcExceptionMessage.toString());
							} else {
								dbTableModelListGrid.setData(new ListGridRecord[] {});
								dbTableModelListGrid.setData(getRecords(result,dbTableModelFieldItems));
								retListGrid = dbTableModelListGrid;
							}
						}
						
					}
					
				});
			}
		});
	
		
        dbTableModelInsertIntoModuleTableMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
				dbModelService.batchInsertAllTableInfotoModuleTable(paramMap, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							SC.say("插入成功！");
						}else {
							SC.say("插入失败！");
						}
						
					}});
			}
		});
        
        moduleSearchMenu.addClickHandler(new ClickHandler() {
        	
        	@Override
        	public void onClick(MenuItemClickEvent event) {
        		Map<String, String> paramMap = new HashMap<String, String>();
        		dbModelService.querySysModuleInfo(paramMap, new AsyncCallback<List<Map<String, String>>>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<Map<String, String>> result) {
						if (result == null || result.isEmpty()) {
							moduleListGrid.setData(new ListGridRecord[] {});
							SC.say("没有符合条件的数据！");
						} else {
							Object rpcExceptionMessage = result.get(0).get("error");
							if (rpcExceptionMessage != null) {
								SC.say(rpcExceptionMessage.toString());
							} else {
								moduleListGrid.setData(new ListGridRecord[] {});
								moduleListGrid.setData(getRecords(result,sysModuleFieldItems));

							}
						}
						
					}
					
				});
        	}
        });
        
        moduleListGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Map<String, String> paramMap = new HashMap<String, String>();
        		paramMap.put("SMI_ID", event.getRecord().getAttribute("SMI_ID"));
        		
        		dbModelService.querySysTableInfo(paramMap, new AsyncCallback<List<Map<String, String>>>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<Map<String, String>> result) {
						if (result == null || result.isEmpty()) {
							tableListGrid.setData(new ListGridRecord[] {});
							SC.say("没有符合条件的数据！");
						} else {
							Object rpcExceptionMessage = result.get(0).get("error");
							if (rpcExceptionMessage != null) {
								SC.say(rpcExceptionMessage.toString());
							} else {
								tableListGrid.setData(new ListGridRecord[] {});
								tableListGrid.setData(getRecords(result,sysTableFieldItems));

							}
						}
						
					}
					
				});
				
			}
        	
        });
        
        dbTableModelListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] records = event.getSelection();
				lastDbTableModelListGridRecord = event.getRecord();
			}
        	
        });
        
        moduleListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] records = event.getSelection();
				lastsysModuleListGridRecord = event.getRecord();
				lastClickSmiIds = "";
				for(ListGridRecord record:records) {
					lastClickSmiIds += record.getAttribute("SMI_ID")+",";
				}
				if(lastClickSmiIds.length()>0) {
					lastClickSmiIds = lastClickSmiIds.substring(0, lastClickSmiIds.length()-1);
				}
				
			}
        	
        });
        
        
        tableListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] records = event.getSelection();
				lastsysTableListGridRecord = event.getRecord();
			}
        	
        });
        
        
        tableSearchMenu.addClickHandler(new ClickHandler() {
        	
        	@Override
        	public void onClick(MenuItemClickEvent event) {
        		Map<String, String> paramMap = new HashMap<String, String>();
//        		paramMap.put("SMI_ID", lastClickSmiIds);
        		
        		dbModelService.querySysTableInfo(paramMap, new AsyncCallback<List<Map<String, String>>>(){

					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<Map<String, String>> result) {
						if (result == null || result.isEmpty()) {
							tableListGrid.setData(new ListGridRecord[] {});
							SC.say("没有符合条件的数据！");
						} else {
							Object rpcExceptionMessage = result.get(0).get("error");
							if (rpcExceptionMessage != null) {
								SC.say(rpcExceptionMessage.toString());
							} else {
								tableListGrid.setData(new ListGridRecord[] {});
								tableListGrid.setData(getRecords(result,sysTableFieldItems));

							}
						}
						
					}
					
				});
        	}
        });
        
		DynamicForm yzmForm = new DynamicForm();
		yzmForm.setTop(30);
		yzmForm.setHeight(30);
		yzmForm.setWidth(180);
		yzmForm.setPadding(0);
		yzmForm.setLayoutAlign(VerticalAlignment.TOP);
		yzmForm.setEdgeMarginSize(1);
		
		
		this.addItem(mainLayout);
        
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
				record.setAttribute(key, mapItem.get(key)); 
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
