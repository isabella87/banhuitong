package com.bht.banhuitong.client.files;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.client.BaseFrame;
import com.bht.banhuitong.client.BasePortlet;
import com.bht.banhuitong.server.FileService;
import com.bht.banhuitong.server.FileServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

public class FileModulePortlet extends BasePortlet {

	private static Map<String, String> fileFieldItems = new LinkedHashMap<String, String>();
	private static String portletTitleName = "文件系统 -文件上传下载";

	private static final FileServiceAsync fileService = GWT.create(FileService.class);

	private FileModulePortlet portletInstance;

	public FileModulePortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new FileModulePortlet();
		}
		return portletInstance;
	}

	public FileModulePortlet() {
		super(portletTitleName);
	}

	static {
		fileFieldItems.put("uLink", "下载地址");
	}

	/**
	 * 初始化界面
	 */
	public void init() {

		HLayout mainLayout = new HLayout();
		mainLayout.setTop(45);
		mainLayout.setLeft(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		HLayout leftLayout = new HLayout();
		leftLayout.setWidth("30%");
		leftLayout.setOverflow(Overflow.HIDDEN);
		leftLayout.setShowResizeBar(true);

		VLayout vLayout = new VLayout();
		vLayout.setWidth("70%");
		vLayout.setOverflow(Overflow.HIDDEN);

		final TreeGrid dirsTree = new TreeGrid();
		dirsTree.setShowRoot(true);
		dirsTree.setCanDrag(true);
		dirsTree.setWidth100();
		dirsTree.setHeight100();
		dirsTree.setShowConnectors(true);

		final ListGrid fileListGrid = new ListGrid();
		fileListGrid.setHeight100();
		fileListGrid.setWidth100();
		// fileListGrid.setTop(20);
		// fileListGrid.setLeft(5);
		fileListGrid.setShowAllRecords(true);
		fileListGrid.setShowEmptyMessage(true);
		fileListGrid.setEmptyMessage("请点击<b>左边目录节点</b>查询该节点下的文件！");
		fileListGrid.setAutoFetchData(true);
		fileListGrid.setCanSelectCells(true);
		fileListGrid.setCanDragSelect(true);
		fileListGrid.setCanRemoveRecords(true);

		initListGridFields(fileListGrid, fileFieldItems, emptyArrayList);

		SectionStack leftSideLayout = new SectionStack();
		leftSideLayout.setWidth(280);
		leftSideLayout.setShowResizeBar(true);
		leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		leftSideLayout.setAnimateSections(true);

		SectionStackSection suppliesCategorySection = new SectionStackSection("目录树结构");
		suppliesCategorySection.setExpanded(true);
		suppliesCategorySection.setItems(dirsTree);

		SectionStackSection instructionsSection = new SectionStackSection("Instructions");
		// instructionsSection.setItems(new HelpPane());
		instructionsSection.setExpanded(true);

		leftSideLayout.setSections(suppliesCategorySection);

		SectionStack rightSideLayout = new SectionStack();
		rightSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		rightSideLayout.setAnimateSections(true);

		/*
		 * SectionStackSection findSection = new SectionStackSection("Find Items"); //
		 * findSection.setItems(searchForm); findSection.setExpanded(true);
		 */

		SectionStackSection supplyItemsSection = new SectionStackSection("文件列表");
		supplyItemsSection.setItems(fileListGrid);
		supplyItemsSection.setExpanded(true);

		// itemDetailTabPane = new ItemDetailTabPane(supplyItemDS, supplyCategoryDS,
		// itemList);
		SectionStackSection itemDetailsSection = new SectionStackSection("Item Details");
		// itemDetailsSection.setItems(itemDetailTabPane);
		itemDetailsSection.setExpanded(true);

		rightSideLayout.setSections(supplyItemsSection);

		leftLayout.addMember(dirsTree);
		vLayout.addMember(fileListGrid);

		mainLayout.addMembers(leftLayout, vLayout);
		this.addItem(mainLayout);

		fileService.queryDirList(new AsyncCallback<Map<String, String>>() {

			@Override
			public void onFailure(Throwable caught) {
				showErrorMessage(caught.getMessage());

			}

			@Override
			public void onSuccess(Map<String, String> result) {
				if (result == null || result.isEmpty()) {
					dirsTree.setData(new Tree());
					SC.say("没有符合条件的数据！");
				} else {
					dirsTree.setData(new Tree());
					dirsTree.setData(getTreeData(result));
				}

			}

		});

		dirsTree.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				TreeNode treeNode = dirsTree.getSelectedRecord();
				String path = treeNode.getAttribute("urlLink");
				fileService.queryFileLinksByDir(path, new AsyncCallback<Map<String, String>>() {

					@Override
					public void onFailure(Throwable caught) {
						showErrorMessage(caught.getMessage());

					}

					@Override
					public void onSuccess(Map<String, String> result) {
						if (result == null || result.isEmpty()) {
							fileListGrid.setData(new ListGridRecord[] {});
							fileListGrid.setContents("没有内容");
							// SC.say("没有符合条件的数据！");
						} else {
							fileListGrid.setData(new ListGridRecord[] {});
							fileListGrid.setData(getRecords(result));
							retListGrid = fileListGrid;
						}
					}
				});
			}
		});

		fileListGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {

			@Override
			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
				ListGridRecord record = fileListGrid.getRecord(event.getRowNum());
				String uLink = record.getAttribute("hideLink");
				fileService.deleteFile(uLink, false, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("网络错误，删除失败！");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {

							SC.say("删除成功！");
						} else {
							SC.say("删除失败！");
						}
					}
				});
			}
		});

		Menu treeMenu = new Menu();

		MenuItem addTreeMenu = new MenuItem("新增");
		addTreeMenu.setIcon("actions/add.png");

		MenuItem delTreeMenu = new MenuItem("删除");
		delTreeMenu.setIcon("actions/remove.png");
		treeMenu.setItems(addTreeMenu, delTreeMenu);

		dirsTree.setContextMenu(treeMenu);

		addTreeMenu.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				TreeNode treeNode = dirsTree.getSelectedRecord();
				String allPath = treeNode.getAttribute("urlLink");
				String pName = treeNode.getName();
				new CreateDirWindow(pName, allPath).init();
			}
		});

		delTreeMenu.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				TreeNode treeNode = dirsTree.getSelectedRecord();

				if (treeNode == null) {
					SC.say("请选择一个节点删除！");
					return;
				}
				final String uLink = treeNode.getAttribute("urlLink");

				fileService.queryFileLinksByDir(uLink, new AsyncCallback<Map<String, String>>() {

					@Override
					public void onFailure(Throwable caught) {
						showErrorMessage(caught.getMessage());

					}

					@Override
					public void onSuccess(Map<String, String> result) {
						if (result != null && !result.isEmpty()) {
							SC.say("原节点下有文件，不能删除！");
						} else {
							fileService.deleteFile(uLink, false, new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									SC.say("网络错误，删除失败！");
								}

								@Override
								public void onSuccess(Boolean result) {
									if (result) {
										dirsTree.removeSelectedData();

										SC.say("删除成功！");
									} else {
										SC.say("删除失败！");
									}
								}
							});
						}
					}
				});
			}
		});

		Menu menu = new Menu();

		MenuItem refreshMenu = new MenuItem("刷新");

		MenuItem downloadMenu = new MenuItem("下载");

		MenuItem uploadMenuItem = new MenuItem("上传");

		MenuItem delMenuItem = new MenuItem("删除");

		menu.setItems(refreshMenu, downloadMenu, uploadMenuItem);

		fileListGrid.setContextMenu(menu);

		refreshMenu.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				TreeNode treeNode = dirsTree.getSelectedRecord();
				String path = treeNode.getAttribute("urlLink");
				fileService.queryFileLinksByDir(path, new AsyncCallback<Map<String, String>>() {

					@Override
					public void onFailure(Throwable caught) {
						showErrorMessage(caught.getMessage());

					}

					@Override
					public void onSuccess(Map<String, String> result) {
						if (result == null || result.isEmpty()) {
							fileListGrid.setData(new ListGridRecord[] {});
							fileListGrid.setContents("没有内容");
							// SC.say("没有符合条件的数据！");
						} else {
							fileListGrid.setData(new ListGridRecord[] {});
							fileListGrid.setData(getRecords(result));
							retListGrid = fileListGrid;
						}
					}
				});
			}
		});

		downloadMenu.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				ListGridRecord record = fileListGrid.getSelectedRecord();
				if (record == null) {
					SC.say("请选择一个文件下载！");
					return;
				}
				String uLink = record.getAttribute("hideLink");
				new BaseFrame().download(uLink);
			}
		});

		uploadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				TreeNode treeNode = dirsTree.getSelectedRecord();
				String path = treeNode.getAttribute("urlLink");
				new UploadWindow(path).init();
			}
		});

		delMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final ListGridRecord record = fileListGrid.getSelectedRecord();
				if (record == null) {
					SC.say("请选择一个文件删除！");
					return;
				}
				String uLink = record.getAttribute("hideLink");

				fileService.deleteFile(uLink, false, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("网络错误，删除失败！");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							fileListGrid.removeData(record);

							SC.say("删除成功！");
						} else {
							SC.say("删除失败！");
						}
					}

				});
			}
		});

	}

	protected Tree getTreeData(Map<String, String> result) {
		Tree tree = new Tree();
		TreeNode rootTreeNode = new TreeNode();
		for (String key : result.keySet()) {
			String[] values = result.get(key).split(",");
			if (values[0].equals("0")) {
				rootTreeNode.setName(key);
				rootTreeNode.setAttribute("urlLink", values[1]);
				break;
			}
		}

		addTreeChilds(rootTreeNode, result);

		tree.setRoot(rootTreeNode);
		return tree;
	}

	private void addTreeChilds(TreeNode parentTreeNode, Map<String, String> result) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (String key : result.keySet()) {
			TreeNode treeNode = new TreeNode();
			String[] values = result.get(key).split(",");

			if (values[0].equals(parentTreeNode.getName())) {
				treeNode.setName(key);
				treeNode.setAttribute("urlLink", values[1]);

				treeNodeList.add(treeNode);
			}
		}

		parentTreeNode.setChildren(treeNodeList.toArray(new TreeNode[treeNodeList.size()]));

		for (TreeNode tn : treeNodeList) {
			addTreeChilds(tn, result);
		}
	}

	private ListGridRecord[] getRecords(Map<String, String> result) {

		int length = result.size();
		ListGridRecord[] listRecords = new ListGridRecord[length];
		int i = 0;
		for (String key : result.keySet()) {
			String item = result.get(key);
			listRecords[i] = createRecord(key, item, i);
			i++;
		}

		return listRecords;
	}

	private ListGridRecord createRecord(String key, String item, int i) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute("id", ++i);
		record.setAttribute("uLink", key);
		record.setAttribute("hideLink", item);
		return record;
	}
}
