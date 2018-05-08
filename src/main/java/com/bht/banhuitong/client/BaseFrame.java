package com.bht.banhuitong.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bht.banhuitong.server.AccountService;
import com.bht.banhuitong.server.AccountServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragMoveEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;

public class BaseFrame {
	static RootPanel rootPanel;
	protected static Canvas canvasMain = new Canvas();
	protected static Label loginNameLabel = new Label();
	public static Canvas endCanvas = new Canvas();
	
	private final static AccountServiceAsync accountService = GWT.create(AccountService.class);
	
	public static Map<Integer,String> exceMap = new HashMap<Integer,String>();
	static {
		exceMap.put(0, "服务器端错误，请联系后台管理员！");
	}
	public static LinkedHashMap<String, BasePortlet> portlets = new LinkedHashMap<String, BasePortlet>();
	public static Map<String, String> winValuesMap = new LinkedHashMap<String, String>();
	
	/**
	 * 公共字段，如果值为true，则说明当前有数据正在加载中，暂不允许进行其他操作。
	 * 应用于加载量比较大的列表
	 */
	public static boolean isLoading = false;
	
	public static int hBarValue = 0;
	public static final Progressbar hBar = new Progressbar();
	public static final Label hBarLabel = new Label(); 
	public final static Label headerHBarLabel = new Label(); 
	
	public void initMainCanvas() {
		if (canvasMain == null) {
			canvasMain = new Canvas();
			canvasMain.setTop(10);
	        canvasMain.setHeight100();
	        canvasMain.setSnapResizeToAlign(true);
	        canvasMain.setBackgroundColor("#99ccff");
	        
			MainFrame.window.addItem(canvasMain);

		}
	}
	
	public static void initEndCanvas() {
		
		if (endCanvas == null) {
			endCanvas = new Canvas();
			endCanvas.setBottom(0);
	        endCanvas.setHeight("25");
	        endCanvas.setBackgroundColor("#DDDDDD");
	       
	        loginNameLabel = new Label("登录用户："+LoginWindow.loginName);
	        loginNameLabel.setHeight(25);
	        loginNameLabel.setLeft("90%");
	       
	        endCanvas.addChild(loginNameLabel);
	        MainFrame.menuLayout.setDisabled(false);
			MainFrame.window.addItem(endCanvas);

		}
		
		
	}
	
	public static void editEndCanvas() {

		accountService.getAccountInfo(new AsyncCallback<List<Map<String,String>>>(){

			@Override
			public void onFailure(Throwable caught) {
				new BasePortlet("").showErrorMessage(caught.getMessage());
			}

			@Override
			public void onSuccess(List<Map<String, String>> result) {
				if(result!=null&&!result.isEmpty()) {
					LoginWindow.loginName = result.get(0).get("LOGIN_NAME");
					BaseFrame.endCanvas.destroy();
					BaseFrame.endCanvas = null;

					BaseFrame.initEndCanvas();
				}else {
					SC.say("用户信息初始化失败！");
				}
			}
			
		});
		
	}
	
	/**
	 * 进度条设置（背景色及进度条宽度设置均无效）
	 */
	public void initProgressbar(){
		hBarValue = 0;
		hBar.setPercentDone(hBarValue);
		
		 new Timer() {  
             public void run() {  
            	 hBarValue += 1 + (int) (1 * Math.random());  
                 if (hBarValue > 100) {  
                	 hBarValue = 100; 
                 }
                 headerHBarLabel.setContents("");
                 headerHBarLabel.setContents("正在接受数据......");
                 hBar.setPercentDone(hBarValue); 
                 hBarLabel.setContents(hBarValue+"%");
                 if(hBarValue!=100)  {
                     schedule(5 + (int) (1 * Math.random()));  
                 }else {
                	 hBar.destroy();
                	 hBarLabel.destroy();
                	 headerHBarLabel.setContents("就绪");
                 }
             }  
         }.schedule(100);  
	
 		VLayout vlayout = new VLayout(4);  
 		vlayout.setWidth(300); 
 		vlayout.setTop(3);
 		vlayout.setHeight(22); 
 		HLayout hLayout = new HLayout();
 		hLayout.addMember(headerHBarLabel);
 		hLayout.addMember(hBar);
 		hLayout.addMember(hBarLabel);
 		vlayout.addMember(hLayout);
 		
		endCanvas.addChild(vlayout);
	}

	/**
	 * 更改主窗体内容
	 * 
	 * @param <T>
	 * @param class1
	 */
	public <T> void changeMainCanvas(BasePortlet portlet) {

		initMainCanvas();
		
		/**
		 * bug：非最佳解决方案。此项用来应对同一浏览器出现打开多个窗口登录不同用户，使得服务端只记录最后一个用户登录信息，
		 * 但浏览器客户端先登录但未关闭的浏览器右下角显示的用户名是之前用户而出现的bug（进行各种查询操作时用的是后一登录账户的信息，导致权限设置失效）
		 * 
		 * 此bug要求不高的系统，可不解决。可以通过权限大的用户他出重新登录规避。
		 * 
		 * 可考虑重构成:1，在调用所有方法前都重新构建一遍底端画布；
		 * 或者2,在调用任何非登录服务时，将当前页面登录时记录的登录名传入服务端，核对该用户是否还是登录状态。此时会直接要求用户重新登录；
		 */
		editEndCanvas();					
		
		
		winValuesMap.put(portlet.getTitle(), portlet.getTitle());

		if (portlets.get(portlet.getTitle()) != null) {
			portlets.get(portlet.getTitle()).setHeight(MainFrame.window.getHeight() - 76);
			portlets.get(portlet.getTitle()).setWidth(MainFrame.window.getWidth() - 12);
			portlets.get(portlet.getTitle()).setTop(0);
			portlets.get(portlet.getTitle()).setLeft(0);
			putPortlet(portlets.get(portlet.getTitle()));
			portlets.get(portlet.getTitle()).bringToFront();
			canvasMain.addChild(portlets.get(portlet.getTitle()));
			canvasMain.redraw();
		} else {
			portlet.setHeight(MainFrame.window.getHeight() - 76);
			portlet.setWidth(MainFrame.window.getWidth() - 12);
			putPortlet(portlet);
			canvasMain.addChild(portlet);
			portlet.bringToFront();

			canvasMain.draw();
		}
	}

