package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.SelectedRecordService;
import com.m3958.firstgwt.client.event.LgbRelationEventHandler;
import com.m3958.firstgwt.client.event.LgbRelationGridEvent;
import com.m3958.firstgwt.client.event.LgbRelationGridEvent.EvType;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public abstract class LgbRelationGridImpl extends VLayout implements ILgbRelationGrid{

	protected ListGrid grid;
	protected Label lb = new Label();
	protected Label activeLb;
	
	protected boolean isActive;
	
	@Inject
	protected MyEventBus eventBus;
	
	@Inject
	protected AppUtilService auService;
	
	@Inject
	protected AppUiService auiService;
	
	@Inject
	protected SelectedRecordService srservice;
	
	
	public ILgbRelationGrid init(){
		lb.setContents(getLbname());
		grid  = new ListGrid();
		setWidth100();
		setHeight(130);
		initGrid();
		initActiveLb();
		HLayout hl = new HLayout(10);
		hl.setHeight(20);
		hl.addMember(lb);
		hl.addMember(activeLb);
		addMember(hl);
		addMember(grid);
		eventBus.addHandler(LgbRelationGridEvent.TYPE,leh);
		grid.addSelectionChangedHandler(sch);
		return this;
	}

	private void initActiveLb() {
		activeLb = new Label("<b>点击激活列表</b>");
		activeLb.setCursor(Cursor.HAND);
		activeLb.addClickHandler(lch);
	}

	protected ClickHandler lch = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			LgbRelationGridEvent lge = new LgbRelationGridEvent(getNextView(),EvType.ACTIVE);
			eventBus.fireEvent(lge);
		}
	};
	
	protected LgbRelationEventHandler leh = new LgbRelationEventHandler() {
		@Override
		public void onLgbGridEvent(LgbRelationGridEvent event) {
			if(event.getNextView() == getNextView()){
				setBorder("2px solid red");
				activeLb.hide();
				isActive = true;
				grid.setDisabled(false);
			}else{
				isActive = false;
				grid.setDisabled(true);
				setBorder("");
				activeLb.show();
				grid.deselectAllRecords();
			}
		}
	};
	
	protected SelectionChangedHandler sch = new SelectionChangedHandler() {
		@Override
		public void onSelectionChanged(SelectionEvent event) {
			if(isActive){
				Record[] rs = event.getSelection();
				if(rs.length == 1){
					srservice.putRecord(rs[0].getAttributeAsString("id"), rs[0]);
				}
				LgbRelationGridEvent re = new LgbRelationGridEvent(getNextView(),EvType.SELECT_CHANGE,auService.getRecordsIds(rs));
				eventBus.fireEvent(re);
			}
		}
	};
	
	public void removeSelectedData(){
		grid.removeSelectedData();
	}
	
	public ListGrid asGrid(){
		return grid;
	}
	
	protected abstract void initGrid();
}
