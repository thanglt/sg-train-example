package com.m3958.firstgwt.client.layout;

import java.util.ArrayList;
import java.util.List;

import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.StripEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AppToolStrip extends HLayout implements Iview{
	
	
	public static enum Btname{
		ADD,REMOVE,EDIT,BACK,LEAVE_FOLDER,ROLE,PERMISSION,SHARE_TO_USER,SHARE_TO_GROUP,
		MINE,OTHERS,GROUPS,SHOW_ALL,HOSTNAME,ARTICLE,STATIC_FILE,REFRESH,CONFIRM,MEDIA,LINK,XINJIAN,VOTE,VOTE_HIT,
		COMMENT,PAGE_MISTAKE
	}
	
	public class BtClickHandler implements ClickHandler{
		
		private Btname lbtName; 
		
		public BtClickHandler(Btname btName){
			this.lbtName = btName;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			StripEvent tse = new StripEvent(vname,lbtName);//G2 slot对此感兴趣。
			eventBus.fireEvent(tse);
		}
		
	}
	
	private final ToolStripButton mineBt = new ToolStripButton("我的");
	private final  ToolStripButton othersBt = new ToolStripButton("别人的");
	private final ToolStripButton groupsBt = new ToolStripButton("组的");
	
	private final ToolStripButton addbt = new ToolStripButton("添加");
	private final ToolStripButton editbt = new ToolStripButton("编辑");
	private final ToolStripButton removebt = new ToolStripButton("删除");
	private final ToolStripButton leaveFolderBt = new ToolStripButton("脱离分组");
	
	private final ToolStripButton objectRoleBt = new ToolStripButton("角色");
	private final ToolStripButton permissionBt = new ToolStripButton("权限");
	
	private final ToolStripButton share2userBt = new ToolStripButton("共享给用户");
	
	private final ToolStripButton share2groupBt = new ToolStripButton("共享给组");
	
	private final ToolStripButton hostnameBt = new ToolStripButton("主机名");
	private final ToolStripButton articleBt = new ToolStripButton("站点文章");
	private final ToolStripButton  staticfileBt = new ToolStripButton("静态文件");
	
	private final ToolStripButton showAllBt = new ToolStripButton("全部");
	
	private final ToolStripButton refreshBt = new ToolStripButton("刷新");
	
	private final ToolStripButton confirmBt = new ToolStripButton("确定选择");
	
	private final ToolStripButton mediaBt = new ToolStripButton("媒体");
	
	private final ToolStripButton xinJianBt = new ToolStripButton("信箱");
	
	private final ToolStripButton linkBt = new ToolStripButton("链接");
	
	private final ToolStripButton voteBt = new ToolStripButton("投票");
	
	private final ToolStripButton commentBt = new ToolStripButton("评论");
	
	private final ToolStripButton pageMistakeBt = new ToolStripButton("纠错");
	
	private final ToolStripButton voteHitBt = new ToolStripButton("投票详细");
	
	
	private ToolStrip toolStrip;
	
	private MyEventBus eventBus;
	
	private ViewNameEnum vname;
	
	private List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();
	
	public AppToolStrip(MyEventBus eventBus,ViewNameEnum vname){
		this.eventBus = eventBus;
		this.vname = vname;
		setWidth100();
		setHeight100();
		addToButtons();
		addMember(initStrip());
	}

	private Canvas initStrip() {
		toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		for(ToolStripButton tsb : buttons){
			toolStrip.addButton(tsb);
		}
		
		toolStrip.addFill();
		
		removebt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("确认删除选定条目？", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value){
							StripEvent sie = new StripEvent(vname,Btname.REMOVE);
							eventBus.fireEvent(sie);
							removebt.hide();
						}
					}
				});
				
			}
		});
		
		confirmBt.addClickHandler(new BtClickHandler(Btname.CONFIRM));
		leaveFolderBt.addClickHandler(new BtClickHandler(Btname.LEAVE_FOLDER));
		editbt.addClickHandler(new BtClickHandler(Btname.EDIT));
		addbt.addClickHandler(new BtClickHandler(Btname.ADD));
		objectRoleBt.addClickHandler(new BtClickHandler(Btname.ROLE));
		showAllBt.addClickHandler(new BtClickHandler(Btname.SHOW_ALL));
		refreshBt.addClickHandler(new BtClickHandler(Btname.REFRESH));
		permissionBt.addClickHandler(new BtClickHandler(Btname.PERMISSION));
		share2userBt.addClickHandler(new BtClickHandler(Btname.SHARE_TO_USER));
		share2groupBt.addClickHandler(new BtClickHandler(Btname.SHARE_TO_GROUP));
		mineBt.addClickHandler(new BtClickHandler(Btname.MINE));
		othersBt.addClickHandler(new BtClickHandler(Btname.OTHERS));
		groupsBt.addClickHandler(new BtClickHandler(Btname.GROUPS));
		hostnameBt.addClickHandler(new BtClickHandler(Btname.HOSTNAME));
		articleBt.addClickHandler(new BtClickHandler(Btname.ARTICLE));
		staticfileBt.addClickHandler(new BtClickHandler(Btname.STATIC_FILE));
		mediaBt.addClickHandler(new BtClickHandler(Btname.MEDIA));
		linkBt.addClickHandler(new BtClickHandler(Btname.LINK));
		voteBt.addClickHandler(new BtClickHandler(Btname.VOTE));
		commentBt.addClickHandler(new BtClickHandler(Btname.COMMENT));
		pageMistakeBt.addClickHandler(new BtClickHandler(Btname.PAGE_MISTAKE));
		voteHitBt.addClickHandler(new BtClickHandler(Btname.VOTE_HIT));
		xinJianBt.addClickHandler(new BtClickHandler(Btname.XINJIAN));
		return toolStrip;
		
	}
	
		@Override
		public void changeDisplay(ViewAction va,String...paras) {}
		
		private void hideAllBt(){
			for(ToolStripButton tsb : buttons){
				toolStrip.hideMember(tsb);
			}
		}
		
		public void changeStatus(Btname...bts){
			hideAllBt();
			if(bts == null)return;
			for(Btname bn : bts){
				switch (bn) {
				case ADD:
					toolStrip.showMember(addbt);
					break;
				case REMOVE:
					toolStrip.showMember(removebt);
					break;
				case EDIT:
					toolStrip.showMember(editbt);
					break;
				case LEAVE_FOLDER:
					toolStrip.showMember(leaveFolderBt);
					break;
				case ROLE:
					toolStrip.showMember(objectRoleBt);
					break;
				case PERMISSION:
					toolStrip.showMember(permissionBt);
					break;
				case SHARE_TO_GROUP:
					toolStrip.showMember(share2groupBt);
					break;
				case SHARE_TO_USER:
					toolStrip.showMember(share2userBt);
					break;
				case MINE:
					toolStrip.showMember(mineBt);
					break;
				case OTHERS:
					toolStrip.showMember(othersBt);
					break;
				case GROUPS:
					toolStrip.showMember(groupsBt);
					break;
				case HOSTNAME:
					toolStrip.showMember(hostnameBt);
					break;
				case ARTICLE:
					toolStrip.showMember(articleBt);
					break;
				case STATIC_FILE:
					toolStrip.showMember(staticfileBt);
					break;
				case SHOW_ALL:
					toolStrip.showMember(showAllBt);
					break;
				case REFRESH:
					toolStrip.showMember(refreshBt);
					break;
				case CONFIRM:
					toolStrip.showMember(confirmBt);
					break;
				case MEDIA:
					toolStrip.showMember(mediaBt);
					break;
				case LINK:
					toolStrip.showMember(linkBt);
					break;
				case VOTE:
					toolStrip.showMember(voteBt);
					break;
				case COMMENT:
					toolStrip.showMember(commentBt);
					break;
				case PAGE_MISTAKE:
					toolStrip.showMember(pageMistakeBt);
					break;
				case VOTE_HIT:
					toolStrip.showMember(voteHitBt);
					break;
				case XINJIAN:
					toolStrip.showMember(xinJianBt);
					break;
				default:
					break;
				}
			}
		}

		@Override
		public ViewNameEnum getViewName() {
			return vname;
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
			hideAllBt();
		}
		
		private void addToButtons() {
			buttons.add(addbt);
			buttons.add(editbt);
			buttons.add(removebt);
			buttons.add(leaveFolderBt);
			buttons.add(objectRoleBt);
			buttons.add(permissionBt);
			buttons.add(share2userBt);
			buttons.add(share2groupBt);

			
			buttons.add(hostnameBt);
			buttons.add(articleBt);
			buttons.add(staticfileBt);
			
			buttons.add(mediaBt);
			buttons.add(xinJianBt);
			buttons.add(linkBt);
			buttons.add(voteBt);
			buttons.add(commentBt);
			buttons.add(pageMistakeBt);
			buttons.add(voteHitBt);
			buttons.add(confirmBt);
			
			buttons.add(refreshBt);
			buttons.add(mineBt);
			buttons.add(othersBt);
			buttons.add(groupsBt);
			buttons.add(showAllBt);
		}

}
