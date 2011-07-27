var changeCaptchaImg = function(){
	$('#captchaImg').attr('src','/simpleImg?'+  Math.random());
};

var flagObject = {
		css:null,
		html:null,
		currentDialog:null,
		lastClicked:null,
		lastClickInDisplayArea:false
	};

var InRect = function(x,y){
	var p = $(this).offset();
	if(x<p.left || y<p.top || x > p.left + $(this).width() || y > p.top + $(this).height()){
		return false;
	}else{
		return true;
	}
};
$(document).ready(function(){
	$('#loading').ajaxStart(function(){$(this).show();});
	$('#loading').ajaxStop(function(){$(this).hide();});
	$('.closex').click(function(){$(this).parent().hide();});
	
	$('#parents-area span').live('click',function(event){
		var i = $(this).text().match(/\d+/);
		var lc = flagObject.lastClicked.parents().eq(i);
		noselect();
		flagObject.lastClicked = lc;
		flagObject.lastClicked.wrap('<div class="element-click-wrap"></div>');
		displayParentsPath();
	});
	
	uifunction();
	var da = $('#display-area');
	var htmlCode = $('#htmlCode');
	var noselect = function(){
		$('#display-area .element-click-wrap').each(function(){
			$($(this).children()[0]).unwrap();
		});
		flagObject.lastClicked = null;
//		if(flagObject.lastClicked){
//			if(flagObject.lastClicked.parent().hasClass('element-click-wrap') || flagObject.lastClicked.parent().hasClass('element-hover-wrap'))
//				flagObject.lastClicked.unwrap();
//			flagObject.lastClicked = null;
//		}
		$('#parents-area').html("");
	};
	
	var displayParentsPath = function(){
		var dpreached = false;
		var depth = 0;
		var parentEls = flagObject.lastClicked.parents()
        .map(function () { 
        	if(!dpreached){
        		if($(this).attr("id") == 'display-area')dpreached=true;
        		if($(this).hasClass('element-click-wrap')){
        			return this.tagName + "(clickWrap)" + depth++;
        		}else if($(this).hasClass('element-horver-wrap')){
        			return this.tagName + "(hoverWrap)" + depth++;
        		}else if($(this).attr("id") == 'display-area'){
        			return this.tagName + "(" + $(this).attr("id") + ")" + depth++;
        		}else{
        			return '<span style="cursor:pointer;">' + this.tagName + "(" + $(this).attr("id") + ")" + depth++ + "</span>";
        		}
        	}else{
        		return null;
        	}
            })
        .get().reverse().join("> ");

		$('#parents-area').html(parentEls + "> " + flagObject.lastClicked.get(0).tagName);
	}
	
	$('#display-area').click(function(){noselect();flagObject.lastClicked = undefined;});
	
	$(document).click(function(event){
		flagObject.lastClickInDisplayArea = InRect.call($('#display-area'),event.pageX,event.pageY);
	});
	
	$(window).keyup(function(event){
			if(flagObject.lastClickInDisplayArea){
				if(event.keyCode == 46 && flagObject.lastClicked){
				if(flagObject.lastClicked.parent().hasClass('element-click-wrap') || flagObject.lastClicked.parent().hasClass('element-hover-wrap'))
					flagObject.lastClicked.unwrap();
				flagObject.lastClicked.remove();
				flagObject.lastClicked = undefined;
				htmlCode.val(da.html());
			}
		}
	});
	$('#display-area *').live('mouseover mouseout click',
			function(event){
				if (event.type == 'click') {
					noselect();
					if($(this).hasClass('element-click-wrap') || $(this).hasClass('element-hover-wrap')){//点在wrap上面的话，不需要做什么。
						;
					}else{
						if($(this).parent().hasClass('element-click-wrap')){//已经有框，不需要再框。
							;
						}else{
							//鼠标移入的时候已经有了一个框，再点击的时候。
							if($(this).parent().hasClass('element-hover-wrap')){
								$(this).unwrap();
							}
							$(this).wrap('<div class="element-click-wrap"></div>');
						}
						flagObject.lastClicked = $(this);
						displayParentsPath();
					}
				}else if(event.type == 'mouseover'){
					$(this).css('cursor',"pointer");
					if($(this).hasClass('element-click-wrap') || $(this).hasClass('element-hover-wrap')){//点在wrap上面的话，不需要做什么。
						;
					}else{
						if($(this).parent().hasClass('element-click-wrap') || $(this).parent().hasClass('element-hover-wrap')){//已经有框，不需要再框。
							;
						}else{
							$(this).wrap('<div class="element-hover-wrap"></div>');
						}
						
					}
				}else{
					$(this).css('cursor',"auto");
					if($(this).parent().hasClass('element-hover-wrap')){
							$(this).unwrap();
					}
				}
			});
	$('#confirm-html').click(function(event){
			da.html(htmlCode.val());
	});
	
	$('#confirm-css').click(function(){
		if(!flagObject.lastClicked)return;
		flagObject.lastClicked.css($('#cssAttrName').val(),$('#cssAttrValue').val());
		htmlCode.val(da.html());
	});
	
	$('#confirm-add').click(function(){
		var cv = $('#htmlTagSelect').val();
		var it = resultsHolder.find('html',cv);
		if(it != null)
			htmlCode.val(htmlCode.val() + it.content);
	});
	
	$('#createCss').click(function(event){
								event.preventDefault();
								openDialog('css');
								changeCaptchaImg();
							});
	$('#createHtml').click(function(event){
									event.preventDefault();
									openDialog('html');
									changeCaptchaImg();
							});
});


