package com.m3958.firstgwt.client.layout;

import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class Cattachment extends HLayout{
	private DynamicForm form = new DynamicForm();
	private CheckboxItem cb = new CheckboxItem();
	private Img img;
	private Label fnLabel;
	
	private AssetJso aj;
	
	public Cattachment(AssetJso aj){
		setHeight(48);
		this.aj = aj;
		cb.setTitle("");
		form.setFields(cb);
		form.setWidth(25);
		addMember(form);
		if(StringUtils.isImageExt(aj.getFilePath())){
			img = new Img();
			img.setSrc(aj.getThumbnail());
			img.setWidth(48);
			img.setHeight(48);
			addMember(img);
			img.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(cb.getValueAsBoolean()){
						cb.setValue(false);
					}else{
						cb.setValue(true);
					}
				}
			});
		}else{
			fnLabel = new Label(aj.getOriginName());
			addMember(fnLabel);
		}
	}
	
	public AssetJso getAssetJso(){
		return aj;
	}
	
	public boolean isChecked(){
		if(cb.getValueAsBoolean()){
			return true;
		}
		return false;
	}
}
