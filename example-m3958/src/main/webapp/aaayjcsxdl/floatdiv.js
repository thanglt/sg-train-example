///////////////////////////////////////////////////////\\
///////////////////////////////////////////////////////\\
//***************************************************\\\\
//                   JQuery Float DIV      V1.000     \\\\
//                                                    \\\\
//                www.m3958.com                       \\\\
//                jianglibo@gmail.com                 \\\\
//                Auther: jianglibo                   \\\\
//***************************************************\\\\
///////////////////////////////////////////////////////\\
///////////////////////////////////////////////////////\\

(function($){
	$.fn.floatdiv = function(options){
		
		var floatdiv = $(this);
		
		var Config = {
					startleft		:0,
					starttop:		0,
                     interval       : 100,
                     type           :null,
                     step			:1,
                     closeClass     :"closeClass"
                };
	    if(options)
	    {
			$.extend(Config, options);
		};
		
		function FloatDivMath(jfloatdiv){
			this.floatDiv = jfloatdiv;
			this.incrementl = true;
			this.increamentt = true;
			this.left = Config.startleft;
			this.top = Config.starttop;
			
			this.getLrtb = function(){
				var d = $(document);
				var w = $(window);
				var leftest = d.scrollLeft();
				var rightest = d.scrollLeft() + w.width() - this.floatDiv.width();
				var topest = d.scrollTop();
				var bottomest = d.scrollTop() + w.height() - this.floatDiv.height();
				return {left:leftest,right:rightest,top:topest,bottom:bottomest};
			};
			
			this.getNextOffset = function(){
				this.left = this.left + Config.step*(this.incrementl?1:-1);
				this.top = this.top + Config.step*(this.increamentt?1:-1);
				var lrtb = this.getLrtb();
				if (this.left < lrtb.left) { this.incrementl = true; this.left = lrtb.left;} 
				if (this.left > lrtb.right){ this.incrementl = false; this.left = lrtb.right;} 
				if (this.top < lrtb.top) { this.increamentt = true; this.top = lrtb.top;} 
			    if (this.top > lrtb.bottom) { this.increamentt = false; this.top = lrtb.bottom;}
			    return {left:this.left,top:this.top};
			};
		}
		
		var intervel;
		
		var closeBt = floatdiv.find("." + Config.closeClass);
		
		function start(){
			var floatingMath;
			if(!Config.type){
				floatingMath = new FloatDivMath(floatdiv);
			}
			if(floatingMath){
				floatdiv.css({position:'absolute'})
				closeBt.css({cursor:'pointer'})
				closeBt.click(function(){
					floatdiv.hide();
					if(intervel)
						clearInterval(intervel);
				});
				
				closeBt.hide();
				
				floatdiv.mouseover(function(){
					if(intervel)
						clearInterval(intervel);
					closeBt.show();
				});
				floatdiv.mouseout(function(){
					if(floatdiv.is(":visible")){
						intervel = setInterval(function(){floatdiv.offset(floatingMath.getNextOffset());}, Config.interval);
					}
					closeBt.hide();
				});
				intervel = setInterval(function(){floatdiv.offset(floatingMath.getNextOffset());}, Config.interval);
			}
		}
		
		start();
	}
})(jQuery);