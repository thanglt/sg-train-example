
var com;
if(!com)com={};
if(!com.m3958)com.m3958={};
if(!com.m3958.vote)com.m3958.vote={};
if(!com.m3958.vote.resultdsp)com.m3958.vote.resultdsp={};


YUI().use('node','jsonp','stylesheet','dump','selector-css3','array-extras','querystring-stringify','tabview',"datasource-get", "datasource-jsonschema", "datatable-base",'anim', "datatable-datasource",function(Y){
	
    var getUniqueId = (function(){
    	var n = 0;
    	return function(prefix){
    		if(!prefix)prefix = "m3958";
    		return prefix + n++;
    	};
    })();
    
    
	com.m3958.vote.resultdsp.drawResult = function(cfg){
		if(!cfg || !cfg.container_dom_id){
			alert('请传入配置对象！');
			return;
		}
		if(cfg.container_dom_id.indexOf('#') == -1){
			cfg.container_dom_id = "#" + cfg.container_dom_id;
		}
		nsfunc(cfg);
	};
	
	if(typeof m3958_vote_result_cfgs == 'undefined'){
		return;
	};
	
	if(Y.Lang.isArray(m3958_vote_result_cfgs)){
		Y.each(m3958_vote_result_cfgs,function(v){
			com.m3958.vote.resultdsp.drawResult(v);
		});
	}else{
		com.m3958.vote.resultdsp.drawResult(m3958_vote_result_cfgs);
	}
	
	function nsfunc(cfg){//小环境
		var uniqueInstance = getUniqueId();
		
		var loading = false,
			currentTid = null,
			tIds = {},
			voteSuccess = false,
			tabview = null,
			container_node = Y.one(cfg.container_dom_id);
		
		container_node.setContent('<img src="/aaayjcsxdl/images/ajax-loader.gif"/>');
		com.m3958.vote.resultdsp[uniqueInstance] = {};
		
		var vsheet = Y.StyleSheet(getDefaultCss());
		var usersheet;
		try{
			if(cfg.override_css){
				usersheet = Y.StyleSheet(cfg.override_css);
			}
		}catch(e){
			;
		}
	
	
		var doVoteTabLabel = '<a href="#dovote">我要投票</a>';
		var doVoteTabContent = 
			'<div id="dovote">'+
			'</div>';
			
		var vtpl =	
			'<div class="m3958-vote-box-header clearfix">'+
				'<div class="m3958_vote_result_box">'+
					'<div class="m3958_vote_number"><span id="m3958_vote_number_id">{totalVoteNum}</span> 票</div>'+
				'</div>'+
				'<h1>{vtitle}</h1>'+
				'<p>&nbsp;</p>'+
			'</div>'+
		
			'<div id="votetabview"></div>'+
		
			'<div class="m3958-box-ctrl">'+
				'<div class="m3958_vote_result_box">'+
					'<a id="toWb" target="_self" href="http://www.m3958.com">诗篇网络提供</a>'+
				'</div>'+
				'<span class="m3958-icon-clock">状态：进行中，起止时间：{startDate} 至 {endDate}</span>'+
			'</div>';
		
	    function handleJSONP(response) {
		    // Columns must match data parameter names
		    var vdata = response.response.data;
	    	
			var startDate = vdata.startDate,endDate = vdata.endDate;
			if(!startDate){
				startDate = "";
			}else{
				startDate = startDate.replace(/T/,' ');
			}
			
			if(!endDate){
				endDate = "";
			}else{
				endDate = endDate.replace(/T/,' ');
			}
			
			vtpl = Y.Lang.sub(vtpl,{vtitle:vdata.name,totalVoteNum:vdata.childrenVoteNum,startDate:startDate,endDate:endDate});
			container_node.setContent(vtpl);
			tabview = new Y.TabView({
				srcNode : '#votetabview'
			});
			tabview.render();
			
			var tab = new Y.Tab();
			tab.set('label','<a href="#resulttable">表格</a>');
			tab.set('content','<div id="votetablec"></div>');
			
			tabview.add(tab);
			tabview.add({label:doVoteTabLabel,content:doVoteTabContent});
			tabview.selectChild(0);


		    var voteItems = vdata.children;
		    var totalnum = vdata.childrenVoteNum;
		    
		    
	    	var vpercent = function (o) {
//	    		o.column.thNode.setStyle('width','80px');
//	    		o.td.setStyle('width','80px');
	    	    var votenum  = o.record.getValue("voteNum");
	    	    return Y.Lang.sub('<div class="m3958-rate-trough"><span style="width: {percent}%;display:none;"></span></div><span>{percent}%</span>',{percent:((votenum/totalnum)*100).toFixed(2)});
	    	};
	        
		   	 // Creates a Columnset with 3 Columns. "cost" is not rendered.
			    var cols = [{key:"name",label:'选项'},{key:"percent",formatter:vpercent,label:'比例'},{key:"voteNum",label:'票数'}];
			    
		    // Creates a DataTable with 3 columns and 3 rows
		    var table = new Y.DataTable.Base({
		        columnset: cols,
		        recordset: voteItems,
		        caption:vdata.name,
		        summary:''
		    }).render(Y.one('#votetablec'));
		    
		    Y.all('.m3958-rate-trough span').each(function(anode){
		    	anode.show();
			    var cc = anode.getStyle('width');
			    anode.setStyle('width',0);
			    var anim = new Y.Anim({
			    	node:anode,
			        to: { width: cc },
			        easing: Y.Easing.backOut
			    });
			    anim.set('duration', 2);
			    anim.run();
		    });
		    
	    }
	 
        var url = '/jsonp?_modelName=com.m3958.firstgwt.model.Vote&want=jsonb&callback={callback}&id=' + cfg.vote_id;
	 
	    Y.jsonp(url, handleJSONP);
	}
	
	
	function getDefaultCss(){
		return 	'.yui3-tab {'+
			'border-top: 1px solid #ccc;'+
			'border-left: 1px solid #ccc;'+
			'border-right: 1px solid #ccc;'+
		'}'+

		'.yui3-tab-selected {'+
			'font-weight: bolder;'+
			'background: url("/aaayjcsxdl/images/vote/bg11.png") repeat-x scroll 0 0 transparent;'+
		'}'+

		'.m3958-rate-trough {'+
			'background: url("/aaayjcsxdl/images/others/netapercent-bg.png") no-repeat scroll 0 0 transparent;'+
			'float: left;'+
			'height: 13px;'+
			'margin-top: 4px;'+
			'padding: 0 3px 0 1px;'+
			'text-align: left;'+
			'width: 150px;'+
		'}'+

		'.m3958-rate-trough span {'+
			'background: url("/aaayjcsxdl/images/others/netapercent.png") repeat-x scroll 0 0 transparent;'+
			'border: 1px solid #478B38;'+
			'display: block;'+
			'height: 9px;'+
			'margin-left: -1px;'+
			'overflow: hidden;'+
		'}'+

		'.m3958-vote-box-header {'+
			'line-height: 1.75;'+
		'}'+

		'.m3958-vote-box-header h1 {'+
			'font: 20px/1.75 黑体;'+
		'}'+

		'.m3958-vote-box-header .m3958_vote_result_box {'+
			'float: right;'+
			'text-align: right;'+
		'}'+

		'.m3958_vote_number {'+
			'color: #990000;'+
		'}'+

		'.m3958_vote_number span {'+
			'font: 20px/1.75 Arial;'+
		'}'+

		'.m3958-box-ctrl {'+
			'background: url("/aaayjcsxdl/images/vote/bg12.png") repeat-x scroll 0 0 transparent;'+
			'border-bottom: 1px solid #C4D6EC;'+
			'border-left: 1px solid #C4D6EC;'+
			'border-right: 1px solid #C4D6EC;'+
			'color: #999999;'+
			'height: 23px;'+
			'line-height: 23px;'+
			'padding: 8px 13px;'+
		'}'+

		'.m3958-box-ctrl .m3958_vote_result_box {'+
			'float: right;'+
		'}'+

		'.m3958-icon-clock {'+
			'background: url("/aaayjcsxdl/images/vote/bg15.png") no-repeat scroll 0 1px transparent;'+
			'padding-left: 15px;'+
		'}'+
		
		'#votetablec table {'+
			'width:100%;'+
			'border-top:1px solid #e5eff8;'+
			'border-right:1px solid #e5eff8;'+
			'margin:0 auto;'+
			'border-collapse:collapse;'+
			'}'+
		'#votetablec caption {'+
			'color: #9ba9b4;'+
			'font-size:.94em;'+
				'letter-spacing:.1em;'+
				'margin:1em 0 0 0;'+
				'padding:0;'+
				'caption-side:top;'+
				'text-align:left;'+
			'}'+
				
		'#votetablec tr.yui3-datatable-odd td	{'+
			'background:#f7fbff'+
			'}'+

		'#votetablec td {'+
			'color:#678197;'+
			'border-bottom:1px solid #e5eff8;'+
			'border-left:1px solid #e5eff8;'+
			'padding:.3em 1em;'+
			'text-align:left;'+
			'}'+
		'#votetablec th {'+
			'font-weight:normal;'+
			'color: #678197;'+
			'text-align:left;'+
			'border-bottom: 1px solid #e5eff8;'+
			'border-left:1px solid #e5eff8;'+
			'padding:.3em 1em;'+
			'}'+	
		'#votetablec thead th {'+
			'background:#f4f9fe;'+
			'text-align:left;'+
			'font:bold 1.2em/2em "Century Gothic","Trebuchet MS",Arial,Helvetica,sans-serif;'+
			'color:#66a3d3'+
		'}'+

		'#votetablec{'+
			'border-top: 1px solid #CCCCCC;'+
			'border-bottom: 1px solid #CCCCCC;'+
		'}';
			
	}

});