var openDialog = function(type){
	if('css' == type){
		$('textarea[name="content"]').hide();
		$('label[for="content"]').hide();
		$('label[for="name"]').text("CSS名称");
		$('input[name="contentType"]').val('css');
		flagObject.currentDialog = 'css';
	}else if('html' == type){
		$('textarea[name="content"]').show();
		$('label[for="content"]').show();
		$('label[for="name"]').text("HTML标签名");
		$('input[name="contentType"]').val('html');
		flagObject.currentDialog = 'html';
	}
	$( "#css-dialog-form" ).dialog( "open" );
};


var resultsHolder = {
		cssResponse:null,
		htmlResponse:null,
		cssResults:null,
		htmlResults:null,
		extractResponse:function(results){
			return $.map( results, function( item ) {
			return {
				label: item.name,
				value: item.name
			};
		});},
		find:function(type,val){
			if(!val)return null;
			var res = null;
			if(type == "css"){
				$(this.cssResults).each(function(index,element){
					if(element.name.toUpperCase() == val.toUpperCase()){
						res = element;
						return;
					}
				});
				return res;
			}else if(type == "html"){
				$(this.htmlResults).each(function(index,element){
					if(element.name.toUpperCase() == val.toUpperCase()){
						res = element;
						return;
					}
				});
				return res;
			}
		}
	};

