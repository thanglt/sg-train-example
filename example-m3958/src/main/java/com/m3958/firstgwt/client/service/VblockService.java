package com.m3958.firstgwt.client.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.Vblock;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.ApplicationChangeEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.StringUtils;

@Singleton
public class VblockService {
	
	private String timeZone;
	
	public List<Vblock> getBlocks() {
		return blocks;
	}

	public static class Sperator{
		public static String BLOCK_INNER_SPERATOR = "/";
		public static String BLOCK_SPERATOR = ",";
		public static String PA_SPERATOR = "!";
		public static String P_INNER_SPERATOR = "=";
		public static String P_SPERATOR = ",";
	}
	
	private String siteId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	@Inject
	private MyEventBus eventBus;
	
	private List<String> tokenList = new ArrayList<String>();
	public Vblock b = new Vblock(BlockName.B);
	public Vblock c1 = new Vblock(BlockName.C1); 
	public Vblock c2 = new Vblock(BlockName.C2);
	public Vblock d1 = new Vblock(BlockName.D1);
	public Vblock d2 = new Vblock(BlockName.D2);
	public Vblock e1 = new Vblock(BlockName.E1);
	public Vblock e2 = new Vblock(BlockName.E2);
	public Vblock f1 = new Vblock(BlockName.F1);
	public Vblock f2 = new Vblock(BlockName.F2);
	public Vblock g1 = new Vblock(BlockName.G1);
	public Vblock g2 = new Vblock(BlockName.G2);
	
	
	
	private List<Vblock> blocks = new ArrayList<Vblock>();
	
	private Long lastMouseMoveTimeStamp;
	
	public VblockService(){
		addToBlocks();
	}
	
	private void addToBlocks() {
		blocks.add(b);
		blocks.add(c1);
		blocks.add(c2);
		blocks.add(d1);
		blocks.add(d2);
		blocks.add(e1);
		blocks.add(e2);
		blocks.add(f1);
		blocks.add(f2);
		blocks.add(g1);
		blocks.add(g2);
	}

	//重建Application Status,这是来自url的映射
	public void buildBlocks(String history){
		if(history == null || history.trim().isEmpty())return;
		clearAllBlockContents();
		String[] ssa = history.split(Sperator.PA_SPERATOR);
		if(ssa.length > 1){
			String[] ps = ssa[1].split(Sperator.P_SPERATOR);
			for(String p : ps){
				String[] pkv = p.split(Sperator.P_INNER_SPERATOR); 
				if("siteId".equals(pkv[0])){
					if(pkv[1] != null && !SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(pkv[1]))
						siteId = pkv[1];
				}
			}
		}
		String[] ss = ssa[0].split(Sperator.BLOCK_SPERATOR);
		for(int i=0;i<ss.length;i++){
			if(getBlockByTokenFragment(ss[i]) != null)
				getBlockByTokenFragment(ss[i]).setValues(ss[i].split(Sperator.BLOCK_INNER_SPERATOR));
		}
		ApplicationChangeEvent ace = new ApplicationChangeEvent();
		eventBus.fireEventFromSource(ace, this);
	}
	
	public String getLatestToken(ViewNameEnum vname){
		for(int i = tokenList.size() - 1;i>0;i--){
			if(tokenList.get(i).contains(Sperator.BLOCK_INNER_SPERATOR + vname.toString() + Sperator.BLOCK_INNER_SPERATOR)){
				return tokenList.get(i);
			}
		}
		return null;
	}
	
	
	public Vblock getBlock(ViewNameEnum vname){
		for(Vblock block : blocks){
			if(block.getViewName() == vname)return block;
		}
		return null;
	}
	
	private Vblock getBlockByTokenFragment(String tokenFragment){
		String[] ss = tokenFragment.split("/");
		if(ss.length < 1)return null;
		return getBlock(ss[0]);
	}
	
	private Vblock getBlock(String bname){
		for(Vblock block : blocks){
			if(bname.equals(block.getName())){
				return block;
			}
		}
		return null;
	}
	
	
	public void clearAllBlockContents(){
		for(Vblock vb : blocks){
			vb.clearValues();
		}
	}
	
	public String toHistoryToken(){
		String[] ss = new String[blocks.size()];
		for(int i=0;i<blocks.size();i++){
			ss[i] = blocks.get(i).toToken();
		}
		return StringUtils.join(ss, Sperator.BLOCK_SPERATOR) + Sperator.PA_SPERATOR + getPurlString();
	}
	
	private String getPurlString() {
		return "siteId" + Sperator.P_INNER_SPERATOR + siteId;
	}

	//update url but not send changeevent，fireevent mannully.
	public void addHistoryItemAndFireApplicationEvent(){
		History.newItem(fireApplicationEvent(),false);
	}
	
	public String fireApplicationEvent(){
		String currentToken = toHistoryToken();
		tokenList.add(currentToken);
		ApplicationChangeEvent ace = new ApplicationChangeEvent();
		eventBus.fireEventFromSource(ace, this);
		return currentToken;
	}
	
	public String getPreToken(){
		int i = tokenList.size();
		if(i > 0){
			tokenList.remove(i - 1);
			if(tokenList.size()>0){
				return tokenList.get(tokenList.size() - 1);
			}
		}
		return "";
	}
	
	public boolean hasHistory(){
		if(getPreToken().isEmpty()){
			return false;
		}
		return true;
	}
	
	public void back(){
		String preToken = getPreToken();
		if(!History.getToken().equals(preToken)){
			History.newItem(preToken, true);
		}
	}

	public void setSiteId(String siteId) {
		if(!SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(siteId))this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setLastMouseMoveTimeStamp(Long lastMouseMoveTimeStamp) {
		this.lastMouseMoveTimeStamp = lastMouseMoveTimeStamp;
	}

	public Long getLastMouseMoveTimeStamp() {
		return lastMouseMoveTimeStamp;
	}
}
