/**
 * 
 */

YUI({ filter: 'raw' }).use("overlay","dump", function(Y) {

	if(!m3958_floaing_div_config.divid){
		alert('请指定漂浮div的ID值！');
		return;
	}
	if(m3958_floaing_div_config.divid.indexOf('#') == -1){
		m3958_floaing_div_config.divid = '#' + m3958_floaing_div_config.divid;
	}
	
	var overlayNode = Y.one(m3958_floaing_div_config.divid);
	var closeX = Y.Node.create('<a href="#" style="font-size=14px;font-weight:bolder;position:absolute;border:1px solid gray;width:10px;height:16px;top:-17px;display:none;background-color:yellow;">X</a>');
	overlayNode.append(closeX);
	
	overlayNode.setStyle('cursor','pointer');
    var xy = m3958_floaing_div_config.initxy;
    if(xy)overlayNode.setXY(xy);
	var delay = m3958_floaing_div_config.interval; 
	var step = step1 = m3958_floaing_div_config.step;
	var dx = 1,dy = 1;

	function getNextXy(){
		var xy = overlayNode.getXY(); 
		var x = xy[0],y = xy[1];
		if(x <= overlayNode.get('docScrollX'))dx = 1;
		if(y <= overlayNode.get('docScrollY'))dy = 1;
		if(x > overlayNode.get('docScrollX') + overlayNode.get('winWidth') - overlayNode.get('region').width)dx = 0;
		if(y > overlayNode.get('docScrollY') + overlayNode.get('winHeight') - overlayNode.get('region').height)dy = 0;
		dx == 1 ? x = x + step : x = x - step;
		dy == 1 ? y = y + step : y = y - step;
		return [x,y];
	}
	
	var t = Y.later(delay,overlayNode,function(data,data1){
		this.setXY(getNextXy());
	},[1,2],true);
	
	overlayNode.on('mouseover',function(e){
		step = 0;
		closeX.show();
	});
	
	overlayNode.on('mouseout',function(e){
		step = step1;
		closeX.hide();
	});
	
	closeX.on('click',function(e){
		t.cancel();
		overlayNode.remove(true);
	});
	
	
//	Constructor
//	
//	Bezier(p0: Object, p1: Object, c0: Object, c1: Object)
//
//	    		Generates an instance de Bezier. The points can be generated from the method "Bezier.point".
//	    p0
//	        defines the origin point of the curve 
//	    p1
//	        defines the end point of the curve
//	    c0
//	        defines the point which has influence over the p0 argument
//	    c1
//	        defines the point which has influence over the p1 argument			
//
//
//	Class Methods
//
//
//		
//	Bezier.point(x: Integer, y: Integer): Object
//	    Returns an object containing two properties, x and y.
//
//	Methods
//
//	Bezier.getCoordinates(position: Float): Object
//	    		Returns the coordinates of a point given its "position" on the curve.
//	    position
//	        number from 0 up to 1 which specifies the position of the point (example: 0 = beginning, 0.5 = middle, 1 = end)
//	Bezier.plot(callback: Function(point: Object, currentPosition: Float): void): Object
//	    		Draws the curve.
//	    callback
//	        function which will be called for each point that needs to be drawed, receives as first argument the coordinates of the point (contains the x and y properties), the second argument is the current position, which varies from 0 (0%) up to 1 (100%)
//	
//	
//	
	
	//+ Jonas Raoni Soares Silva
	//@ http://jsfromhell.com/math/bezier [rev. #1]

	function Bezier(p0, p1, c0, c1,step){
		var o = this;
		o.x0 = p0.x, o.y0 = p0.y, o.x1 = p1.x, o.y1 = p1.y, o.cx0 = c0.x, o.cy0 = c0.y, o.cx1 = c1.x, o.cy1 = c1.y,o.step = step,currentT = 0;
	};
	with({$: Bezier, o: Bezier.prototype}){
		$.point = function(x, y){
			return {x: x, y: y};
		};
		o.getNextCoordingates = function(){
			if(!this.step)this.step = 0.1;
			this.currentT = this.currentT + step;
			if(currentT > 1){
				return null;
			}else{
				return this.getCoordinates(this.currentT);
			}
		};
		o.getCoordinates = function(t){
			var i = 1 - t, x = t * t, y = i * i, a = x * t, b = 3 * x * i, c = 3 * t * y, d = y * i, o = this;
			return Bezier.point(a * o.x0 + b * o.cx0 + c * o.cx1 + d * o.x1, a * o.y0 + b * o.cy0 + c * o.cy1 + d * o.y1);
		};
		o.plot = function(c){
			var r, x = (x = this.x0 - this.x1) * x, y = (y = this.y0 - this.y1) * y, l = l = Math.ceil(Math.sqrt(x + y)), i = l + 1;
			while(c(this.getCoordinates(r = --i / l), r), i);
		};
	}
	
	//http://jsfromhell.com/dhtml/graphical-plotter
//	var canvas = new Canvas;
//	canvas.pen.color = "#f00";
//	canvas.pixelSize = 5;

	var points = {
	    'p0': Bezier.point(0, 70),
	    'p1': Bezier.point(100, 20),
	    'c0': Bezier.point(50, 20),
	    'c1': Bezier.point(50, 70)
	};

	var bezier = new Bezier(points.p0, points.p1, points.c0, points.c1);
//	var z = bezier.getNextCoordingates();
//	while(z){
//		Y.log(z);
//		z = bezier.getNextCoordingates()
//	}
	
//	for(var i=0;i<100;i++){
//		Y.log(bezier.getCoordinates(i/100.0));
//	}
	
	
//	bezier.plot(function(p){
//	    canvas.pixel(p.x, p.y);
//	});

//	canvas.pen.color = "blue";
//	canvas.line(points.p0.x, points.p0.y, points.c0.x, points.c0.y);
//
//	canvas.pen.color = "green";
//	canvas.line(points.p1.x, points.p1.y, points.c1.x, points.c1.y);

	//]]>

});