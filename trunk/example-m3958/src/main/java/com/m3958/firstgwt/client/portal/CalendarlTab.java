package com.m3958.firstgwt.client.portal;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class CalendarlTab {
	
	private Tab pTab;
	
	public CalendarlTab(){
		pTab.setTitle("Home&nbsp;&nbsp;");
		pTab.setCanClose(false);
		
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		
		HLayout calendarLayout = initCalendarLayout();
		
		layout.addMember(calendarLayout);
		pTab.setPane(layout);
	}
	
	public Tab getTab(){
		return pTab;
	}
	
	
	private HLayout initCalendarLayout() {
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
		
		Calendar calendar = new Calendar();
		calendar.setWorkdays(new int[]{0,1,2,3,4,5,6});
		calendar.setShowWorkday(true);
		calendar.setWorkdayStart("7:00am");
		calendar.setWorkdayEnd("6:00pm");
		calendar.setScrollToWorkday(true);
		
		Criteria defaultCriteria = new Criteria();
		calendar.setAutoFetchData(false);
		
		calendar.fetchData(defaultCriteria);
		
		layout.addMember(calendar);
		return layout;
	}

}
