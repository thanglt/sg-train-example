package com.m3958.firstgwt.client.layout;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;

public class CattachmentContainer extends HLayout{
	
	private List<Cattachment> cas = new ArrayList<Cattachment>();
	
	public CattachmentContainer(){
		setOverflow(Overflow.VISIBLE);
	}
	
	public JSONArray getJsonArray(){
		JSONArray ja = new JSONArray();
		for(int i = 0;i<cas.size();i++){
			ja.set(i, new JSONObject(cas.get(i).getAssetJso()));
		}
		return ja;
	}

	
	public void setCattachment(AssetJso aj){
		for(Cattachment a:cas){
			removeMember(a);
		}
		cas.clear();
		if(aj == null)return;
		Cattachment ca = new Cattachment(aj);
		addMember(ca);
		cas.add(ca);
	}
	
	public void setCattachments(JsArray<AssetJso> ajs){
		for(Cattachment a:cas){
			removeMember(a);
		}
		cas.clear();
		for(int i=0;i<ajs.length();i++){
			Cattachment ca = new Cattachment(ajs.get(i));
			addMember(ca);
			cas.add(ca);
		}
	}

	public void addCattachment(AssetJso aj){
		Cattachment ca = new Cattachment(aj);
		addMember(ca);
		cas.add(ca);
	}

	
	public void addCattachments(JsArray<AssetJso> ajs){
		for(int i=0;i<ajs.length();i++){
			addCattachment(ajs.get(i));
		}
	}
	
	
	public void removeSelectedCattachment(){
		List<Cattachment> removed = new ArrayList<Cattachment>();
		for(Cattachment ca:cas){
			if(ca.isChecked()){
				removed.add(ca);
				removeMember(ca);
			}
		}
		cas.removeAll(removed);
	}
	
	public void clearCattachments(){
		for(Cattachment ca:cas){
			removeMember(ca);
		}
		cas.clear();
	}
	
	public List<Cattachment> getChecked(){
		List<Cattachment> checked = new ArrayList<Cattachment>();
		for(Cattachment ca:cas){
			if(ca.isChecked()){
				checked.add(ca);
			}
		}
		return checked;
	}
	
	public List<Cattachment> getCattachments(){
		return cas;
	}
}
