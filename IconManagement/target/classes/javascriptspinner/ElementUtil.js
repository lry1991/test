javascriptspinner_ElementUtil = function() {

	var ElementUtil = {};
	window.ElementUtil = ElementUtil;
	var elementId = this.getState().elementId;
	var width;
	var height;
	var top;
	var left;
	console.log('excute');
	this.getExactPosition = function(callBackFunc) {
		console.log('22222222222');
		var element = document.getElementById(elementId);
		console.log(element+'111111111111');
		if (element != null) {
			width = element.clientWidth;
			height = element.clientHeight;
			top = getTop(element);
			left = getLeft(element);
			console.log(width+"-------");
			var func = callBackFunc + "(" + width + "," + height + "," + top
					+ "," + left + ")";
			eval(func);
		}
	}

	// 获取元素的纵坐标
	function getTop(e) {
		var offset = e.offsetTop;
		if (e.offsetParent != null)
			offset += getTop(e.offsetParent);
		return offset;
	}
	// 获取元素的横坐标
	function getLeft(e) {
		var offset = e.offsetLeft;
		if (e.offsetParent != null)
			offset += getLeft(e.offsetParent);
		return offset;
	}
}
