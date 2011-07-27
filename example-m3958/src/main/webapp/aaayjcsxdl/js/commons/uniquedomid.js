/**
 * 
 */

YUI.add('unique-dom-id-module', function(Y) {
	
    var UniqueDomId = function(prefix){
    	var n = 0;
    	var instanceId = UniqueDomId.instanceId++;
    	this.next = function(){
    		if(!prefix)prefix = "m3958";
    		return prefix + '-' +  instanceId + '-' + n++;
    	};
    };

	UniqueDomId.instanceId = 0;
	
	Y.UniqueDomId = UniqueDomId;
    
});