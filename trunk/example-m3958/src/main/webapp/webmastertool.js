/**
 * 
 */

//var m3958_webmastertools_module_path = 'http://127.0.0.1:8888/aaayjcsxdl/js/webmastertools/votetab.js';
//if()

YUI({combine:false,charset:'utf-8',
    modules:  {
        'unique-dom-id-module':{
        	fullpath: '/aaayjcsxdl/js/commons/uniquedomid.js'
        },
    	'votetab-module':{
            fullpath: '/aaayjcsxdl/js/webmastertools/votetab.js',
            requires: ['node','jsonp','align-plugin','event-delegate','selector-css3','unique-dom-id-module','io-form']
        },
        'message-box-module':{
            fullpath: '/aaayjcsxdl/js/webmastertools/messagediv.js',
            requires: ['node','stylesheet']
        }
    }
	}).use('node','stylesheet','dump','io-base','json','event', 'yui2-treeview','history','votetab-module','unique-dom-id-module','message-box-module',function(Y){
	
    var getUniqueId = (function(){
    	var n = 0;
    	return function(prefix){
    		if(!prefix)prefix = "m3958";
    		return prefix + n++;
    	};
    })();
    
    var ns = Y.namespace('m3958.webmastertools');
    ns.logined = false;
    ns.user = null;
    
//    var votetab = new Y.Votetab();
//    votetab.init();

	var history = new Y.HistoryHash(),
		loginBar = Y.one('#loginbar'),
		tabs,
		mmenuBar = Y.one('#mmenubar');
//		,
//		voteTree,
//		voteForm,
//		voteTreeToolbar;
	
//	Y.on('domready',function(e){
//		alert(Y.dump(voteForm));
//	});
	
	
	/* -- global func start --*/
	(function(){
//		Y.one(document).on('click',function(e){
//			if(!voteTreeNode.contains(e.target) && !voteTreeToolbar.node.contains(e.target)){
//				voteTreeToolbar.disable();
//			}
//		});
	})();
	
	/* -- global func end --*/
	
	
	/* -- mainmenu start --*/
	(function(){
		mmenuBar.delegate('click',function(e){
			e.preventDefault();
			var a = e.currentTarget;
			var id = a.get("id");
			tabs.showtab(id.split('-')[1]);
		},"a");
		
		tabs = {
					votetab:new Y.Votetab(),
					commenttab:Y.one('#commenttab'),
					currentTab:null,
					showtab:function(tabid){
						Y.log(ns);
						if(!ns.logined)return;
						if(this.currentTab){
							var ma = Y.one('#a-' + this.currentTab.get('id')); 
							if(ma)ma.removeClass('current-menu');
							if(this.currentTab.addClass){
								this.currentTab.addClass('inithiden');
							}else{
								this.currentTab.hide();
							}
							
						}
						if(this[tabid].removeClass){
							this[tabid].removeClass('inithiden');
						}else{
							this[tabid].show();
						}
						this.currentTab = this[tabid];
						var ma1 = Y.one('#a-' + tabid);
						if(ma1)ma1.addClass('current-menu');
						history.addValue('tab', tabid);
					}
				};
		
		Y.on('history:change', function (e) {
			if (e.src === Y.HistoryHash.SRC_HASH) {
				if (e.changed.tab) {
					alert('changed');
				} else if (e.removed.tab) {
					alert('removed');
				}
			}
		});
	})();
	/* -- mainmenu end --*/
	
	(function(){
		function changeLoginStatus(){
			if(ns.logined){
				loginBar.setContent('<span>' + ns.user.nickname +  '</span> <a href="/openid.html#tab=3">修改</a> | <a href="/notrpclogin?logout=true">登出</a>');
			}
		}
		var tH = {
				success: function(id, o, args) {
						   var res = Y.JSON.parse(o.responseText);
						   Y.log(res);
						   if(res && res.response && res.response.data && res.response.data.length == 1){
							   ns.user = res.response.data[0];
							   ns.logined = true;
							   changeLoginStatus();
								if(history.get('tab')){
									tabs.showtab(history.get('tab'));
								}else{
									tabs.showtab('votetab');
								}
						   }else{
							   Y.messageBox.warn('您尚未登录，从右上角的登录开始--------------------------------->',3000);
						   }
						 },
				failure: function(id, o, args) {
							alert("发生未知故障！");
						 }

			};
		
		var cfg = {
				on: {
					success: tH.success,
					failure: tH.failure
				},
				context: tH
			};
		
		Y.io('/openid?loginstatus=true', cfg);		
	})();
});


//function loadNodeData(node,fnLoadComplete){
//var tH = {
//		success: function(data, args) {
//		    		var vdata = response.response.data;
//		    		alert(Y.dump(vdata));
//		    		args.fnLoadComplete();
//				 },
//		failure: function(data, args) {
//					alert("获取数据失败！");
//					args.fnLoadComplete();
//				 }
//
//	};
//var cfg = {
//		on: {
//			success: tH.success,
//			failure: tH.failure
//		},
//		context: tH,
//		args:[{"node": node,"fnLoadComplete": fnLoadComplete}]
//	};
//
//var url = '/jsonp?_modelName=com.m3958.firstgwt.model.Vote&want=jsonb&callback={callback}&id=15201';
//Y.jsonp(url, cfg);
//}