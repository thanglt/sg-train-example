package com.m3958.firstgwt.client.window;


import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class ImgSizeMenuButton extends ToolStripMenuButton{
	
	private Menu menu;
	
	public ImgSizeMenuButton(String title){
		super(title);
        menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);
	}
	
	public void addItemClickHandler(ItemClickHandler handler){
		menu.addItemClickHandler(handler);
	}

	public Menu getMenu() {
		return menu;
	}

}
