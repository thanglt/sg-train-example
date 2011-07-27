//json.nl,wt=json
//json.wrf  wrapper-function

//alert(window.location.href);
					
YUI({ filter: 'raw' }).use("io-base","jsonp","json", "node","dump","querystring","history","event-delegate","highlight",function (Y) {
	
	var uniqueId = (function(){
		var id = 0;
		return function(){return "m3958uid" + id++;};
	})();
	
	var timezone = jzTimezoneDetector.determine_timezone().timezone,
		lurl = window.location.href + "",
		url,
		uniqueHash={};
	
	if(lurl.indexOf("8888") != -1){
		url = "http://localhost:8080/solr/select?json.wrf={callback}&wt=json&";
	}else{
		url = "http://search.m3958.com/solr/select?json.wrf={callback}&wt=json&";
	}
    
	var template = //结果条目模板
		'<div class="result-document">' +
			'<div class="result-title"><a href="{url}" target="_blank"><b>{title}</b></a></div>' +
			'<div class="map">{titleImg}</div>' + 
			'<div>摘要:{excerpt}</div>' +
			'<div>最后更新: {updatedAt}</div>' +
			'<div class="mlt"><span class="mltl"><a href="#" id="{uniqueKey}">相似文章</a></span></div>' +
		'</div>',
		history = new Y.HistoryHash(),
		q_node = Y.one('#q'),
		constraints_node = Y.one('.constraints'),
		fieldFacetContainer = Y.one('#field-facet-container'),
		dateFacetContainer = Y.one('#date-facet-container'),
		resultContainer = Y.one('.results'),
		mainContainer = Y.one('#main .content'),
		leftContainer = Y.one('#nav .content'),
		navigatorsContainer = Y.one('.navigators'),
		noresult_node = Y.one('#noresult'),
		results_found_node = Y.all('.results-found'),
		qtime_node = Y.all('.qtime'),
		page_num_node = Y.all('.page-num'),
		page_count_node = Y.all('.page-count'),
		previous_page_node = Y.all('.previouspage'),
		next_page_node = Y.all('.nextpage'),
		facet_field_node = Y.one('#facet-field'),
		qo = {},
		paginator,
		website;
		
	function getNextMonth(ms){
		//2011-04-25T00:00:00Z
		var ss = ms.split("-");
		var m = parseInt(ss[1]);
		if(m < 12){
			m = m + 1;
			if(m<10){
				ss[1] = "0" + m;
			}else{
				ss[1] = m;
			}
		}else{//m == 12
			var y = parseInt(ss[0]);
			ss[0] = y + 1;
			ss[1] = 1;
		}
		return ss[0] + "-" + ss[1] + "-" + ss[2];
		
	}
	
	mainContainer.delegate('click',function(e){
		var ta = e.currentTarget;
		if(ta.hasClass("mltl")){
			e.preventDefault();
			return;
			var a = ta.one('a');
			var l = "q=uniqueKey:" + a.get('id') + "&mlt=true&mlt.fl=complex";
			sendMoreLikeThis(l);
		}
	},"span");
	
	constraints_node.delegate('click',function(e){
		e.preventDefault();
		var ta = e.currentTarget;
		var onefq = ta.get("title");
		qo.deletefq(onefq);
		qo.start = 0;
		sendJsonP(true);
	},"a");
	
	navigatorsContainer.delegate('click',function(e){
		e.preventDefault();
		var ta = e.currentTarget;
		if(ta.hasClass("facet-section-link")){
			qo.addfq("section:" + uniqueHash[ta.get("id")]);
			qo.start = 0;
			sendJsonP(true);
		}else if(ta.hasClass("facet-tag-link")){
			qo.addfq("tag:" + ta.getContent());
			qo.start = 0;
			sendJsonP(true);
		}else if(ta.hasClass("facet-publishedat-link")){
			var dd = uniqueHash[ta.get("id")];
			qo.addfq("publishedAt:[" + dd + " TO " + getNextMonth(dd) + "]");
			qo.start = 0;
			sendJsonP(true);
		}
	},"a");
	
	
		
	Y.on('click',function(e){
		e.preventDefault();
		if(this.hasClass("previouspage")){
			qo.start = paginator.getPreviousStart();
			sendJsonP(true);
		}
		
		if(this.hasClass("nextpage")){
			qo.start = paginator.getNextStart();
			sendJsonP(true);
		}
		
	},previous_page_node.concat(next_page_node));

	/*
    "echoParams":"all",
    "rows":"10",
    "explainOther":"",
    "facet":"true",
    "indent":"on",
    "hl.fl":"",
    "wt":"json",
    "rows":"10",
    "version":"2.2",
    "fl":"*,score",
    "start":"0",
    "q":"title:西伯利亚 complex:西伯利亚",
    "facet.field":["section",
      "tag"],
    "qt":"",
    "fq":"siteId:9568"}},
	*/
    
	var QueryObject = function () {
	    return {
	        addfq : function (onefq) {
	            if (!this.fq) {
	               	this.fq = [];
	               	this.fq.push(onefq);
	            }else{
	            	if(Y.Lang.isArray(this.fq)){
	            		if(Y.Array.indexOf(this.fq,onefq) == -1)
	            			this.fq.push(onefq);
	            	}else{
	            		this.fq = [this.fq];
	            		this.fq.push(onefq);
	            	}
	            }
	        },
	        deletefq : function(onefq){
	        	var i = Y.Array.indexOf(this.fq,onefq); 
	        	if( i != -1){
	        		this.fq = this.fq.slice(0,i);
	        	}
	        },
	        setDefaultValues:function(){
	    		if(Y.Lang.isUndefined(this["start"]))this["start"] = 0;
	    		if(Y.Lang.isUndefined(this["rows"]))this["rows"] = 10;
	    		if(Y.Lang.isUndefined(this["facet"]))this["facet"] = "true";
	    		if(Y.Lang.isUndefined(this["indent"]))this["indent"] = "off";
	    		if(Y.Lang.isUndefined(this["facet.field"]))this["facet.field"] = ["section","tag"];
	    		if(Y.Lang.isUndefined(this["facet.missing"]))this["facet.missing"] = "true";
	    		if(Y.Lang.isUndefined(this["facet.date"]))this["facet.date"] = "publishedAt";
	    		if(Y.Lang.isUndefined(this["facet.mincount"]))this["facet.mincount"] = 1;
	    		if(Y.Lang.isUndefined(this["facet.date.start"]))this["facet.date.start"] = "NOW/DAY-10YEARS";
	    		if(Y.Lang.isUndefined(this["facet.date.end"]))this["facet.date.end"] = "NOW/DAY";
	    		if(Y.Lang.isUndefined(this["facet.date.gap"]))this["facet.date.gap"] = "+1MONTH";
	    		if(Y.Lang.isUndefined(this["hl"]))this["hl"] = "true";
	    		if(Y.Lang.isUndefined(this["hl.fl"]))this["hl.fl"] = ["title","excerpt"];
	        }
	    }
	}();
	
	Y.mix(qo,QueryObject);
	
	function Paginator(total,start,pageSize){
		this.total = parseInt(total);
		this.start = parseInt(start);
		this.pageSize = parseInt(pageSize);
		
		this.getNextStart = function(){
			var ns = this.start + this.pageSize;
			if(ns >= this.total){
				return -1;
			}else{
				return ns;
			}
		};
		this.getPreviousStart = function(){
			var ns = this.start - this.pageSize;
			if(ns < 0){
				return -1;
			}else{
				return ns;
			}
		};
		
		this.getCurrentPageNum = function(){
			return Math.floor(this.start/this.pageSize) + 1;
		};
		
		this.getPageCount = function(){
			if(this.total % this.pageSize == 0){
				return this.total/this.pageSize;
			}else{
				return Math.floor(this.total/this.pageSize) + 1;
			}
		}
		
	}

	var requestFirer = {
			historyTokenChange:function(htoken){//历史回溯的话，已经包含了所有必要的参数。没有使用mixin的方法，所以不需要mixin
				if(htoken){
					qo = Y.QueryString.parse(htoken);
//					Y.mix(qo,QueryObject);
//					delete qo.addfq;
//					delete qo.deletefq;
//					delete qo.setDefaultValues;
//					Y.mix(qo,QueryObject);
					q_node.set('value',qo.q);
					sendJsonP(false);
					return true;
				}else{
					var i = lurl.indexOf("#url=");
					if(i != -1){
						var qs = lurl.substring(i+5);
						qs = decodeURIComponent(qs);
						qo = Y.QueryString.parse(qs);
//						Y.mix(qo,QueryObject);
//						delete qo.addfq;
//						delete qo.deletefq;
//						delete qo.setDefaultValues;
//						Y.mix(qo,QueryObject);
						q_node.set('value',qo.q);
						sendJsonP(false);
						return true;
					}else{
						return false;
					}
				}
			},
			queryStringChange:function(){//url直接调用的话，因为需要使用mixin的setdefaultValue，所以必须mixin，而且minix之后不能删除mixin的方法，不然的话按钮查询的时候会出错，因为用到了setdefault方法。
				var i = lurl.indexOf("?");
				if(i != -1){
					var qs = lurl.substring(i+1);
					qo = Y.QueryString.parse(qs);
					Y.mix(qo,QueryObject);
					qo.setDefaultValues();
					if(qo.siteId){
						qo.fq = "siteId:" + qo.siteId;
					}
					if(!qo.q){
						qo.q = "*:*";
					}
//					delete qo.siteId;
//					delete qo.addfq;
//					delete qo.deletefq;
//					delete qo.setDefaultValues;
					if(qo.q){
						q_node.set('value',qo.q);
					}else{
						q_node.set('value',"*:*");
					}
					sendJsonP();
					return true;
				}else{
					return false;
				}
			}
	};

		
	Y.on('domready',function(){
		if(lurl.indexOf("siteId") != -1){
			if(!requestFirer.historyTokenChange()){
				requestFirer.queryStringChange();
			}
		}
		Y.io('/jsonp',{method:'GET',
						data: {_modelName:'com.m3958.firstgwt.model.WebSite'},
						on:{
							success:function(id,o){
								var alerterror = function(){
									alert("站点解析错误，请刷新浏览器尝试一下！");
								};
								try {
								    var response = Y.JSON.parse(o.responseText);
								    if(response.response.status != 0){
								    	alerterror();
								    }else{
								    	if(Y.Lang.isArray(response.response.data)){
								    		if(response.response.data.length == 1){
								    			website = response.response.data[0];
								    			qo.fq = "siteId:" + website.id;
								    			Y.one('#querySubmit').set('disabled',false);
								    			//qo = {indent:'off',version:2.2,fq:'siteId:' + website.id,
	//									    						start:0,rows:10,fl:'*,score',qt:'',wt:'json',explainOther:'',"hl.fl":'',q:getQvalue(),
	//									    						facet:'true',"facet.field":'title',"f.title.facet.prefix":'',"facet.date":'publishedAt'};
								    		}else{
								    			alerterror();
								    		}
								    	}else{
								    		alerterror();
								    	}
								    }
								}catch (e) {
									alerterror();
								}
							},
							failure:function(id,o){
								//alert(Y.dump(o));
							}
						}
					});
		});
	
	
	
    
    function getQvalue(){
    	var qv = q_node.get('value');
    	if(!qv){
    		qv = '*:*';
    	}
    	return qv;
    }
    
    function displayDocs(docs){
        Y.each(docs,function(doc,index){
        	var tt = {};
        	tt.title = Y.Highlight.all(doc.title,highlights());
        	tt.excerpt = Y.Highlight.all(doc.excerpt,highlights());
        	tt.url = doc.url;
        	tt.updatedAt = doc.updatedAt;
        	tt.uniqueKey = encodeURIComponent(doc.uniqueKey);
        	if(doc.titleImg){
        		tt.titleImg = '<a href="'+ doc.titleImg +'" target="_blank"><img src="'+ doc.titleImg +'" alt="" title="" style="width:48px;height:48px;"/></a>';
        	}else{
        		tt.titleImg = "";
        	}
        	resultContainer.append(Y.Lang.sub(template,tt));
        });
    }
    
    var highlights = (function(){
    	var highlightWords = [];
    	
        var extractHighlights = function(str){
        	var pattern = new RegExp( "<em>(.*?)</em>", "gi" );
        	while (matches = pattern.exec( str )){
        		var ms = matches[1];
        		if(Y.Array.indexOf(highlightWords,ms) == -1){
        			highlightWords.push(ms);
        		}
        	}
        };
    	
    	return function(solrhl,clear){
    		if(!solrhl)return highlightWords;
    		if(clear){
    			highlightWords = [];
    			if(qo && qo.q){
    				var qos = qo.q.split(" ");
    				Y.each(qos,function(value){
    					if(value){
    						highlightWords.push(value);
    					}
    				});
    			}
    		}
	        Y.each(solrhl,function(value){
	        	if(Y.Lang.isArray(value.title)){
	        		Y.each(value.title,function(v1){
	        			extractHighlights(v1);
	        		});
	        	}else{
	        		extractHighlights(value.title);;
	        	}
	        	if(Y.Lang.isArray(value.excerpt)){
	        		Y.each(value.excerpt,function(v1){
	        			extractHighlights(v1);
	        		});
	        	}else{
	        		extractHighlights(value.excerpt);;
	        	}
	        });
    	};
    })();
    
    
    var responseHandler = {
    		moreLikeThisResponse:function(){
    			//alert(Y.dump(moreLikeThis));
    			return;
    		},
    		solrDocResponse:function(){
    			
    			return;
    		}
    };
    
    
    function handleJSONP(res) {
    	
    	q_node.setStyle("background","");
    	
        var solrResponseHeader = res.responseHeader;
        var solrDocResponse = res.response;
        var moreLikeThis = res.moreLikeThis;
        
        
        if(moreLikeThis){
        	//alert(Y.dump(moreLikeThis));
        	return;
        }
        
        //docresponsehandler
        uniqueHash = {};
        if(solrDocResponse.numFound == 0){
        	noresult_node.show();
        	hideContents();
        	Y.one('#qs').setContent(getQvalue());
        }else{
        	noresult_node.hide();
        	resultContainer.empty(false);
        	results_found_node.setContent(solrDocResponse.numFound);
        	qtime_node.setContent(solrResponseHeader.QTime);
        	
        	showContents();
        	//高亮字段收集
//        	alert(Y.dump(res.highlighting,true));
        	highlights(res.highlighting,true);
        	//add doc list
        	
        	displayDocs(solrDocResponse.docs);
            
            //过滤路径
            constraints_node.empty(false);
            var constraints_tt = '>{onefq}<a href="#" title="{onefq}">X</a> ';
            if(qo.fq && !Y.Lang.isArray(qo.fq)){
            	qo.fq = [qo.fq];
            }
            Y.each(qo.fq,function(value){
            	if(value.indexOf("siteId") == -1)
            	constraints_node.append(Y.Lang.sub(constraints_tt,{onefq:value}));
            });
            
            
            //面搜索结果
            var facet_counts = res.facet_counts;
            
            fieldFacetContainer.empty(false);
            dateFacetContainer.empty(false);
            if(facet_counts){
                var facet_fields = facet_counts.facet_fields;
                
                //字段分类
                var sections = facet_fields.section;
                var tags = facet_fields.tag;
                if(sections && Y.Lang.isArray(sections)){
                    fieldFacetContainer.append('<span class="facet-field">目录</span>');
                    var ulnode = Y.Node.create('<ul></ul>');
                    fieldFacetContainer.append(ulnode);
                    
                    var tt = '<li><a href="#" class="facet-section-link" id="{uid}">{sectionName}</a> ({articleCount})</li>';
    				for(var i=0;i<sections.length;){
    					var snid = sections[i++];
    					var c = sections[i++];
    					if(snid){
    						var ss = snid.split(",");
    						if(ss.length == 2){
    							var uid = uniqueId();
    							uniqueHash[uid] = snid;
    							var so = {sectionName:ss[0],articleCount:c,uid:uid};
    							ulnode.append(Y.Lang.sub(tt,so));
    						}
    					}
    				}
                }
                
                if(tags && Y.Lang.isArray(tags)){
                    fieldFacetContainer.append('<span class="facet-field">标签</span>');
                    var ulnode = Y.Node.create('<ul></ul>');
                    fieldFacetContainer.append(ulnode);
                    
                    var tt = '<li><a href="#" class="facet-tag-link">{tagName}</a> ({articleCount})</li>';
    				for(var i=0;i<tags.length;){
    					var tn = tags[i++];
    					var c = tags[i++];
    					if(tn){
   							var so = {tagName:tn,articleCount:c};
   							ulnode.append(Y.Lang.sub(tt,so));
    					}
    				}
                }
                
                
                
                //日期分类
                var formatDate = function(ds){
                	var ss = ds.split('T');
                	ss = ss[0].split("-");
                	return ss[0] + "年" + ss[1] + "月";
                };
                
                var facet_dates = facet_counts.facet_dates;
                var publishedAtMonthes = facet_dates.publishedAt;
				if(publishedAtMonthes && Y.Lang.isObject(publishedAtMonthes)){
                	dateFacetContainer.append('<span class="facet-field">发布日期</span>');
                	var ulnode = Y.Node.create('<ul></ul>');
                	dateFacetContainer.append(ulnode);
                    var tt = '<li><a href="#" class="facet-publishedat-link" id="{uid}">{yearmonth}</a> ({articleCount})</li>';
	                Y.each(publishedAtMonthes,function(value,key){
	                	if("gap" == key || "start" == key || "end" == key){
	                		;
	                	}else{
	                		var uid = uniqueId();
	                		uniqueHash[uid] = key;
 							var so = {yearmonth:formatDate(key),articleCount:value,uid:uid};
 							ulnode.append(Y.Lang.sub(tt,so));
	                	}
	                });					
				}           
            }
            
            //分页器
            paginator = new Paginator(solrDocResponse.numFound,solrDocResponse.start,solrResponseHeader.params.rows);
            page_num_node.setContent(paginator.getCurrentPageNum());
            page_count_node.setContent(paginator.getPageCount());
            if(paginator.getPreviousStart() != -1){
            	previous_page_node.show();
            }else{
            	previous_page_node.hide();
            }
            
            if(paginator.getNextStart() != -1){
            	next_page_node.show();
            }else{
            	next_page_node.hide();
            }
        }
    }
    
    function hideContents(){
    	mainContainer.hide();
    	leftContainer.hide();
    }
    
    function showContents(){
    	mainContainer.show();
    	leftContainer.show();
    }
    
    
    Y.on('history:change', function (e) {
    	  // Ignore changes we make ourselves, since we don't need
    	  // to update the selection state for those. We're only
    	  // interested in outside changes, such as the ones generated
    	  // when the user clicks the browser's back or forward buttons.
    	  if (e.src === Y.HistoryHash.SRC_HASH) {
    	    if (e.changed.url) {
    	    	var qs = e.changed.url.newVal;
    	    	requestFirer.historyTokenChange(qs);
    	    	//e.removed原来有的现在没有了的参数的容器
    	    } else if (e.removed.url) {
    	    	requestFirer.queryStringChange();
    	    }
    	  }
    	});
    
    function sendMoreLikeThis(qs){
    	Y.jsonp(url + qs,handleJSONP);
    }
    
    function sendJsonP(addToHistory){
    	q_node.setStyle("background","#fff url(/aaayjcsxdl/images/ajax-loader.gif) no-repeat center center");
    	if(!qo.q)qo.q="*:*";
    	var qs = Y.QueryString.stringify(qo);
		Y.jsonp(url + qs,{
	        on: {
	            success: handleJSONP,
	            timeout: function(){},
	            failure:function(response){
	            	alert(Y.dump(response));
	            }
	        },
//	        context: MyApp
	        timeout: 3000
			});
		
		if(addToHistory)history.addValue('url',qs || null);
    }
    
    function getDateStr(mills){
    	var date = new Date();
    	var mm = date.getTime();
    	mm = mm + mills;
    	date.setTime(mm);
    	var tt = "{year}-{month}-{date}T{hour}:{minute}:{second}Z";
    	var o = {year:date.getFullYear(),month:date.getMonth()+1,date:date.getDate(),hour:date.getHours(),minute:date.getMinutes(),second:date.getSeconds()};
    	return Y.Lang.sub(tt,o); 
    }
    
    
	Y.on('submit',function(e){
		e.preventDefault();
		qo.setDefaultValues();
		qo.q = getQvalue();
		delete qo.fq;//将fq复位。
		qo["fq"] = ["siteId:" + website.id];
		sendJsonP(true);
	},'#query-form');
	
	
	//global io event
	var GlobalIOEventHandler = {
			  start: function(id, args) {
				  q_node.setStyle("background","#fff url(/aaayjcsxdl/images/ajax-loader.gif) no-repeat center center");
			  },
			  success: function(id, o, args) {
				  q_node.setStyle("background","");
			  },
			  end: function(id, args) {
				  q_node.setStyle("background","");
			  }
		};
	Y.on('io:start', GlobalIOEventHandler.start, Y);
	// Subscribe GlobalEventHandler.complete to event "io:complete".
	Y.on('io:success', GlobalIOEventHandler.complete, Y);
	// Subscribe GlobalEventHandler to event "io:end".
	Y.on('io:end', GlobalIOEventHandler.end, Y);
});
/*
 *    /HOUR
 ... Round to the start of the current hour
 /DAY
    ... Round to the start of the current day
 +2YEARS
    ... Exactly two years in the future from now
 -1DAY
    ... Exactly 1 day prior to now
 /DAY+6MONTHS+3DAYS
    ... 6 months and 3 days in the future from the start of
        the current day
 +6MONTHS+3DAYS/DAY
    ... 6 months and 3 days in the future from now, rounded
        down to nearest day
        
        facet.date.gap=+1DAY,+2DAY,+3DAY,+10DAY
        
        facet.range=price&facet.range=age
        f.price.facet.range.start=0.0&f.age.facet.range.start=10
        
 */