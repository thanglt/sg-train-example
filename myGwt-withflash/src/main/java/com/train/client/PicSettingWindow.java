package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class PicSettingWindow extends Window{

	public PicSettingWindow() {
		
		setTitle("设置图片(修改后重新登录生效)");
		setWidth(680);
		setHeight(420);
		setAutoCenter(true);
		setCanDrag(false);
	    setCanDragResize(false);
	    setCanDragReposition(false);
	    setIsModal(true);  
	    setShowCloseButton(true);
	    setShowMinimizeButton(false);
	    setShowModalMask(true);
        setBackgroundColor("green");
	}
}
