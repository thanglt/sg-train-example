package com.skynet.spms.datasource.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skynet.spms.datasource.DataSourceAction;
import com.skynet.spms.datasource.GetEntityCls;

@Component
public class TestUnlimitListAction implements DataSourceAction<MockEntity>,GetEntityCls{

	private Logger log=LoggerFactory.getLogger(TestUnlimitListAction.class);
	
	@Override
	public String[] getBindDsName() {

		return new String[]{"mockTestPage"};
	}

	@Override
	public void insert(MockEntity item) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public MockEntity update(Map<String, Object> newValues, String itemID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String itemID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<MockEntity> doQuery(Map<String, Object> values, int startRow,
			int endRow) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<MockEntity> getList(int startRow, int endRow) {
		
		log.info("start Row:"+startRow+" endRow:"+endRow);
		
		List<MockEntity> list=new ArrayList<MockEntity>();
		
		endRow=Math.min(10000, endRow);
		
		for(int i=startRow;i<endRow;i++){
			MockEntity info=new MockEntity();
			info.setCategory("category "+i%5);
			info.setDescription("description "+i%7);
			info.setItemID(new Long(i));
			info.setItemName("name"+i%3);			

			info.setSKU("SKU");
			
			MockItem item=new MockItem();
			item.setUnitCost(100.0*i*i/37.5);
			item.setUnits("Bag");
			item.setInStock(((i%2)==0)?true:false);
			item.setNextShipment(DateUtils.addDays(new Date(), i));
			info.setItem(item);
			
			List<SubItem> subList=new ArrayList<SubItem>();
			for(int idx=0;idx<i%3;idx++){
				SubItem sub=new SubItem();
				sub.setDate(new Date());
				sub.setId(idx+i*100);
				sub.setName("sub Name "+idx+" from mock:"+i);
				subList.add(sub);
			}
			info.setItemList(subList);
			SubItem subItem=new SubItem();
			subItem.setId(i*100);
			subItem.setName("sub name"+i);
			info.setSubItem(subItem);
			
			list.add(info);
		}
		
		return list;
	
	}

	@Override
	public Class getEntityClass() {
		return MockEntity.class;
	}

}