	public void registerCanvasMainClickEvent() {
		canvasMain.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Object object = event.getSource();
				if (object instanceof BaseFrame) {
					BasePortlet portlet = (BasePortlet) object;
					portlets.put(portlet.getTitle(), portlet);
				}
			}
		});

		canvasMain.addDragMoveHandler(new DragMoveHandler() {

			@Override
			public void onDragMove(DragMoveEvent event) {
				Object object = event.getSource();
				if (object instanceof BaseFrame) {
					BasePortlet portlet = (BasePortlet) object;
					portlets.put(portlet.getTitle(), portlet);
				}
			}

		});
	}

	public Map<String, BasePortlet> getPortlets() {
		return portlets;
	}

	/**
	 * 水平平铺主窗体
	 */
	public void changeMainCanvasToHLayout() {
		int pSize = portlets.size();
		if (pSize == 0) {
			canvasMain.draw();
			return;
		}
		int i = 0;
		for (Portlet portlet : portlets.values()) {
			portlet.setWidth(MainFrame.window.getWidth() - 12);
			portlet.setHeight((MainFrame.window.getHeight() - 76) / pSize);
			portlet.setTop((MainFrame.window.getHeight() - 76) * i / pSize);
			portlet.setLeft(0);
			i++;
		}
		canvasMain.redraw();
	}

	/**
	 * 垂直平铺主窗体
	 */
	public void changeMainCanvasToVLayout() {
		int pSize = portlets.size();
		if (pSize == 0) {
			canvasMain.draw();
			return;
		}
		int i = 0;
		for (Portlet portlet : portlets.values()) {
			portlet.setWidth((MainFrame.window.getWidth() - 13) / pSize);
			portlet.setHeight(MainFrame.window.getHeight() - 76);
			portlet.setTop(0);
			portlet.setLeft((MainFrame.window.getWidth() - 12) * i / pSize);
			i++;
		}
		canvasMain.redraw();
	}

	/**
	 * 重叠主窗体
	 */
	public void changeMainCanvasToCardLayout() {
		int pSize = portlets.size();
		if (pSize == 0) {
			canvasMain.draw();
			return;
		}
		int i = 0;
		for (Portlet portlet : portlets.values()) {
			portlet.setWidth(MainFrame.window.getWidth() - 13);
			portlet.setHeight((MainFrame.window.getHeight() - 76) - i * 20);
			portlet.setTop(i * 20);
			portlet.setLeft(0);
			portlet.bringToFront();
			i++;
		}
		canvasMain.redraw();
	}

	/**
	 * 重新排列（回到初始平铺效果） 每行三个，流式排列
	 */
	public void returnInitMainCanvasToLayout() {
		int height = MainFrame.window.getHeight() - 76;
		int width = MainFrame.window.getWidth() - 12;
		int pSize = portlets.size();
		if (pSize == 0) {
			canvasMain.draw();
			return;
		}

		String[] keyArrays = portlets.keySet().toArray(new String[portlets.size()]);
		int cols = 3;
		int rows = pSize % cols == 0 ? pSize / cols : pSize / cols + 1;
		int perHeight = height / rows;
		int perWidth = width / cols;

		for (int i = 0; i < rows; i++) {
			for (int j = 1; (j <= cols && (i * cols + j) <= pSize); j++) {
				Portlet portlet = portlets.get(keyArrays[i * cols + j - 1]);
				portlet.setWidth(perWidth);
				if ((i * cols + j) == pSize) {
					portlet.setWidth(perWidth * (cols - j + 1));
				}
				portlet.setHeight(perHeight);
				portlet.setTop(i * perHeight);
				portlet.setLeft((j - 1) * perWidth);
				System.out.println("i:" + i + "j:" + j);
			}
		}
		canvasMain.redraw();
	}

	/**
	 * 关闭所有
	 */
	public void closeAllPortlets() {
		portlets.clear();
		canvasMain.destroy();
		canvasMain = null;
		endCanvas.destroy();
		endCanvas=  null;
		initMainCanvas();
		
		initEndCanvas();
	}

	public <T> Long putPortlet(BasePortlet portlet) {
		if (portlets.get(portlet.getTitle()) != null) {
			delPortlet(portlet.getTitle());
		}
		portlets.put(portlet.getTitle(), portlet);
		return Long.valueOf(portlets.size() + 1);
	}

	public int delPortlet(String key) {
		canvasMain.removeChild(portlets.get(key));
		portlets.remove(key);
		return 1;
	}
	
	public static <K, V> Entry<K, V> getTail(LinkedHashMap<K, V> map) {
	    Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
	    Entry<K, V> tail = null;
	    while (iterator.hasNext()) {
	        tail = iterator.next();
	    }
	    return tail;
	}
	
	public void download(String filename) {

		Frame frame = new Frame(GWT.getModuleBaseURL() + "download?filename=" + filename);
		frame.setVisible(false);
		frame.setSize("0px", "0px");
		RootPanel.get().add(frame);
	}
	
}
