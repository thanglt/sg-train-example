package com.m3958.firstgwt.client.portal;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.layout.PortalLayout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class PortalTab {
	
	private Tab pTab;
	
	private PortalLayout pl;
	
	private int pNum = 0;
	
	private final int ROWS = 4;
	
//	java.lang.Object
//	  com.google.gwt.core.client.JavaScriptObject
//	      com.google.gwt.dom.client.Node
//	          com.google.gwt.dom.client.Element
//-----------------------------------------------------------------------	
//	java.lang.Object
//	  com.google.gwt.core.client.JavaScriptObject
//	      com.google.gwt.dom.client.Node
//	          com.google.gwt.dom.client.Element//这个是native element，不能直接创建。
//	              com.google.gwt.user.client.Element DOM操作的都是这个对象，不能是上面的Element对象。
//	An opaque handle to a native DOM Element. An Element cannot be created directly. 
//	Instead, use the Element type when returning a native DOM element from JSNI methods. 
//	An Element passed back into JSNI becomes the original DOM element the Element was created from, 
//	and can be accessed in JavaScript code as expected. This is typically done by calling methods in the DOM class.
	
	public PortalTab(){
		pTab = new Tab();
		pTab.setTitle("Home&nbsp;&nbsp;");
		pTab.setCanClose(false);
		
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		pl = new PortalLayout();
		pl.setShowColumnMenus(false);
		pl.setColumnBorder("0");
		addPortlets();
//		layout.setID("xxxx");
//		SC.say(layout.getElement().getId());
//		Element script = DOM.createElement("script");
//		DOM.setElementProperty(script, "src", "/abc.js");
//		
//		DOM.appendChild(layout.getElement(), script);
		
		layout.addMember(initTsp());
		layout.addMember(pl);
		
//		pl.addDropHandler(new DropHandler() {
//			
//			@Override
//			public void onDrop(DropEvent event) {
//				SC.say("droped");
//			}
//		});
		pTab.setPane(layout);
	}
	
	@SuppressWarnings("unused")
	private Timer t;
	
	private void addPortlets() {
//			t = new Timer() {
//				@Override
//				public void run() {
//					try {
		HmessagePorlet hp = new HmessagePorlet();
		addPortlet(hp);
		ChinaCalendar cc = new ChinaCalendar();
		addPortlet(cc);
//		WeatherPortlet wp = new WeatherPortlet();
//		addPortlet(wp);
//					} catch (Exception e) {
//						t.cancel();
//					}
//				}
//			};
//			t.scheduleRepeating(1500);
	}
	
	private void addPortlet(Portlet p){
		
		int col = pl.getNumColumns();
		int ncol = pNum/ROWS;
		int m = pNum % ROWS;
		if(ncol == col && m >= 0){
			pl.addColumn(col);
			pl.addPortlet(p, col+1, m);
		}else{
			pl.addPortlet(p, ncol, m);
		}
		pNum++;
//		if(pNum >1)t.cancel();
	}
	
	private ToolStrip initTsp() {
		ToolStrip portletToolStrip = new ToolStrip();
		portletToolStrip.setWidth100();
        return portletToolStrip;
	}


	public Tab getTab(){
		return pTab;
	}

}
