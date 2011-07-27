/**
 * 
 */
	
YUI({combine:false,charset:'utf-8',
    modules:  {
        'center-message-box-module':{
            fullpath: '/aaayjcsxdl/js/commons/centermessagediv.js',
            requires: ['node','stylesheet']
        }
    }
	}).use('node','stylesheet','dump','selector-css3','array-extras','querystring-stringify','jsonp','overlay','transition','align-plugin','center-message-box-module',function(Y){
    var getUniqueId = (function(){
    	var n = 0;
    	return function(prefix){
    		if(!prefix)prefix = "m3958";
    		return prefix + n++;
    	};
    })();
    
    var ajax_loading_img = Y.Node.create('<img src="/aaayjcsxdl/images/ajax-loader.gif" style="display:none"/>');
    
	var drawVote = function(voteCfg){
		if(voteCfg.vote_type == 'html'){
			processHtmlVote(voteCfg);
		}else{
			if(!voteCfg || !voteCfg.container_dom_id){
				alert('请传入配置对象！');
				return;
			}
			if(!voteCfg.clock_html){
				voteCfg.clock_html = "";
			}
				
			if(!voteCfg.result_page){
				voteCfg.result_page = '/voteresult.html';
			}
				
			if(voteCfg.container_dom_id.indexOf('#') == -1){
				voteCfg.container_dom_id = "#" + voteCfg.container_dom_id;
			}
			processJavascriptVote(voteCfg);
		}
	};
	
	if(typeof com_m3958_vote_cfgs == 'undefined'){
		return;
	}
	
	//程序执行入口
	if(Y.Lang.isArray(com_m3958_vote_cfgs)){
		Y.each(com_m3958_vote_cfgs,function(v){
			drawVote(v);
		});
	}else{
		drawVote(com_m3958_vote_cfgs);
	}
	
	function processHtmlVote(voteCfg){//因为新建了全局函数，所以环境自然被保存，无需担心环境丢失
		var uniqueInstance = getUniqueId();
		var loading = false,
			currentTid = null,
			tIds={},
			voteSuccess = false;

		var form_node = Y.one('#' + voteCfg.vote_form_id);
		form_node.insertBefore(ajax_loading_img,form_node);
		ajax_loading_img.show();
		form_node.hide();
		if(!form_node.get('action') || form_node.get('action').length < 3){
			form_node.set('action','/voteresult.html?vote_id=' + voteCfg.vote_id);
		}
	
		var hiddens = '<input type="hidden" value="com.m3958.firstgwt.model.VoteHit" name="_modelName"/>'+ 
						'<input type="hidden" value="com.m3958.firstgwt.model.Vote" name="_relationModelName"/>'+ 
						'<input type="hidden" name = "obname" value = "vote"/> '+
						'<input type="hidden" name = "obid" value = "' + voteCfg.vote_id + '"/> '+
						'<input type="hidden" name="_operationType" value="add"/>';
		form_node.append(hiddens);
			
		
		var doVote = function(qs){		
			var tH = {
					success: function(response, args) {
				    		var vdata = response.response.data;
				    			form_node.all('input').set("disabled",true);
								voteSuccess = true;
								Y.centerMessageBox.prompt("投票成功！",Y.one(voteCfg.vote_form_id));
							 },
					failure: function(response, args) {
								alert("获取数据失败！");
							 }
	
				};
			var cfg = {
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
		    var url = "/jqfprocessor?" + qs + "&callback={callback}";
		    Y.log(url);
		    Y.jsonp(url, cfg);
		};
		
		
		var getVoteInfo = function() {
			var tH = {
					success: function(response, args) {
						var vdata = response.response.data;
						var subobj = {minSelect:vdata.minSelect,maxSelect:vdata.maxSelect,
										startDate:vdata.startDate,endDate:vdata.endDate,voteNum:vdata.voteNum,vname:vdata.name};
						var mins = vdata.minSelect,maxs=vdata.maxSelect;
						if(mins < 1)mins = 1;
						if(maxs < 1)maxs = 1;
						var inputsub;
						if(mins < 2 && maxs < 2){
							inputsub = {inputType:'radio'};
						}else{
							inputsub = {inputType:'checkbox'};
						}
						
						//替换form包含的html片段中的变量。一次性。
						var form_html = form_node.getContent();
						form_html = Y.Lang.sub(form_html,subobj);
						form_node.setContent(form_html);
						
						var loopblock = form_node.one('#' + voteCfg.vote_loop_id);
						loopblock.removeAttribute('id');
						var loopblockClone = loopblock.cloneNode(true);
						var loopblockHtml = Y.Node.create("<div></div>").append(loopblockClone).getContent();

						
						//准备一个loop条目
						var inputstr = '<input type="{inputType}" name="_relationModelId" id="{uniqueId}" value="{vitemid}" />';
						inputstr = Y.Lang.sub(inputstr,inputsub);
						loopblockHtml = Y.Lang.sub(loopblockHtml,{radio_or_checkbox:inputstr});
						if(loopblockHtml.indexOf('<label>') == -1 && loopblockHtml.indexOf('<LABEL>') == -1){
							loopblockHtml = loopblockHtml.replace('{label}','<label>{label}</label>');
						}
						
						for(var i=0;i<vdata.children.length;i++){
							var uniq = getUniqueId();
							var finalLoopblockHtml = Y.Lang.sub(loopblockHtml,{
								vitemid:vdata.children[i].id,
								label:vdata.children[i].name,
								uniqueId:uniq
							});
							var n123 = Y.Node.create(finalLoopblockHtml);
							if(n123.one('label')){
								n123.one('label').setAttribute('for',uniq);
							}
							loopblock.insert(n123,'before');
						}
						
						loopblock.remove(true);
						form_node.show();
						ajax_loading_img.hide();
						//from here all vote element are present.
						form_node.on('submit',function(e){
							if(voteSuccess){
								;
							}else{
								e.preventDefault();
								var fv = serializeForm(form_node,true);
								var selected = fv["_relationModelId"]; 
								if(!selected){
									Y.centerMessageBox.prompt("请至少选择一项",voteCfg.vote_form_id);
									return;
								}
								
								var selectNum = 1;
								if(Y.Lang.isArray(selected)){
									selectNum = selected.length;
								}
								
								if(selectNum < mins || selectNum > maxs){
									Y.centerMessageBox.prompt("必须选择" + mins + '到' + maxs + '个',voteCfg.vote_form_id);
									return;
								}
								
								var  query = Y.QueryString.stringify(fv);
								doVote(query);
							}
						});
					},
					failure: function(response, args) {
								alert("获取数据失败！");
							 }
	
				};
			var cfg = {
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
		    var url = "/jsonp?_modelName=com.m3958.firstgwt.model.Vote&id=" + voteCfg.vote_id + "&callback={callback}";
		    Y.log(url);
		    Y.jsonp(url, cfg);
		};
		
		getVoteInfo();
	}
	
	function processJavascriptVote(voteCfg){//因为新建了全局函数，所以环境自然被保存，无需担心环境丢失！
		var uniqueInstance = getUniqueId();
		var loading = false,
			currentTid = null,
			tIds={},
			voteSuccess = false,
			b0_html,b1_html,b2_html,b3_html,form_html,voteNode,form_node;
		
	
		var vcss = Y.Array.map(getDefaultCss(),function(item){
			return voteCfg.container_dom_id + ' ' + item;
		});

		var vsheet = Y.StyleSheet(vcss.join(' '));
		var usersheet;
		if(voteCfg.override_css){
			usersheet = Y.StyleSheet(voteCfg.override_css);
		}
		
		var vitem_tpl = '<dd>'+
							'<label>'+
							'<input type="{vtype}" name="_relationModelId" value="{vitemid}">{vitemname}</label>'+
						'</dd>';
		if(!voteCfg.b1){
			b0_html = '<div class="m3958-vote-display-b0"></div>';
		}else{
			b0_html = voteCfg.b0;
		}
		if(!voteCfg.b1){
			b1_html = '<div class="m3958-vote-display-b1">{vbtitle}</div>';
		}else{
			b1_html = voteCfg.b1;
		}
		
		b2_html = '<div class="m3958-vote-display-b2">'+
								'<dl class="m3958_vote_list">'+
								'<dt>'+
								'<span><b>{vtitle}</b></span>'+
								'</dt>'+
								
								'</dl>'+
							'</div>';
		b3_html =	'<div class="m3958-vote-display-b3">'+
								'<div class="m3958_vote_bt_prompt">'+
								'<input type="submit" class="m3958_vote_submit" value="投票">'+
								'<a target="_blank" class="m3958_vote_view" href="' + voteCfg.result_page + '">查看结果</a>'+
								'</div>'+
								voteCfg.clock_html + 
							'</div>';
		
		b4_html =	'<div class="m3958-vote-display-b4"></div>';
		
		form_html ='<form target="_blank" method="get" action="' + voteCfg.result_page + '" id="m3958_vote_form">'+
								'<input type="hidden" value="com.m3958.firstgwt.model.VoteHit" name="_modelName"/>'+ 
								'<input type="hidden" value="com.m3958.firstgwt.model.Vote" name="_relationModelName"/>'+ 
								'<input type="hidden" name = "obname" value = "vote"/> '+
								'<input type="hidden" name = "obid" value = "' + voteCfg.vote_id + '"/> '+
								'<input type="hidden" name="_operationType" value="add"/>'+
							'</form>';
		

		
		
		var createVoteDom = function(vdata){
			var c = Y.Node.create('<div></div>');
			var form = Y.Node.create(form_html);
			c.append(form);
			var b0 = Y.Node.create(b0_html);
			form.append(b0);
			var b1 = Y.Node.create(b1_html);
			var b2 = Y.Node.create(b2_html);
			var b3 = Y.Node.create(b3_html);
			var b4 = Y.Node.create(b4_html);
			
			b0.append(b1);
			b0.append(b2);
			b0.append(b3);
			b0.append(b4);
			
			var vitemc_dom = b2.one('.m3958_vote_list'); 
			Y.each(vdata.children,function(value){
				vitemc_dom.append(Y.Lang.sub(vitem_tpl,{vitemid:value.id,vitemname:value.name}));
			});
			return c;
		};

		
		var doVote = function(qs){		
			var tH = {
					success: function(response, args) {
				    		var vdata = response.response.data;
								voteNode.all('input[name=_relationModelId]').set("disabled",true);
								voteNode.one('.m3958_vote_submit').set('disabled',true);
								voteSuccess = true;
							 },
					failure: function(response, args) {
								alert("获取数据失败！");
							 }
	
				};
			var cfg = {
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
		    var url = "/jqfprocessor?" + qs + "&callback={callback}";
		    Y.log(url);
		    Y.jsonp(url, cfg);
		};
	    

		
		var getVoteInfo = function() {
			var tH = {
					success: function(response, args) {
						var res = response.response;
						var vdata = res.data;
						if(res.status != 0){
							Y.centerMessageBox.warn(vdata[0].message);
							return;
						}
						
						var mins = vdata.minSelect,maxs=vdata.maxSelect;
						if(mins < 1)mins = 1;
						if(maxs < 1)maxs = 1;
						if(mins < 2 && maxs < 2){
							vitem_tpl = Y.Lang.sub(vitem_tpl,{vtype:'radio'});
						}else{
							vitem_tpl = Y.Lang.sub(vitem_tpl,{vtype:'checkbox'});
						}
						
						if(!voteCfg.vtitle){
							voteCfg.vtitle = vdata.name;
						}
						voteCfg.vote_start_date = vdata.startDate;
						voteCfg.vote_end_date = vdata.endDate;
						
						var voteFormHtml = createVoteDom(vdata).getContent();
						
						voteFormHtml = Y.Lang.sub(voteFormHtml,voteCfg);
						
						var voteFormNode = Y.Node.create(voteFormHtml);
						voteNode = Y.Node.create('<div></div>');
						voteNode.setStyles({width:voteCfg.width,height:voteCfg.height});
						voteNode.append(voteFormNode);
						form_node = voteFormNode;
						Y.one(voteCfg.container_dom_id).append(voteNode);
						
						//from here all vote element are present.
						form_node.on('submit',function(e){
							if(voteSuccess){
								;
							}else{
								e.preventDefault();
								var fv = serializeForm(form_node,true);
								var selected = fv["_relationModelId"]; 
								if(!selected){
									Y.centerMessageBox.warn("请至少选择一项",Y.one(voteCfg.container_dom_id));
									return;
								}
								
								var selectNum = 1;
								if(Y.Lang.isArray(selected)){
									selectNum = selected.length;
								}
								
								if(selectNum < mins || selectNum > maxs){
									Y.centerMessageBox.warn("必须选择" + mins + '到' + maxs + '个',Y.one(voteCfg.container_dom_id));
									return;
								}
								
								var  query = Y.QueryString.stringify(fv);
								doVote(query);
							}
						});
					},
					failure: function(response, args) {
								alert("获取数据失败！");
							 }
	
				};
			var cfg = {
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
		    var url = '/jsonp?_modelName=com.m3958.firstgwt.model.Vote&want=jsonb&callback={callback}&id=' + voteCfg.vote_id;
		    Y.log(url);
		    Y.jsonp(url, cfg);
		};
		
		getVoteInfo();
	}
	
	
	var serializeForm = (function(){
		var namevalues = {};
		function add(name,value){
			if(namevalues[name]){
				 if(Y.Lang.isArray(namevalues[name])){
					 namevalues[name].push(value);
				 }else{
					 namevalues[name] = [namevalues[name]];
					 namevalues[name].push(value);
				 }
			}else{
				namevalues[name] = value;
			}
		}
		return function(form,obj){
			namevalues = {};
			var formNode;
			if(form){
				if(Y.Lang.isString(form)){
					formNode = Y.one(form);
				}else{
					formNode = form;
				}
			}
			var items = formNode.all('input[name][type=text],input[name][type=hidden],input[name][type=radio]:checked,input[name][type=checkbox]:checked,select[name],textarea[name]');
			
			items.each(function(nel){
				if(nel.get('tagName').toUpperCase() == "SELECT"){
					nel.get('options').each(function(op){
						if(op.get('selected')){
							add(nel.get('name'),op.get('value'));
						}
					});
				}else{
					add(nel.get('name'),nel.get('value'));
				}
			});
			if(obj){
				return namevalues;
			}else{
				Y.QueryString.stringify(namevalues);
			}
		};
	})();
	
	function getDefaultCss(){
		return [			'.m3958-vote-display-b0 {'+
		       					'border: 1px solid #C4D6EC;'+
		       			    	'clear: both;'+
		       			    	'text-align: left;'+
		       				'}',
		       			
		       				'.m3958-vote-display-b0 .m3958-vote-display-b2{'+
		       			    	'margin-left: 30px;'+
		       			    	'margin-right: 30px;'+
		       				    'clear: both;'+
		       				    'font-size: 12px;'+
		       				    'line-height: 21px;'+
		       				    'overflow: hidden;'+
		       				    'text-align: left;'+
		       				    'margin-bottom: 10px;'+
		       			    '}',
		       			    
		       				'.m3958-vote-display-b0 .m3958-vote-display-b3{'+
		       			    	'margin-left: 30px;'+
		       			    	'margin-right: 30px;'+
		       				    'clear: both;'+
		       				    'font-size: 12px;'+
		       				    'line-height: 21px;'+
		       				    'overflow: hidden;'+
		       				    'text-align: left;'+
		       				    'margin-bottom: 10px;'+
		       			    '}',
		       				
		       				'.m3958_vote_list dt {'+
		       				    'line-height: 25px;'+
		       				'}',
		       				
		       				'.m3958_vote_list dd {'+
		       				    'margin: 0;'+
		       				'}',
		       				
		       				'.m3958_vote_list input {'+
		       				    'height: 19px;'+
		       				    'margin: 0 1px;'+
		       				    'vertical-align: top;'+
		       				    'width: 19px;'+
		       				'}',
		       				
		       				'.m3958_vote_submit {'+
		       				    'height: 23px;'+
		       				    'width: 60px;'+
		       				'}',
		       				
		       				'.m3958_vote_clock {'+
		       				    'background: url("/aaayjcsxdl/images/vote/vote24.gif") no-repeat scroll left center transparent;'+
		       				    'padding-left: 16px;'+
		       				'}',
		       				
		       				'.m3958-vote-display-b0 .m3958-vote-display-b1 {'+
		       				    'background: url("/aaayjcsxdl/images/vote/voteicon.gif") no-repeat scroll 9px center #E5EEF8;'+
		       				    'font-size: 14px;'+
		       				    'font-weight: bold;'+
		       				    'height: 34px;'+
		       				    'line-height: 34px;'+
		       				    'margin-bottom: 10px;'+
		       				    'padding-left: 30px;'+
		       				'}',
		       				'.m3958_vote_clock_container{' +
		       					'float:right;' +
		       				'}',
		       				
		       				'.m3958_vote_bt_prompt{' +
		       					'float:left;' +
		       				'}'];
	}
	
//	function showInfow(content,alignto){
//		var iw = new Infow(content,alignto);
//		iw.trans();
//	}
//	
//    function Infow(content,alignto,innernode){
//    	var removex = '<a href="#close" class="infow-remove" style="width: 20px;height: 20px;position: absolute;top:1px;right: 1px;text-decoration: none;color: black;border: 1px solid white;text-align: center;">x</a>';
//    	this.c = content;
//    	var al = alignto,n=innernode;
//    	var tthis = this;
//    	if(!n){
//    		var innerstr = '<div style="position: relative;border: 1px solid #634819;background: none repeat scroll 0 0 #D9C5AB;color: #634819;padding: 8px;overflow: hidden;">{content}</div>';
//    		var s = Y.Lang.sub(innerstr,{content:this.c});
//    		this.inner = Y.Node.create(s); 
//    	}else{
//    		if(Y.Lang.isString(n)){
//	    		if(n.indexOf('<') == -1){
//					this.inner = Y.one(n);		    			
//	    		}else{
//	    			var innerstr = Y.Lang.sub(n,{content:this.c});
//	    			this.inner = Y.Node.create(innerstr);
//	    		}
//    		}else{
//    			var t = Y.Node.create('<div></div>');
//    			t.append(n);
//    			var s = Y.Lang.sub(t.getContent(),{content:this.c});
//    			this.inner = Y.Node.create(s);
//    		}
//    	}
//    	
//			    	
//    	this.outer = Y.Node.create('<div style="position: absolute;top: 300px;z-index: 9999;top:-1000px;left:-1000px;"></div>');
//    	this.outer.append(this.inner);
//    	this.inner.show();
//    	
//    	var removea = this.outer.one('.infow-remove');
//    	if(removea){
//	    	removea.on('click',function(){
//		    	this.hide();
//		    	tthis.trans();
//		    });
//    	}
//    	
//    	Y.one('body').append(this.outer);
//    	
//	    this.outer.plug(Y.Plugin.Align);
//	    if(al){
//    		if(Y.Lang.isString(al)){
//	    		if(al.indexOf('#') == -1){
//					al = Y.one('#' + al);		    			
//	    		}else{
//	    			al = Y.one(al);
//	    		}
//    		}
//	    	this.outer.align.center(al.get('region'));
//	    }else{
//	    	this.outer.align.center(this.outer.get('viewportRegion'));
//	    }
//	    
//	    
//	    var rg = this.inner.get('region');
//	    
//	    this.trans = function(){
//		    this.inner.transition({
//		        duration: 1, // seconds
//		        easing: 'ease-out', // CSS syntax
//		        height: 0,
//		        top:rg.height/2,
//		        width: {
//		            delay: 1,
//		            duration: 0.5,
//		            easing: 'ease-in',
//		            value: 0
//		        },
//		     
//		        left: {
//		            delay: 1,
//		            duration: 0.5,
//		            easing: 'ease-in',
//		            value: rg.width/2
//		        },
//		     
//		        opacity: {
//		            delay: 1.5,
//		            duration: 0.25,
//		            value: 0
//		        }
//		    }, function() {
//		        tthis.outer.remove();
//		    });
//	    }
//    }
    

});





//if(typeof com == 'undefined')com={};
//if(!com.m3958)com.m3958={};
//if(!com.m3958.vote)com.m3958.vote={};
//if(!com.m3958.vote.display)com.m3958.vote.display={};


//com.m3958.vote.display[uniqueInstance] = {};
//minSelect,maxSelect,startDate,endDate,voteNum,name
//com.m3958.vote.display[uniqueInstance].fetchVoteInfo = function(results){//global function,but can remember environment;
//	tIds[currentTid] = true;
//	
//	var vdata = results.response.data;
//	var subobj = {minSelect:vdata.minSelect,maxSelect:vdata.maxSelect,
//					startDate:vdata.startDate,endDate:vdata.endDate,voteNum:vdata.voteNum,name:vdata.name};
//	var mins = vdata.minSelect,maxs=vdata.maxSelect;
//	if(mins < 1)mins = 1;
//	if(maxs < 1)maxs = 1;
//	var inputsub;
//	if(mins < 2 && maxs < 2){
//		inputsub = {inputType:'radio'};
//	}else{
//		inputsub = {inputType:'checkbox'};
//	}
//	
//	//替换form包含的html片段中的变量。一次性。
//	var form_html = form_node.getContent();
//	form_html = Y.Lang.sub(form_html,subobj);
//	form_node.setContent(form_html);
//	
//	var loopblock = form_node.one('#' + voteCfg.vote_loop_id);
//	loopblock.removeAttribute('id');
//	var loopblockClone = loopblock.cloneNode(true);
//	var loopblockHtml = Y.Node.create("<div></div>").append(loopblockClone).getContent();
//
//	
//	//准备一个loop条目
//	var inputstr = '<input type="{inputType}" name="_relationModelId" id="{uniqueId}" value="{vitemid}" />';
//	inputstr = Y.Lang.sub(inputstr,inputsub);
//	loopblockHtml = Y.Lang.sub(loopblockHtml,{radio_or_checkbox:inputstr});
//	if(loopblockHtml.indexOf('<label>') == -1 && loopblockHtml.indexOf('<LABEL>') == -1){
//		loopblockHtml = loopblockHtml.replace('{label}','<label>{label}</label>');
//	}
//	
//	for(var i=0;i<vdata.children.length;i++){
//		var uniq = getUniqueId();
//		var finalLoopblockHtml = Y.Lang.sub(loopblockHtml,{
//			vitemid:vdata.children[i].id,
//			label:vdata.children[i].name,
//			uniqueId:uniq
//		});
//		var n123 = Y.Node.create(finalLoopblockHtml);
//		if(n123.one('label')){
//			n123.one('label').setAttribute('for',uniq);
//		}
//		loopblock.insert(n123,'before');
//	}
//	
//	loopblock.remove(true);
//	form_node.show();
//	//from here all vote element are present.
//	form_node.on('submit',function(e){
//		if(voteSuccess){
//			;
//		}else{
//			e.preventDefault();
//			var fv = serializeForm(form_node,true);
//			var selected = fv["_relationModelId"]; 
//			if(!selected){
//				showInfow("请至少选择一项",Y.one(Y.one('#' + voteCfg.vote_form_id)));
//				return;
//			}
//			
//			var selectNum = 1;
//			if(Y.Lang.isArray(selected)){
//				selectNum = selected.length;
//			}
//			
//			if(selectNum < mins || selectNum > maxs){
//				showInfow("必须选择" + mins + '到' + maxs + '个',Y.one(voteCfg.container_dom_id));
//				return;
//			}
//			
//			var  query = Y.QueryString.stringify(fv);
//			doVote(query);
//		}
//	});
//}

//var onVoteFetchFailure = function(o){
//	alert("获取投票数据失败，请检查是否提供了正确的voteCfg.vote_id");
//};
//
//var onVoteFetchSuccess = function(o){
//	loading = false;
//	if(o.tId in tIds){
//		;
//	}else{
//		;
//	}
//};

//com.m3958.vote.display[uniqueInstance].afterDoVote = function(results){
//	tIds[currentTid] = true;
//	form_node.all('input').set("disabled",true);
//	voteSuccess = true;
//	showInfow("投票成功！",Y.one(voteCfg.vote_form_id));
//};
//
//var doVote = function(qs){
//    // block multiple requests
//    if (loading) {
//        return;
//    }
//    loading = true;
//
//    var sURL = "/jqfprocessor?" + qs + '&callback=com.m3958.vote.display.'+ uniqueInstance +'.afterDoVote';
//    var transactionObj = Y.Get.script(sURL, {
//        onFailure: onVoteFetchFailure,
//        onSuccess:onVoteFetchSuccess,
//        timeout: 20000,
//        context: Y
//    });
//    // keep track of the current transaction id.  The transaction will be
//    // considered complete only if the web service callback is executed.
//    currentTid = transactionObj.tId; 	
//}


//var getVoteInfo = function() {
//    // block multiple requests
//    if (loading) {
//        return;
//    }
//    loading = true;
//    var sURL = "/jsonp?_modelName=com.m3958.firstgwt.model.Vote&id=" + voteCfg.vote_id + '&callback=com.m3958.vote.display.'+ uniqueInstance +'.fetchVoteInfo';
//    var transactionObj = Y.Get.script(sURL, {
//        onFailure: onVoteFetchFailure,
//        onSuccess:onVoteFetchSuccess,
//        timeout: 20000,
//        context: Y
//    });
//    currentTid = transactionObj.tId;
//};



//com.m3958.vote.display[uniqueInstance].fetchVoteInfo = function(results){//global function,but can remember environment;
//	tIds[currentTid] = true;
//	var vdata = results.response.data;
//	var mins = vdata.minSelect,maxs=vdata.maxSelect;
//	if(mins < 1)mins = 1;
//	if(maxs < 1)maxs = 1;
//	if(mins < 2 && maxs < 2){
//		vitem_tpl = Y.Lang.sub(vitem_tpl,{vtype:'radio'});
//	}else{
//		vitem_tpl = Y.Lang.sub(vitem_tpl,{vtype:'checkbox'});
//	}
//	
//	if(!voteCfg.vtitle){
//		voteCfg.vtitle = vdata.name;
//	}
//	voteCfg.vote_start_date = vdata.startDate;
//	voteCfg.vote_end_date = vdata.endDate;
//	
//	var voteFormHtml = createVoteDom(vdata).getContent();
//	
//	voteFormHtml = Y.Lang.sub(voteFormHtml,voteCfg);
//	
//	var voteFormNode = Y.Node.create(voteFormHtml);
//	voteNode = Y.Node.create('<div></div>');
//	voteNode.setStyles({width:voteCfg.width,height:voteCfg.height});
//	voteNode.append(voteFormNode);
//	form_node = voteFormNode;
//	Y.one(voteCfg.container_dom_id).append(voteNode);
//	
//	//from here all vote element are present.
//	form_node.on('submit',function(e){
//		if(voteSuccess){
//			;
//		}else{
//			e.preventDefault();
//			var fv = serializeForm(form_node,true);
//			var selected = fv["_relationModelId"]; 
//			if(!selected){
//
//				showInfow("请至少选择一项",Y.one(voteCfg.container_dom_id));
//				return;
//			}
//			
//			var selectNum = 1;
//			if(Y.Lang.isArray(selected)){
//				selectNum = selected.length;
//			}
//			
//			if(selectNum < mins || selectNum > maxs){
//				showInfow("必须选择" + mins + '到' + maxs + '个',Y.one(voteCfg.container_dom_id));
//				return;
//			}
//			
//			var  query = Y.QueryString.stringify(fv);
//			doVote(query);
//		}
//	});
//}

//var onVoteFetchFailure = function(o){
//	alert("获取投票数据失败，请检查是否提供了正确的voteCfg.vote_id");
//};

//var onVoteFetchSuccess = function(o){
//	Y.log(o);
//	loading = false;
//	if(o.tId in tIds){
//		;
//	}else{
//		;
//	}
//};

//com.m3958.vote.display[uniqueInstance].afterDoVote = function(results){
//	tIds[currentTid] = true;
//	voteNode.all('input[name=_relationModelId]').set("disabled",true);
//	voteNode.one('.m3958_vote_submit').set('disabled',true);
//	voteSuccess = true;
//	form_node.submit();
//};

//var doVote = function(qs){
//    // block multiple requests
//    if (loading) {
//        return;
//    }
//    loading = true;
//
//    var sURL = "/jqfprocessor?" + qs + '&callback=com.m3958.vote.display.'+ uniqueInstance +'.afterDoVote';
//    var transactionObj = Y.Get.script(sURL, {
//        onFailure: onVoteFetchFailure,
//        onSuccess:onVoteFetchSuccess,
//        timeout: 20000,
//        context: Y
//    });
//    currentTid = transactionObj.tId; 	
//}



//var getVoteInfo = function() {
//    // block multiple requests
//    if (loading) {
//        return;
//    }
//    loading = true;
//    var sURL = "/jsonp?_modelName=com.m3958.firstgwt.model.Vote&id=" + voteCfg.vote_id + '&callback=com.m3958.vote.display.'+ uniqueInstance +'.fetchVoteInfo';
//    var transactionObj = Y.Get.script(sURL, {
//        onFailure: onVoteFetchFailure,
//        onSuccess:onVoteFetchSuccess,
//        timeout: 20000,
//        context: Y
//    });
//    currentTid = transactionObj.tId;
//};
