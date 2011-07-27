package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.gin.MainMenuGrid;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.service.MainMenuToViewService;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class DefaultMainMenuLayout extends VLayout implements Iview{

	private  SectionStack stack;
	
	private Map<String, String> menuSectionMap = new LinkedHashMap<String, String>();
	
	private Map<String, Record> mainMenus = new HashMap<String, Record>();
	
	private Map<Record,ListGrid> menuGridMap = new HashMap<Record, ListGrid>();
	
	private Provider<ListGrid> mmProvider;
	
	private boolean initialzied = false;
	
	private AppStatusService aservice;
	
	private MainMenuToViewService ameh;
	
	@Inject
	public DefaultMainMenuLayout(@MainMenuGrid Provider<ListGrid> mmProvider,
			AppStatusService aservice,MainMenuToViewService ameh){
		this.mmProvider = mmProvider;
		this.aservice = aservice;
		this.ameh = ameh;
		setWidth100();
		setHeight100();
	}

	private void initStack() {
		stack =  new SectionStack();
		stack.setVisibilityMode(VisibilityMode.MULTIPLE);
		stack.setAnimateSections(true);
		stack.setOverflow(Overflow.HIDDEN);
		
		Map<MenuItemCategory, String> mmnameMap = new LinkedHashMap<MenuItemCategory, String>();
		mmnameMap.put(MenuItemCategory.BASE, "程序设定");
		mmnameMap.put(MenuItemCategory.OA, "网站管理");
		
		Map<MenuItemCategory, JSONArray> mmitems = aservice.getMenuItemSections();
		SectionStackSection last = null;
		for(MenuItemCategory mic : mmitems.keySet()){
			JSONArray ja = mmitems.get(mic);
			if(ja.size() > 0){
				ListGrid lg = mmProvider.get();
				SectionStackSection section = new SectionStackSection();
				Record[] records = new Record[ja.size()];
				for(int i=0;i < ja.size();i++){
					JSONObject jo = ja.get(i).isObject();
					Record r = new Record(jo.getJavaScriptObject());
					records[i] = r;
					String itn = r.getAttributeAsString(MenuItemField.UNIQUE_NAME.getEname());
					mainMenus.put(itn, r);
					menuGridMap.put(r, lg);
					menuSectionMap.put(itn, mic.toString());
				}
				
				lg.setData(records);
				lg.addRecordClickHandler(new RecordClickHandler() {
					@Override
					public void onRecordClick(RecordClickEvent event) {
						MainMenuEnum mme = MainMenuEnum.NO_VALUE;
						Record r = event.getRecord();
						try {
							mme = MainMenuEnum.getEnumByValue(r.getAttributeAsString(MenuItemField.UNIQUE_NAME.getEname()));
							ameh.newViewState(mme);
						} catch (Exception e) {
							e.printStackTrace();
						}
						event.cancel();
					}
				});
				
				section.setTitle(mmnameMap.get(mic));
				section.setID(mic.toString());
				section.addItem(lg);
				stack.addSection(section);
				last = section;
			}
		}
		if(last !=null)
			last.setExpanded(true);
		addMember(stack);
	}
	
	
	@Override
	public void changeDisplay(ViewAction va,String...paras) {
		if(!initialzied){
			initStack();
			initialzied = true;
		}
		if(paras != null && paras.length > 0){
			String menuId = paras[0];
			Record r = mainMenus.get(menuId);
			if(r == null)return;
			ListGrid lg = menuGridMap.get(r);
			stack.expandSection(menuSectionMap.get(menuId));
			lg.deselectAllRecords();
			lg.selectRecord(r);
		}else{
			stack.expandSection(MenuItemCategory.OA.toString());
		}
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.MMENU;
	}

	@Override
	public Layout asLayout() {
		return this;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public void setUnInitialized() {
		if(stack != null){
			removeMember(stack);
			stack = null;
		}
		initialzied = false;
	}
}
