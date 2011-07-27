package com.m3958.firstgwt.client.slots;

import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;

public interface ISlot {
	void onApplicationEvent();
	void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras);
	void onStripEvent(ViewNameEnum vname,Btname sourceBt);
	void hiddenAllMembers();
	String getName();
}
