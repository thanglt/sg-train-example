/**
 * 
 */



	
/*filter:debug,console*/
YUI({filter: 'raw'}).use("overlay","dd-drag","node","uploader","selector-css3","json","event-delegate","event-valuechange","io-base","tabview", "datasource-io", "datasource-jsonschema", "datatable-base", "datatable-datasource","dump",function(Y) {
		
	var _tz = window.jzTimezoneDetector.determine_timezone().timezone.olson_tz;
	var closeBtClicked = false;
	
	
	var overlay = createOverlay();
    
	var tabview = new Y.TabView({srcNode:'#assetTab'});
	tabview.render();
    
    var assetFormContainer = new AssetFormContainer();
    
    var allAttachmentsTable = createAtTable();

    function createOverlay(){
        /* 产生overlay*/
        var ol = new Y.Overlay({
        	srcNode:"#overlay",
            width:"600px",
            height:"450px",
            visible:false,
            zIndex:19811212,
            centered:true
        });
        var dd = new Y.DD.Drag({node:'#overlay'});
        ol.render('body');
        return ol;
    }
    
    function createAtTable(){
        var at = Y.one('#allAttachmentsTable').one('tbody');
        Y.mix(at,getAtMix());
        Y.log(window.opener.firstgwtns.getAttachmentJson(),'info');
        at.initContainner(window.opener.firstgwtns.getAttachmentJson());
        at.delegate('click',function(e){
        	Y.log(this.get('id'),"info","asset name clicked!")
        	var asset = at.findAsset(this.get('id'));
        	Y.log(asset,"info","clicked asset");
        	assetFormContainer.showAssetDetail(asset);
        	e.preventDefault();
        	Y.log(e.currentTarget.get('title'),"info","asset name clicked!");
        },"a");

        at.delegate('click',function(e){
        	at.removeRow(this.get('id'));
        	e.preventDefault();
        },".removeIcon");

        at.delegate('change',function(e){
        	Y.log(this.get('id'),"info","checkbox icon clicked!")
        	at.typeChanged(this);
        },"input[type='checkbox']");
        
        at.all("input[type='text']").on('valueChange',function(e){
        	Y.log(this.get('id'),"info","text value changed!")
        	e.preventDefault();
        });
        return at;
    }
    
    
    function showOverlay(e){
    	var im = Y.Node.create(KE.selectedHtml('content1'));
    	if(im && im.get('tagName') == 'IMG'){
    		var imsrc = im.get('src');
    		var burl = window.location.href.substring(0,window.location.href.indexOf("/",10));
    		Y.log(burl,'info');
    		if(imsrc.indexOf(burl) != -1){
    			imsrc = imsrc.substring(burl.length);
    		}
    		Y.one('#assetUrl').set('value',imsrc);
    		if(im.get('alt'))
    			Y.one('#imgdesc').set('value',im.get('alt'));
    		if(im.get('width')){
    			Y.one('#myImgWidth').set('value',im.get('width'));
    		}
    		
    		if(im.get('height')){
    			Y.one('#myImgHeight').set('value',im.get('height'));
    		}
    		assetFormContainer.resetView(true);
    	}else{
    		assetFormContainer.resetView(false);
    	}
    	overlay.show();
    };
    
	KE.mmmf = function(id){
		KE.sync(id);
		window.opener.firstgwtns.setAreaHtml(KE.html(id));
		closeBtClicked = true;
		window.close();
	};
	
	
	KE.lang['my_image'] = "附件管理";
	KE.plugin['my_image'] = {
		click : function(id) {showOverlay();},
		check : function(id) {},
		exec : function(id) {},
		insert : function(id, url, title, width, height, border) {}
	};

	KE.init({
		id : 'content1',
		width:'100%',
		height:'600px',
//		cssPath : '/aaayjcsxdl/ke/examples/index.css',
		imageUploadJson:'/fileUploadHandler/?siteId=' + window.opener.firstgwtns.getCurrentWebSite().id + '&WANTED_RESPONSE=KINDEDITORJSON',
		cssPath:window.opener.firstgwtns.getCurrentWebSite().richEditorCssPath,
		items : ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
		      			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		      			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		      			'superscript', '|', 'selectall', '-',
		      			'title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold',
		      			'italic', 'underline', 'strikethrough', 'removeformat', '|', /*'image',*/
		      			'flash', 'media', 'advtable', 'hr', 'emoticons', 'link', 'unlink', '|', 'about','|','my_image'],
		afterCreate : function(id) {
			KE.util.focus(id);
			Y.on('click',function(e){
				KE.mmmf(id);
			},'#kebt');
			
			KE.event.ctrl(document, 13, function() {
				KE.mmmf(id);
			});
			KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
				KE.mmmf(id);
			});
		}
	});
	
	Y.on('domready',function(){
		KE.create('content1');
		KE.html('content1',window.opener.firstgwtns.getAreaHtml());
	});
	
