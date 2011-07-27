/**
 * 
 */

YUI.add('votetab-module', function(Y) {
	//all JavaScript code goes here
	
	 var voteTab,voteTree,voteTreeToolbar,voteForm,voteList;
	 
	 var uniquedomid = new Y.UniqueDomId('votetab-module');
	 
	 var ns = Y.namespace('m3958.webmastertools');
	 
	 Y.on('click',function(e){
		 if(voteList.highlightedNode){
			 this.set('href',this.get('href') + '?vote_id=' + voteList.getHighlightedVoteId());
		 }else{
			 e.preventDefault();
			 Y.messageBox.warn("没有选定的Vote");
		 }
	 },'#vote-test-link');
	
	  /* -- votetree toolbar start --*/
	  function VoteTreeToolbar(){
			var tree_toolbar_node = Y.one('#tree-toolbar');
			tree_toolbar_node.delegate('click',function(e){
				e.preventDefault();
				if(voteTreeToolbar.disabled())return;
				if(e.currentTarget.getContent() == '删除'){
					voteTree.removeHighlightedNode();
					voteForm.hide();
					voteTreeToolbar.disable();
				}else{
					voteForm.show();
					if(e.currentTarget.getContent() == '添加'){
						voteForm.newNode();
					}else{
						voteForm.editNode(voteTree.getHighlightedNode());
					}
				}
			},'a');
			
			var disablecss = 'tree-toolbar-disabled';
			this.disable = function(){
				tree_toolbar_node.addClass(disablecss);
				};
			this.enable = function(){
				tree_toolbar_node.removeClass(disablecss);
			};
			this.node = tree_toolbar_node;
			this.disabled = function(){
				return tree_toolbar_node.hasClass(disablecss);
			};
	}
	/* -- votetree toolbar end --*/
	  
  /* -- create voteform start --*/
	  
	  	function VoteForm(fielddefine){
	  			var vote_form_div_node = Y.one('#vote-form-div');
	  			vote_form_div_node.append('<form id="vote-form"><table></table></form>');
	  			var f = vote_form_div_node.one('#vote-form');

	  			f.append('<input type="hidden" name="_modelName" value="com.m3958.firstgwt.model.Vote" />');
	  			f.append('<input type="hidden" name="_operationType" value="add" id="opfield"/>');
			 	f.append('<input type="hidden" name="_tz" value="' + _tz + '" />');
			 	f.append('<input type="hidden" name="parentId" value="-1" id="parentidfield"/>');
			 	f.append('<input type="hidden" name="id" value="" id="idfield"/>');
			 	f.append('<input type="hidden" name="siteId" value="" id="siteIdfield"/>');
			 	f.append('<input type="hidden" name="bianmachecker" value="我是utf-8" />');
			 	
	  			var t = Y.one('#vote-form table');
  				var trtpl = '<tr>' +
					'<td><label for="{field_dom_id}">{field_title}：</label></td>' +
					'<td><input type="{inputtype}" name="{field_name}" id="{field_dom_id}" value=""/></td>' +
					'</tr>';
  				
  				Y.log(t);
  				Y.each(fielddefine,function(value){
  					value.field_dom_id = uniquedomid.next();
  					if(!value.inputtype)value.inputtype = 'text';
					t.append(Y.Lang.sub(trtpl,value));
  				});
  				
  				t.append( '<tr>' +
  						'<td><input type="submit" name="submit" value="新建/保存"/></td>' +
  						'<td></td>' +
  						'</tr>');
  				
  				f.on('submit',function(e){
  					e.preventDefault();
  					var tH = {
  							success: function(id, o, args) {
  										var isedit = args.isedit;
	  									var res = Y.JSON.parse(o.responseText);
	  						    		var vdata = res.response.data;
	  						    		if(res.response.status == 0 && vdata.length == 1){
	  						    			if(isedit){
	  						    				voteTree.updateNode(vdata[0]);
	  						    				voteList.updateVote(vdata[0]);
	  						    				Y.messageBox.prompt("已更新！");
	  						    			}else{
	  						    				voteTree.addNode(vdata[0]);
	  						    				Y.messageBox.prompt("添加成功！");
	  						    			}
	  						    			voteForm.hide();
	  						    		}else{
	  						    			Y.messageBox.warn("操作失败！");
	  						    		}
  									 },
  							failure: function(response, args) {
  										Y.messageBox.warn("更新失败！");
  									 }

  						};
  					var cfg = {
  							method:'POST',
  							form: {
  								id: 'vote-form',
  								useDisabled: true
  							},
  							on: {
  								success: tH.success,
  								failure: tH.failure
  							},
  							context: tH,
  							arguments: { 'isedit' : voteForm.isedit }
  						};
  					
  				    var url = '/smartcfud';
  				    Y.io(url, cfg);
  				});

			  var close = Y.Node.create('<a href="#" style="font-weight:bolder;border:1px solid gray;position:absolute;">X</a>');
			  close.plug(Y.Plugin.Align)
			  vote_form_div_node.append(close);
			  
			  close.on('click',function(e){
				  e.preventDefault();
				  vote_form_div_node.hide();
			  });
			  
			  var inputNodes = Y.all('#vote-form input');
			  var optypeNode = Y.one('#opfield');
			  var parentIdNode = Y.one('#parentidfield');
			  var idNode = Y.one('#idfield');
			  var siteIdNode = Y.one('#siteIdfield');
			  
			  Y.log(inputNodes);
			  this.isedit = false;
			  this.visible = false;
			  this.hide = function(){
				  vote_form_div_node.hide();
				  this.visible = false;
			  };
			  this.show = function(){
				  vote_form_div_node.show();
				  close.align.to(vote_form_div_node,'tr','tr',true);
				  this.visible = true;
			  };
			  
			  this.editNode = function(tnode){
				  this.newNode();
				  this.isedit = true;
				  inputNodes.each(function(input){
					  var v = tnode.data[input.get('name')]; 
					  if(v){
						  input.set('value',v);
					  }
				  });
				 optypeNode.set('value','update');
				  
			  };
			  this.newNode = function(){
				  this.isedit = false;
				  Y.log(inputNodes);
				  inputNodes.each(function(input){
					  var ty = input.get('type');
					  if(ty == 'submit'){
						  ;
					  }else if(ty == 'text'){
						  input.set('value',"");
					  }
				  });
				  optypeNode.set('value','add');
				  parentIdNode.set('value',voteTree.getHighlightedNode().data.id);
				  siteIdNode.set('value',voteTree.getHighlightedNode().data.siteId);
				  idNode.set('value',"");
			  };
		  };
		  
		  /* -- create voteform end --*/
		 
			/* -- create votetree start --*/
		function VoteTree(){
			var YAHOO = Y.YUI2;
			var tree;
		    
			//每个递归处理一个node。
			function recursive(tnode,node_data){
				//process tnode
				//if hasChildren recursive
				var local_node_data = Y.merge(node_data);
				delete local_node_data.children;
				var cur_tnode = new YAHOO.widget.TextNode({label:node_data.name,expanded: true,data:local_node_data}, tnode);
				var children = node_data.children;
				for(var i=0;i<children.length;i++){
					recursive(cur_tnode,children[i]);
				}
			}
			
			this.createTree = function(voteId){
				if(tree && tree.getRoot() && tree.getRoot().children.length > 0 &&  tree.getRoot().children[0].data.id == voteId){
					return;
				}
				if(tree){
					tree.destroy();
				}
				voteTreeToolbar.disable();
				
				function highlightNode(oArgs){
					//tree.onEventToggleHighlight(oArgs);
					//copy from Treeview.js
			        var node;
			        if ('node' in oArgs && oArgs.node instanceof YAHOO.widget.Node) {
			            node = oArgs.node;
			        } else if (oArgs instanceof YAHOO.widget.Node) {
			            node = oArgs;
			        } else {
			            return false;
			        }
			        node.highlight(true);
//			        node.toggleHighlight();
			        return false;
				}
				var tH = {
						success: function(response, args) {
					    		var vdata = response.response.data;
						          tree = new YAHOO.widget.TreeView("treeDiv1");
						          var root = tree.getRoot();
						          recursive(root,vdata);
							  		tree.subscribe("clickEvent", function(e) {
							  			Y.log(e.node);
							  			voteTreeToolbar.enable();
							  			if(voteForm.visible)voteForm.editNode(e.node);
							        });
									tree.singleNodeHighlight = true;
									tree.subscribe('clickEvent',/*tree.onEventToggleHighlight*/highlightNode);
									tree.render();
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
				
			    var url = '/jsonp?_modelName=com.m3958.firstgwt.model.Vote&want=jsonb&callback={callback}&id=' + voteId;
			    Y.log(url);
			    Y.jsonp(url, cfg);
			}
			
			this.getHighlightedNode = function(){
				return tree.getHighlightedNode();
			};
			
			this.addNode = function(node_data){
				var tnode = tree.getHighlightedNode();
				var cur_tnode = new YAHOO.widget.TextNode({label:node_data.name,expanded: true,data:node_data}, tnode);
				if(tnode.parent)tnode.parent.refresh();
				tnode.refresh();
				tnode.expand();
				
				voteForm.hide();
			}
			this.updateNode = function(node_data){
				var tnode = tree.getHighlightedNode();
				tnode.label = node_data.name;
				tnode.data = node_data;
				if(tnode.parent)tnode.parent.refresh();
				tnode.refresh();
				tnode.expand();
				
				voteForm.hide();
			}
			this.removeHighlightedNode = function(){
				var tnode = tree.getHighlightedNode();
				var tH = {
						success: function(id, o, args) {
									var res = Y.JSON.parse(o.responseText);
						    		var vdata = res.response.data;
						    		if(res.response.status == 0 && vdata.length == 1){
										var pn = tnode.parent;
										tree.removeNode(tnode);
										voteList.deleteVote(vdata[0]);
										pn.refresh();
										Y.messageBox.prompt("已删除！");
						    		}else{
						    			Y.messageBox.warn("删除失败！");
						    		}
						    		
								 },
						failure: function(response, args) {
							Y.messageBox.warn("删除失败！");
								 }

					};
				var cfg = {
						method:'POST',
						data:'_operationType=remove&_modelName=com.m3958.firstgwt.model.Vote&id=' + tnode.data.id,
						on: {
							success: tH.success,
							failure: tH.failure
						},
						context: tH
					};
				
			    var url = '/smartcfud';
			    Y.io(url, cfg);
			};
		}
		/* -- create votetree end --*/
		
		function getVoteId(domid){
			var ss = domid.split('-');
			return ss[ss.length-1];
		}
		
		//新建投票之后，1、添加到投票列表，2、更新tree
		iframeCallbacks.voteCallback = function(res){
			var v = res.response.data[0];
			voteList.addVote(v);
			voteTree.createTree(v.id);
		}
		
		function VoteList(){
			var vlNode;
			var vitpl = '<li id="{optionid}" style="cursor:pointer;">{optionname}</li>';
			var outerthis = this;
			this.highlightedNode = null;
			this.showVoteList = function(){
				var tH = {
						success: function(response, args) {
									var vdata = response.response.data;
									vlNode = voteTab.one('#user-vote-list');
						    		Y.each(vdata,function(vote){
						    			vlNode.append(Y.Lang.sub(vitpl,{optionid:uniquedomid.next() + '-' + vote.id,optionname:vote.name}));
						    		});
						    		vlNode.delegate('click',function(e){
						    			vlNode.all('li').setStyles({backgroundColor:'',color:''});
						    			this.setStyles({backgroundColor:'blue',color:'white'});
						    			outerthis.highlightedNode = this;
						    			var vid = getVoteId(this.get('id'));
						    			voteTree.createTree(vid);
						    			voteForm.hide();
						    		},'li');
								},
						failure: function(response, args) {
									Y.messageBox.warn("获取数据失败！");
								 }

					};
				var cfg = {
						on: {
							success: tH.success,
							failure: tH.failure
						},
						context: tH
					};
				Y.log('userid:' + ns.user.id);
			    var url = '/jsonp?_modelName=com.m3958.firstgwt.model.Vote&want=jsonb&callback={callback}&what=myvote&userId=' + ns.user.id;
			    Y.log(url);
			    Y.jsonp(url, cfg);
			}
			
			
			this.addVote = function(v){
				var curnode = Y.Node.create(Y.Lang.sub(vitpl,{optionid:uniquedomid.next() + '-' + v.id,optionname:v.name}));
				vlNode.append(curnode);
    			vlNode.all('li').setStyles({backgroundColor:'',color:''});
    			curnode.setStyles({backgroundColor:'blue',color:'white'});
			}
			
			this.getHighlightedVoteId = function(){
				if(!this.highlightedNode)return null;
				var pattern = new RegExp( "-(\\d+)$", "gi" );
				var matches = pattern.exec(this.highlightedNode.get('id'));
				if(matches){
					return matches[1];
				}else{
					return null;
				}
			};
			
			this.deleteVote = function(node_data){
				vlNode.all('li').some(function(li){
					var pattern = new RegExp( "-" + node_data.id + "$", "gi" );
					var matches = pattern.exec( li.get('id') );
					if(matches){
						li.remove(true);
						outerthis.highlightedNode = null;
						return true;
					}
				});
			};
			this.updateVote = function(node_data){
				vlNode.all('li').some(function(li){
					var pattern = new RegExp( "-" + node_data.id + "$", "gi" );
					var matches = pattern.exec( li.get('id') );
					if(matches){
						li.setContent(node_data.name);
						return true;
					}
				});
			};
		}
		
		function newVote(){
			 voteTab.one('#newvotelink').on('click',function(e){
				 e.preventDefault();
				 var f = Y.one('#submit-form');
				 f.set('action','/formprocessor');
				 f.set('method','POST');
				 f.append('<input type="hidden" name="_modelName" value="com.m3958.firstgwt.model.Vote" />');
				 f.append('<input type="hidden" name="_tz" value="' + _tz + '" />');
				 f.append('<input type="hidden" name="parentId" value="-1" />');
				 f.append('<input type="hidden" name="_operationType" value="add" />');
				 f.append('<input type="hidden" name="name" value="这样新建投票你还习惯吗？" />');
				 f.append('<input type="hidden" name="bianmachecker" value="我是utf-8" />');
				 
				 Y.log(f);
				 f.submit();
			 });
		}
		
		 Y.Votetab = function(){
			 this.initialized=false;
			 this.init = function(){
				 voteTab = Y.one('#votetab');
				 voteTree = new VoteTree();
				 voteTreeToolbar = new VoteTreeToolbar();
				 voteForm = new VoteForm([{field_name:'name',field_title:'标题'},
				                          {field_name:'position',field_title:'排序'},
				                          {field_name:'minSelect',field_title:'最少选项'},
				                          {field_name:'maxSelect',field_title:'最多选项'},
				                          {field_name:'startDate',field_title:'开始时间'},
				                          {field_name:'endDate',field_title:'结束时间'}]);
				 
				 
				 voteList  = new VoteList();
				 voteList.showVoteList();
				 newVote();
				 this.initialized = true;
			 };
			 this.show = function(){
				 if(!this.initialized){
					 this.init();
					 this.initialized = true;
				 }
				 voteTab.removeClass('inithiden');
			 };
			 this.hide = function(){
				 voteTab.addClass('inithiden');
			 };
			 this.get = function(p){
				if('id' == p){
					return voteTab.get('id');
				} 
			 };
		 };
	},'0.0.1', {requires:[]}
);