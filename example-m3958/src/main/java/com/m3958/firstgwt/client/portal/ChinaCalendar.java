package com.m3958.firstgwt.client.portal;

import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.Portlet;

public class ChinaCalendar extends Portlet{
	
	
	public ChinaCalendar(){
		setTitle("中国农历日历");
		HTMLPane hp = new HTMLPane();
		hp.setIFrameURL("/chinaCalendar.html");
		addItem(hp);
	}

//	private void addCgadgat(Canvas c) {
//		IFrameElement ife = Document.get().createIFrameElement();
//		ife.setFrameBorder(0);
//		ife.setAttribute("width", "400");
//		ife.setAttribute("height", "800");
//		
//		ife.setSrc("http://www.gmodules.com/gadgets/ifr?url=http://www.google.com/ig/modules/chinagadgets/chinacalendar/chinacalendar.xml&amp;up_isMonthCal=1&amp;synd=open&amp;w=320&amp;h=300&amp;title=__MSG_title__&amp;lang=zh-CN&amp;country=ALL&amp;border=%23ffffff%7C3px%2C1px+solid+%23999999&amp;output=js");
//		
//		c.getElement().appendChild(ife);
//		
//		
//	}

}