//	KE.event.ready(function() {
//		KE.create('content1');
//		KE.html('content1',window.opener.firstgwtns.getAreaHtml());
//	});
	

    
    
    /* uploader --*/
    (function(){
    	var uploader = new Y.Uploader({boundingBox:"#selectButton", 
			   		buttonSkin:"/aaayjcsxdl/images/uploadbrowse.png",
   					transparent: false,
				    swfURL : '/yui/3.3.0/build/uploader/assets/uploader.swf'
  				});
    	
    	uploader.on("uploaderReady", setupUploader);
    	uploader.on("fileselect", fileSelect);
    	uploader.on("uploadprogress", updateProgress);
    	uploader.on("uploadcomplete", uploadComplete);
    	uploader.on("uploadcompletedata",uploadCompleteData);
 		Y.one("#uploadButtonLink").on("click", uploadFile);
   	 
    	function setupUploader (event) {
    		uploader.set("multiFiles", false);
    		uploader.set("log", true);
//    		var fileFilters = new Array({description:"Images", extensions:"*.jpg;*.png;*.gif"},
//    		                   {description:"Videos", extensions:"*.avi;*.mov;*.mpg"}); 
//    	    uploader.set("fileFilters", fileFilters);
    	}
    	 
    	 
    	function fileSelect (event) {
    		var fileData = event.fileList;	
    	 
    		for (var key in fileData) {
    			var output = "上传文件: " + fileData[key].name;
    			Y.one("#filename").setContent(output);
    		}
    	}
    	 
    	function updateProgress (event) {
    		Y.one("#percent").setContent("上床进度： " + Math.round((100 * event.bytesLoaded / event.bytesTotal)) + "%");
    	}
    	 
    	function uploadComplete (event) {
    		Y.one("#percent").setContent("上传结束!");
    	}
    	
//    	{"STATUS":0,"DATA":[{"createdAt":"2011-06-03T19:04:25","creatorIds":",","description":"","extension":".jpg","fileId":"17953","filePath":"9/5/3/17953.jpg","fileSize":69363,"id":17953,"mimeType":"application/octet-stream","oname":"abc.jpg","originName":"abc.jpg","saveTo":"FILE_SYSTEM","siteId":9568,"thumbnail":"/asset/17953.48x48.jpg","url":"/asset/17953.jpg"}],"OTHERS":{"MSG":"上传成功！"}}
    	function uploadCompleteData (event) {
    		var rdata = Y.JSON.parse(event.data);
    		if(rdata.STATUS == 0){
    			var data = rdata.DATA[0];
    			assetFormContainer.showAssetDetail(data);
    			data.isAttachment = true;
    			allAttachmentsTable.addAsset(data,true);
    		}else{
    			alert("上传出错！");
    			return;
    		}
    	}
    	 
    	function uploadFile (event) {
    		uploader.uploadAll("/fileUploadHandler/?siteId=" + window.opener.firstgwtns.getCurrentWebSite().id + "&&WANTED_RESPONSE=JSON","POST");
    	}
    })();
    
    //直接关闭提示
    window.onbeforeunload = function(e) {
    	if(closeBtClicked){
    		return;
    	}else{
    		var msg = "通过关闭窗口退出的话，为保存的内容将丢失！";
    		if(e){
    			e.returnValue = msg; // most browsers
    		}
    		return msg; // safari
    	}
    };
    
  //资产列表
    (function(){
    		var myDataSource = new Y.DataSource.IO({source:"/smartcfud?&_modelName=com.m3958.firstgwt.model.Asset&_operationType=fetch&_sortBy=-createdAt&_textMatchStyle=substring&_tz=" + _tz});
    		
    		myDataSource.plug(Y.Plugin.DataSourceJSONSchema, {schema: {
    			metaFields:{status:"response.status",startRow:"response.startRow",endRow:"response.endRow",totalRows:"response.totalRows"},
    		    resultListLocator: "response.data",
    		    resultFields: ["originName", "fileSize","id","img","url","thumbnail","filePath"]
    		}});
    		
    		var cols = [{key:"originName",label:"文件名"}, {key:"fileSize", label:"大小"},{key:"id",label:"选择",formatter:'<a href="#" id="aid-{value}">选择</a>'}];
    		var dt = new Y.DataTable.Base({columnset:cols, summary:"Pizza places near 98089", caption:"站点附件浏览"});
    		dt.render("#assetDataTable");
    		
    		var assets;
    		
    		function findAssetById(aid){
    			for(var i=0;i<assets.length;i++){
    				if(aid == assets[i].id){
    					return assets[i];
    				}
    			}
    			return null;
    		}
    		
    		myDataSource.sendRequest({request:"&_startRow=0&_endRow=8",
    									callback:{
    											success:function(e){
    												assets = e.response.results;
    												dt.set("recordset", new Y.Recordset({records: assets}));
    											}
    									}
    								});
    								
    		
    		Y.delegate('click',function(e){
    				var asset = findAssetById(e.currentTarget.get("id").substring(4));
    				if(asset){
    					tabview.selectChild(0);
    					assetFormContainer.showAssetDetail(asset);
    					asset.isAttachment = true;
    					allAttachmentsTable.addAsset(asset,true);
    				}
    				
    			},'#assetRepository',"a");
    })();
    
    
    /* 远程附件 */
    Y.one('#remoteAssetBt').on('click',function(e){
    	this.set('disabled','disabled');
    	var loading = Y.one('#loading1');
    	loading.show();
    	var assetUrl = Y.one('#assetUrl');
    	var av = assetUrl.get('value');
    	if(!av || av.length < 5 || av.indexOf("/asset/") != -1){
    		alert("提供的路径不适合抓取！");
    		return;
    	}
		var tH = {
			success: function(id, o, args) {
	    		var res = Y.JSON.parse(o.responseText).response;
	    		if(res.status == 0){
	    			var data = res.data[0];
	    			assetFormContainer.showAssetDetail(data);
	    			data.isAttachment = true;
	    			allAttachmentsTable.addAsset(data,true);
	    		}else{
	    			alert("上传出错！");
	    			return;
	    		}
			 },
			failure: function(id, o, args) {
					   alert(o.statusText);
					 },

			end: function(id, args) {
					loading.hide();
					Y.one('#remoteAssetBt').set('disabled',"");
			}
		};

    	var cfg = {
    			method: 'GET',
    			data: '_modelName=com.m3958.firstgwt.model.Asset&_operationType=custom&_relationModelId=-1&_subOperationType=REMOTE_ASSET&_tz=' + _tz + "&siteId=" + window.opener.firstgwtns.getCurrentWebSite().id + "&url=" + assetUrl.get('value'),
    		on: {
    			success: tH.success,
    			failure: tH.failure,
    			end: tH.end
    		},
    		context: tH,
    		arguments: {
    				   start: 'foo',
    				   complete: 'bar',
    				   success: 'baz',
    				   failure: 'Oh no!',
    				   end: 'boo'
    				   }
    	};
    	Y.io('/smartcfud', cfg);
    });
    
    /*- 附件容器- */
    function getAtMix(){
	    var AttachmentsContainerMix = (function(){
	    	var allattachments = new Array();
	    	
	    	function getAttachJoStr(){
	    		var ajo = {contentImgs:[],attachments:[]};
	    		Y.each(allattachments,function(a){
	    			if(a.isTitleImg){
	    				ajo.titleImg = a;
	    			}else if(a.isContentImg){
	    				ajo.contentImgs.push(a);
	    			}else{
	    				ajo.attachments.push(a);
	    			}
	    		});
	    		return Y.JSON.stringify(ajo);
	    	}
	    	return	{
	    		removeAssetFromList:function(assetId){
	    			var aid = assetId.replace(/\D/gi,"");
	    			if(!aid){
	    				return null;
	    			}else{
	    				var newa = new Array();
	    				var find = false;
	    				var j=0;
	    				for(var i = 0;i<allattachments.length;i++){
	    					if(allattachments[i].id == aid){
	    						find = true;
	    					}else{
	    						newa.push(allattachments[j++]);
	    					}
	    				}
	    				if(find){
	    					allattachments = newa;
	    				}
	    			}
	    		},
	    		findAsset:function(assetId){
	    	    	for(var i=0;i<allattachments.length;i++){
	    	    		if(allattachments[i].id == assetId){
	    	    			return allattachments[i];
	    	    		}
	    	    	}
	    	    	return null;
	    		},
	    		initContainner:function(attachesJson){
	    				if(attachesJson.titleImg){
	    					var asset = attachesJson.titleImg;
							if(!AttachmentsContainerMix.findAsset(asset.id)){
								asset.isTitleImg = true;
								allattachments.push(asset);
							}else{
								AttachmentsContainerMix.findAsset(asset.id).isTitleImg = true;
							}
	    				}
	    				
	    				Y.each(attachesJson.contentImgs,function(asset){
							if(!AttachmentsContainerMix.findAsset(asset.id)){
								asset.isContentImg = true;
								allattachments.push(asset);
							}else{
								AttachmentsContainerMix.findAsset(asset.id).isContentImg = true;
							}
	    				});
	    				
	    				Y.each(attachesJson.attachments,function(asset){
							if(!AttachmentsContainerMix.findAsset(asset.id)){
								asset.isAttachment = true;
								allattachments.push(asset);
							}else{
								AttachmentsContainerMix.findAsset(asset.id).isAttachment = true;
							}
	    				});
		    		    
		    		    Y.each(allattachments,function(value,index,array){
		    		    	this.addAsset(value,false);
		    		    },this);
	    		},
	    		
	    		/*    
	    		<tr>
	    			<td>name</td>
	    			<td><input type="text" size="10"/></td>
	    			<td><input type="checkbox"/></td>
	    			<td><input type="checkbox"/></td>
	    			<td><input type="checkbox"/></td>
	    			<td><img src="/images/icons/16/close.png" /></td>
	    		</tr>
	    		*/
	    		createTR:function(asset){
	    			var tr = '<tr><td><a href="#" title="' + asset.originName + '" id="' + asset.id + '">' + asset.originName + '</a></td>';
	    			tr += '<td><input type="text" size="10" value="' + asset.description + '"/></td>';
	    			
	    			if(asset.isTitleImg){
	    				tr = tr + '<td><input type="checkbox" checked="checked" class="titleImgChk" id="titleImgChk_'+ asset.id +'"/></td>';
	    			}else{
	    				tr = tr + '<td><input type="checkbox" class="titleImgChk"  id="titleImgChk_'+ asset.id  +'"/></td>';
	    			}
	    			
	    			if(asset.isContentImg){
	    				tr = tr + '<td><input type="checkbox" class="contentImgChk"  checked="checked" id="contentImgChk_'+ asset.id +'"/></td>';
	    			}else{
	    				tr = tr + '<td><input type="checkbox" class="contentImgChk"   id="contentImgChk_'+ asset.id +'"/></td>';
	    			}
	    		
	    			if(asset.isAttachment){
	    				tr = tr + '<td><input type="checkbox" class="attachmentImgChk" checked="checked" id="attachmentChk_'+ asset.id +'"/></td>';
	    			}else{
	    				tr = tr + '<td><input type="checkbox" class="attachmentImgChk" id="attachmentChk_' + asset.id +'"/></td>';
	    			}
	    			tr = tr + '<td><img src="/images/icons/16/close.png" class="removeIcon" id="removeIcon_' + asset.id + '"/></td></tr>';
	    			return tr;
	    		},
	    		addAsset:function(asset,add2list){
	    			asset.isAttachment = true;
	    			this.appendChild(this.createTR(asset));
	    			if(add2list){
	    				Y.log(getAttachJoStr(),'info');
	    				allattachments.push(asset);
	    				window.opener.firstgwtns.setAttaches(getAttachJoStr());
	    			}
	    		},
	    		findTR:function(cnid){
	    			var n = this.one('#' + cnid);
	    			return n.ancestor('tr');
	    		},
	    		removeRow:function(cnid){
	    			this.removeChild(this.findTR(cnid));
	    			this.removeAssetFromList(cnid);
	    			window.opener.firstgwtns.setAttaches(getAttachJoStr());
	    		},
	    		typeChanged:function(checkbox){
	    			if(checkbox.hasClass('titleImgChk')){
	    				this.changeTitleImg(checkbox.get('id'),checkbox.get('checked'));
	    				if(checkbox.get('checked')){
	    					var nl = this.all('.titleImgChk');
	    					Y.each(nl,function(value){
	    						value.set('checked',false);
	    					});
	    					checkbox.set('checked',true);
	    				}
	    			}else{
	    				this.changeContentAtta(checkbox.get('id'),checkbox.get('checked'));
	    			}
	    			window.opener.firstgwtns.setAttaches(getAttachJoStr());
	    		},
	    		changeTitleImg:function(cnid,checked){
	    			var aid = cnid.replace(/\D/gi,"");
	    			Y.each(allattachments,function(value,index,array){
	    				if(value.id == aid && checked){
	    					value.isTitleImg = true;
	    				}else{
	    					value.isTitleImg = false;
	    				}
	    			});
	    		},
	    		changeContentAtta:function(cnid,checked){
	    			var aid = cnid.replace(/\D/gi,"");
	    			Y.each(allattachments,function(value,index,array){
	    				if(value.id == aid){
	    					value.isTitleImg = checked;
	    				}
	    			});
	    		}
	    	}
	    })();
	    return AttachmentsContainerMix;
    }
    
    //本地全局函数
    function isImg(url){
    	if(url.match(/\.(gif|jpg|jpeg|png)$/i)){
    		return true;
    	}else{
    		return false;
    	}
    }

    //资产表单form
    function AssetFormContainer(){
    	var maxSize;
        this.possibleSize = Y.one('#possibleSize');
        this.imgdesc = Y.one('#imgdesc');
        this.myImgWidth = Y.one('#myImgWidth');
        this.myImgHeight = Y.one('#myImgHeight');
        this.imgalign = Y.one('#imgalign');
        this.assetUrl = Y.one('#assetUrl');
        this.previewSlot = Y.one('#imgPreview');
        if(window.opener.firstgwtns)
        	this.imgsizesStr = window.opener.firstgwtns.getCurrentWebSite().imgSizes;
        this.imgSizes = this.imgsizesStr.split(",");
        
        Y.on('click',function(e,insertImg){
        	e.halt();
        	this.insert2Ke(insertImg);
        },'#insertImgBt',this,true);
        
        Y.on('click',function(e,insertImg){
        	e.halt();
        	this.insert2Ke(insertImg);
        },'#insertLinkBt',this,false);

        
        Y.on('click',Y.bind(overlay.hide,overlay),'#clswinBt');
        
	    Y.each(this.imgSizes,function(value,index,array){
  	    		if(value){
  	    			if(index == 0)maxSize = value;
  	    			this.append('<option>'+ value +'</option>');
  	    		};
  	    	},this.possibleSize);
  		    
	    this.possibleSize.after('change',function(e){
  	    		this.appendSizePostfix();
  	    },this);
   		
		this.previewImg = function(asset){
			this.previewSlot.set('src',asset.thumbnail);
		};
    		
   	    this.resetView = function(isimg){
   	    	if(isimg){
   	   	    	this.possibleSize.set('disabled','disabled');
   	    	}else{
   	   	    	this.imgdesc.set('value',"");
   	   	    	this.myImgWidth.set('value',"");
   	   	    	this.myImgHeight.set('value',"");
   	   	    	this.assetUrl.set('value',"");
   	   	    	this.possibleSize.set('disabled','disabled');
   	    	}
   	    };
   	    
   	    this.showAssetDetail = function(asset){
   	    	this.assetUrl.set('value',asset.url);
   	    	this.imgdesc.set('value',asset.originName);
			if(asset.img){
				this.possibleSize.set("disabled","");
				this.possibleSize.set('value',maxSize);
				this.myImgWidth.set("disabled","");
				this.myImgHeight.set("disabled","");
				this.appendSizePostfix();
				this.previewImg(asset);
			}else{
				this.possibleSize.set("disabled","disabled");
				this.myImgWidth.set("disabled","disabled");
				this.myImgHeight.set("disabled","disabled");
			}
   	    };
   	    
   	    
   	    this.appendSizePostfix = function(){
   	    	var s = this.assetUrl.get('value');
   	    	s = s.replace(/\.\d+x\d+\./i,".");
   	    	var i = s.lastIndexOf(".");
   	    	var v = this.possibleSize.get('value');
   	    	if("原图" == v){
   	    		v = "";
   	    	}else{
   	    		v = "." + v;
   	    	}
   	    	s = s.substring(0,i) + v + s.substring(i);
   	    	this.assetUrl.set('value',s);
   	    };
   	    
   	    this.getImgHtml = function(){
   	    	var value = Y.one('[name=imgalign]:checked').get('value'),
   	    		l2d = Y.one('#link2origin').get('checked'),
   	    		src = this.assetUrl.get('value'),
   	    		alt = this.imgdesc.get('value'),
   	    		title = this.imgdesc.get('value'),
   	    		href,
   	    		html;
        	var so = {
            		src:src,
            		alt:alt,
            		title:title
            	};
        	if(this.myImgWidth.get('value')){
        		so.imgwidth = 'width="' + this.myImgWidth.get('value') + '"';
        	}else{
        		so.imgwidth = "";
        	}
        	if(this.myImgHeight.get('value')){
        		so.imgheight = 'height="' + this.myImgHeight.get('value') + '"';
        	}else{
        		so.imgheight = "";
        	}
    		
   	    	if(l2d){
   	    		html = '<a href="{href}" target="_blank"><img src="{src}" alt="{alt}" {style} title="{title}"  {imgwidth} {imgheight}/></a>';
   	    		so.href = src.replace(/\.\d+x\d+\./i,".");
   	    	}else{
   	    		html = '<img src="{src}" alt="{alt}" {style} title="{title}"   {imgwidth} {imgheight}/>';
   	    	}
        	if('left' == value){
        		so.style = 'style="float:left;border:0;"';
        	}else if('right' == value){
        		so.style = 'style="float:right;border:0;"';
        	}else{
        		so.style = 'style="border:0;"';
        	}
        	Y.log(Y.Lang.sub(html,so));
        	return Y.Lang.sub(html,so);
   	    };
   	    
   	    this.getLinkHtml = function(){
	    		html = '<a href="{href}" title="{title}">{name}</a>';
	    		var so = {
	    			href:this.assetUrl.get('value'),
	    			title:this.imgdesc.get('value'),
	    			name:this.imgdesc.get('value')
	    		};
   	        	return Y.Lang.sub(html,so);
   	    };
   	    
   	    this.insert2Ke = function(insertImg){
   	    	if(!this.assetUrl.get('value') || this.assetUrl.get('value').length < 5){
   	    		alert("请输入附件地址！");
   	    		return;
   	    	}
   	    	
   	    	var html;
   	    	if(insertImg && isImg(this.assetUrl.get('value'))){
   	    		html = this.getImgHtml();
   	    	}else{
				html = this.getLinkHtml();
   	    	}
   	    	KE.util.insertHtml('content1',html);
   	    };
    }
});
