javascriptspinner_Spinner = function() {
	console.log("come in--");
	console.log('sssasasass');

	var pDiv=document.createElement('div');

	pDiv.setAttribute("id","allmap");           //设

	console.log('ssss');
	document.body.appendChild(pDiv); 
	var map = new BMap.Map("allmap");    // 创建Map实例

	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("上海");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true); 
	console.log('ssssss2');

	console.log('end');


};