var uifunction = function(){
	
	var selectchanged = function(cssorhtml){
		var select = null;
		var contributorurl = null;
		if(cssorhtml == 'css'){
			select = $("#cssAttrName");
			contributorurl = $('#csscontributorurl');
		}else if(cssorhtml == 'html'){
			select = $("#htmlTagSelect");
			contributorurl = $('#htmlcontributorurl');
		}
		if(select == null)return;
		
		var cv = select.val();
		var it = resultsHolder.find(cssorhtml,cv);
		if(it == null){
//			if(cssorhtml == 'html'){
//				select.val("");
//			}
			;
		}else{
			if(it.contributor){
				var cc = $(it.contributor.split(","));
				if(cc.length == 1){
					contributorurl.html(cv + '由<a href="' + cc.get(0) + '" target="_blank">'+ cc.get(0) +'</a>提供。')
				}else if(cc.length == 2){
					contributorurl.html(cv + '由<a href="' + cc.get(0) + '" target="_blank">'+ cc.get(1) +'</a>提供。')
				}
			}
			if(cssorhtml == 'html'){
//				var htmlCode = $('#htmlCode');
//				htmlCode.val(htmlCode.val() + it.content);
			}else{
				$('#explain-container').show();
				$('#explain').html(it.description);
			}
		}
	};
	
	
	var showResult = function(result){
		var rs = $('#resultshow'); 
		rs.html(result);
		rs.show();
		rs.fadeOut(5000)
	};
	

	
	var jsonpajax = function(data,who){
		$.ajax({
			url: "/jsonp",
			dataType: "jsonp",
			data: data,
			success: function( result ) {
				var response = result.response;
				if(response.status != 0)return;
				if(response.data.length < 1)return;
				var responsedata = response.data;
				var distinct = responsedata[0].contentType;
				if("css" == distinct){
					resultsHolder.cssResults = responsedata;
					resultsHolder.cssResponse = resultsHolder.extractResponse(responsedata);
	         		$( "#cssAttrName" ).autocomplete({
	         			source:resultsHolder.cssResponse,
	         			minLength: 0,
	         			change:function(){selectchanged('css')}
	         		});
	         		$( "#cssAttrName" ).autocomplete('search','');
				}else if("html" == distinct){
					resultsHolder.htmlResults = responsedata;
					resultsHolder.htmlResponse = resultsHolder.extractResponse(responsedata);
	         		$( "#htmlTagSelect" ).autocomplete({
	         			source:resultsHolder.htmlResponse,
	         			minLength: 0,
	         			change:function(){selectchanged('html')}
	         		});
	         		$( "#htmlTagSelect" ).autocomplete('search','');
				};
			}
		});
	}

	         		$( "#cssAttrName" ).focus(
         				function(){
         					if(!flagObject.css){
         						flagObject.css = true;
         						jsonpajax({
    	        					_modelName:'com.m3958.firstgwt.model.HtmlCss',
    	        					contentType:'css',
    	        					maxRows: 12
    	        					},'css');
         						$('#loading').css({left:$(this).offsetLeft,top:$(this).offsetTop});
         						return;
         					}
         					if(!$(this).val()){
         						$( this ).autocomplete('search','');
         					}
         				}
	         		);
	         		
	         		$( "#htmlTagSelect" ).focus(
	         				function(){
	         					if(!flagObject.html){
	         						flagObject.html = true;
	         						jsonpajax({
	    	        					_modelName:'com.m3958.firstgwt.model.HtmlCss',
	    	        					contentType:'html',
	    	        					maxRows: 12
	    	        					},'html');
	         						$('#loading').css({left:$(this).offsetLeft,top:$(this).offsetTop});
	         						return;
	         					}
	         					if(!$(this).val()){
	         						$( this ).autocomplete('search','');
	         					}
	         				}
		         		);
	
	//end of autocompelete
	
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	var name = $( "#name" ),
		contributor = $( "#contributor" ),
		description = $( "#description" ),
		answer = $( "#answer" ),content = $('#content'),
		allFields = $( [] ).add( name ).add( contributor ).add( description ).add(answer).add(content),
		tips = $( ".validateTips" );

	function updateTips( t ) {
		tips
			.text( t )
			.addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}

	function checkLength( o, n, min, max ) {
		if ( o.val().length > max || o.val().length < min ) {
			o.addClass( "ui-state-error" );
			updateTips( n + " 的长度 " + "必须在" +
				min + " 和 " + max + "之间" );
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			updateTips( n );
			return false;
		} else {
			return true;
		}
	}
	
	$("#captchaImg").click(changeCaptchaImg);
	
	$("#css-dialog-form" ).dialog({
		autoOpen: false,
		height: 550,
		width: 550,
		modal: true,
		buttons: {
			"确定": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );
				
				if(flagObject.currentDialog == 'css'){
					bValid = bValid && checkLength( name, "css名称", 3, 30 );
					bValid = bValid && checkRegexp( name, /^[a-z]([a-z\-])+$/i, "CSS名称是字母和-的组合。" );
				}else if(flagObject.currentDialog == 'html'){
					bValid = bValid && checkLength( name, "HTML标签名", 1, 20 );
					bValid = bValid && checkRegexp( name, /^[a-z]+$/i, "HTML名称是字母的组合。" );
					bValid = bValid && checkLength( content,"HTML内容不能为空",5,1000);
				}else{
					bValid = false;
				}
				
				bValid = bValid && checkRegexp( answer,/^([0-9a-zA-Z])+$/i, "请输入正确的验证码");
				// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
//				bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
				var thisThis = $(this);
				if ( bValid ) {
					$('#submitForm').ajaxSubmit({dataType:'json',target:'#divToUpdate',url:'/jqfprocessor',success:function(responseJson, statusText, xhr, $form){
						var res = responseJson.response; 
						if(res.status != 0){
//							if($.isArray(res.data)){
								if($(res.data).length > 0){
									updateTips(res.data[0].errorMessage);
								}else{
									updateTips("發生未知錯誤！");
								}
								changeCaptchaImg();
						}else{
							thisThis.dialog( "close" );
							showResult("谢谢！你的条目已加入数据库，审阅后将会出现。");
						}
					}});
				}
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
};
