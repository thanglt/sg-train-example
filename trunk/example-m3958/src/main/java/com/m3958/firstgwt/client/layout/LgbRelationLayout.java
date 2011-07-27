package com.m3958.firstgwt.client.layout;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class LgbRelationLayout extends VLayout{
	
	private List<ILgbRelationGrid> grids = new ArrayList<ILgbRelationGrid>();

	@Inject
	public LgbRelationLayout(AddressGrid address,HouseGrid house,FamilyGrid family,CareerGrid career,StepCareerGrid stepCareer,RewardGrid reward){
		grids.add(address.init());
		grids.add(house.init());
		grids.add(family.init());
		grids.add(career.init());
		grids.add(stepCareer.init());
		grids.add(reward.init());
		setWidth100();
		setHeight(130*grids.size());
		
		addMember(address);
		addMember(family);
		addMember(house);
		addMember(career);
		addMember(stepCareer);
		addMember(reward);
	}
	
	public void fetchData(String lgbId){
		for(ILgbRelationGrid lg : grids){
			lg.fetchData(lgbId);
		}
	}
	
	public void removeSelected(ViewNameEnum nextView){
		for(ILgbRelationGrid lg : grids){
			if(lg.getNextView() == nextView){
				lg.removeSelectedData();
				break;
			}
		}
	}

	public void disableAll() {
		for(ILgbRelationGrid lg : grids){
			lg.asGrid().setDisabled(true);
		}
	}

	public void invalidateCache(ViewNameEnum nextView) {
		for(ILgbRelationGrid lg : grids){
			if(lg.getNextView() == nextView){
				lg.asGrid().invalidateCache();
			}
		}
	}
}
