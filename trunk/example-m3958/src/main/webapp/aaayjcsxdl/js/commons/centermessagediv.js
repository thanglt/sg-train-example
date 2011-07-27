/**
 * 
 */

YUI.add('center-message-box-module', function(Y) {
	
	var css = ['.m3958-error-box{'+
						'background-color:	#ffebe8;'+ 	
						'border: 1px solid	#dd3c10;'+
						'position: absolute;'+
						'top: -1000px;'+
						'left:-1000px;'+
						'padding: 5px;'+
						'overflow: hidden;'+
					'}',

		           '.m3958-info-box{'+
						'background-color:	#fff9d7;'+ 	
						'border: 1px solid	#e2c822;'+
						'position: absolute;'+
						'top: -1000px;'+
						'left:-1000px;'+
						'padding: 5px;'+
						'overflow: hidden;'+
					'}',

					
					'.m3958-light-text{'+
						'color: #999999;'+
					'}',
					
					'.m3958-medium-text{'+
						'color: #666666;'+
					'}', 
					
					'.m3958-dark-text{'+
						'color: #333333;'+
					'}' 
	           ];
	var vsheet = Y.StyleSheet(css.join(' '));
	var tpl = '<div class="{box_class} m3958-medium-text">{message}</div>';
	
	
	function createBox(box_class,message,alignto,millis){
    	
		var box = Y.Node.create(Y.Lang.sub(tpl,{message:message,box_class:box_class}));
    	Y.one('body').append(box);
    	
	    box.plug(Y.Plugin.Align);
	    if(alignto){
    		if(Y.Lang.isString(alignto)){
	    		if(alignto.indexOf('#') == -1){
	    			alignto = Y.one('#' + alignto);		    			
	    		}else{
	    			alignto = Y.one(alignto);
	    		}
    		}
	    	box.align.center(alignto);
	    }else{
	    	box.align.center(Y.one('body').get('viewportRegion'));
	    }
	    
	    
	    
	    var rg = box.get('region');
	    Y.log(rg);
	    var trans = function(){
		    box.transition({
		        duration: 1, // seconds
		        easing: 'ease-out', // CSS syntax
		        height: 0,
		        top:rg.top + rg.height/2,
		        width: 0,
		        left: rg.left + rg.width/2,
		        opacity: 0
		    }, function() {
		       box.remove();
		    });
	    };
	    if(!millis)millis = 1500;
	    Y.later(millis,Y,trans,[],false);
    }
    
	
	function MessageBox(){
		this.prompt = function(message,alignto,millis){
			createBox('m3958-info-box',message,alignto,millis);
		};
		
		this.warn = function(message,alignto,millis){
			createBox('m3958-error-box',message,alignto,millis);
		};
	}
	
	Y.centerMessageBox = new MessageBox();

});