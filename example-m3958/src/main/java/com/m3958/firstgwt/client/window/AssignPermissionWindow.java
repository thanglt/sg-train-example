package com.m3958.firstgwt.client.window;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

@Singleton
public class AssignPermissionWindow extends Window{
	
	private HasPermissionGrid leftGrid;
	private OwnedPermissionGrid ownedSrcGrid;
	private GivenPermissionGrid givenSrcGrid;
	private PermissionTransferBar transferBar;
	
	private boolean ownedGridFetched = false;
	private boolean givenGridFetched = false;

	private String rid;
	
	@Inject
	public AssignPermissionWindow(HasPermissionGrid leftGrid,OwnedPermissionGrid ownedSrcGrid,GivenPermissionGrid givenSrcGrid,PermissionTransferBar transferBar) {
		this.leftGrid = leftGrid;
		this.ownedSrcGrid = ownedSrcGrid;
		this.givenSrcGrid = givenSrcGrid;
		this.transferBar = transferBar;
        setWidth(600);
        setHeight(450);
        setIsModal(true);
        setShowModalMask(true);
        centerInPage();
        setAlign(Alignment.CENTER);
        setCanDragReposition(true);
        setCanDragResize(true);
        setSnapOffsetTop(55);
        setSnapTo("T");
		addItem(initContent());
	}
	
	
	public void showme(String rid,String title){
		setTitle(title);
		if(!rid.equals(this.rid)){
			this.rid = rid;
			ownedGridFetched = false;
			givenGridFetched = false;
			leftGrid.myFetchData(rid);
			ownedSrcGrid.myFetchData();
			ownedGridFetched = true;
			transferBar.setup(rid, ownedSrcGrid);
			ts.selectTab("ownedPermissionTab");
		}
		show();
	}
	
	private HLayout initContent(){
		HLayout layout = new HLayout(10);
		layout.setWidth100();
		layout.setHeight100();
		layout.setAlign(Alignment.CENTER);
		layout.addMember(initLeft());
		layout.addMember(transferBar);
		layout.addMember(initRight());
        return layout;
	}
	
	private Canvas initLeft() {
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		Label l = new Label("已有权限");
		l.setHeight(20);
		layout.addMember(l);
		
		layout.addMember(leftGrid);
		return layout;
	}

	private TabSet ts = new TabSet();

	private Canvas initRight() {
		ts.setWidth100();
		ts.setHeight100();
		Tab tab = new Tab("我创建的权限");
		tab.setID("ownedPermissionTab");
		tab.setPane(ownedSrcGrid);
		Tab tab1 = new Tab("我拥有的权限");
		tab1.setPane(givenSrcGrid);
		tab1.setID("givenPermissionTab");
		ts.addTab(tab);
		ts.addTab(tab1);
		
		ts.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				String tid = event.getID();
				if("ownedPermissionTab".equals(tid)){
					if(!ownedGridFetched){
						ownedSrcGrid.myFetchData();
						ownedGridFetched = true;
						transferBar.setup(rid, ownedSrcGrid);
					}
				}else{
					if(!givenGridFetched){
						givenSrcGrid.myFetchData();
						givenGridFetched = true;
						transferBar.setup(rid, givenSrcGrid);
					}
				}
			}
		});
		return ts;
	}
}
