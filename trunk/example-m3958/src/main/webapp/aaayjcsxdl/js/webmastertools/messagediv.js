/**
 * 
 */

YUI.add('message-box-module', function(Y) {
	
	var css = ['div.m3958-webmastertool-warn,div.m3958-webmastertool-info {'+
						'text-align: center;'+
						'padding: 10px;'+
					'}',
					
					'div.m3958-webmastertool-info {'+
						'background-color: #32BC57; '+
						'border: 2px solid #06922C;'+
					'}',
					
					'div.m3958-webmastertool-warn{'+
						'background-color: #E94891;'+
						'border: 2px solid #FA0473;'+
					'}',
					
					'div.m3958-webmastertool-info  h2,div.m3958-webmastertool-warn h2{'+
						'font-size: 24px;'+
						'font-weight: bolder;'+
						'color: #FFF;'+
						'padding: 0 0 0 0;'+
						'margin: 0 0 0 0;'+
					'}'
	           ];
	var vsheet = Y.StyleSheet(css.join(' '));
	var tpl = '<div style="position: absolute;display: none;top: 0;-moz-opacity:0.85;opacity: 0.85;filter: alpha(opacity=85);"><h2>{message}</h2></div>';
	
	
	function createBox(box_class,message,millis){
		var t = Y.Lang.sub(tpl,{message:message});
		var n = Y.Node.create(t);
		n.plug(Y.Plugin.Align)
		var vr = n.get('viewportRegion');
		n.setStyle('width',vr.width - 30 + 'px');
		n.addClass(box_class);
		Y.one('body').append(n);
		n.show();
		n.align.to(n.get('viewportRegion'),'tc','tc',true);
		
		var close = Y.Node.create('<a href="#" style="font-weight:bolder;border:1px solid gray;position:absolute;top:0;right:0;background-color: yellow;color: blue;">X</a>');
		close.on('click',function(e){
			e.preventDefault();
			to.cancel();
			close.get('parentNode').remove(true);
		});
		n.append(close);
		var m = millis;
		if(!m)m = 1500;
		var to = Y.later(m,Y,function(){
			n.remove(true);
		},[],false);
	}
	
	function MessageBox(){
		this.prompt = function(message,millis){
			createBox('m3958-webmastertool-info',message,millis);
		};
		
		this.warn = function(message,millis){
			createBox('m3958-webmastertool-warn',message,millis);
		};
	}
	Y.messageBox = new MessageBox();

});