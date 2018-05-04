package com.bht.banhuitong.client.prj;

import com.bht.banhuitong.client.BasePortlet;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.FileSpec;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class CreditPrjPortlet extends BasePortlet{
	public static String portletTitleName = "系统建模 -filter";
	private CreditPrjPortlet portletInstance;

	public CreditPrjPortlet getInstance() {
		if (portletInstance == null) {
			portletInstance = new CreditPrjPortlet();
		}
		return portletInstance;
	}
	public CreditPrjPortlet() {
		super(portletTitleName);
	}

	public void init() {

		final ListGrid countryGrid = new ListGrid();  
        countryGrid.setWidth(700);  
        countryGrid.setHeight(300);  
        countryGrid.setAlternateRecordStyles(true);  
        countryGrid.setDataSource(WorldXmlDS.getInstance());  
  
        ListGridField countryCodeField = new ListGridField("countryCode", "Code", 50);  
        ListGridField nameField = new ListGridField("countryName", "Country");  
        ListGridField capitalField = new ListGridField("capital", "Capital");  
        ListGridField continentField = new ListGridField("continent", "Continent");  
        ListGridField areaField = new ListGridField("area", "Area (km²)");  
        ListGridField populationField = new ListGridField("population", "Population");  
  
        countryGrid.setFields(countryCodeField, nameField, capitalField, continentField, areaField, populationField);  
  
        countryGrid.setAutoFetchData(true);  
        countryGrid.setShowFilterEditor(true);  
  
        AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new AdvancedCriteria[] {    
            new AdvancedCriteria("countryName", OperatorId.INOT_CONTAINS, "i"),    
            new AdvancedCriteria("capital", OperatorId.INOT_STARTS_WITH, "p")    
        });  
        countryGrid.setAllowFilterOperators(true);  
        countryGrid.setCriteria(criteria);  
      
        this.addItem(countryGrid);
        
        Menu contextMenu = new Menu();
        MenuItem downloadFileItem = new MenuItem("downloadFile");
        MenuItem saveFileItem = new MenuItem("saveFile");
        contextMenu.setItems(downloadFileItem,saveFileItem);
        
        countryGrid.setContextMenu(contextMenu);
        
        downloadFileItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record record =  countryGrid.getSelectedRecord();
				SC.say("length:"+record.getAttributes().length);
				countryGrid.getDataSource().downloadFile(record);
			}
        	
        });
        
        saveFileItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				FileSpec fileSpec =new FileSpec();
				fileSpec.setFileName("aaaa.xls");
				fileSpec.setFileType("xls");
				countryGrid.getDataSource().saveFile(fileSpec, "content");
				
			}
        	
        });
	}
	
}
