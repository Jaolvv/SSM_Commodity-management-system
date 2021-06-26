function getCartCount(path,parameterNameAndtoken){
	var catCount = 0;
	$.ajax({
		type : "POST",
		url : path+"/pc/cart/count"+parameterNameAndtoken,
		dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
		async : false,
		success : function(json) {
			if(json.data==0){
				$("#cartItems").text("");
			}else{
				$("#cartItems").text(json.data);
				catCount = json.data;
			}
		}
	});
	return catCount;
}

function divCart(path,parameterNameAndtoken) {
	var htmlShow = "";
	var sumPrivce = 0;
	$.ajax({
		type : "POST",
		url : path+"/pc/cart/item/view"+parameterNameAndtoken,
		dataType : "json", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）  
		async : false,
		success : function(json) {
			if (json.data != null) {
				var idx = 2;
				if(json.data.orderItemList.length<2){
					idx = json.data.orderItemList.length;
				}
				for (var i = 0; i < idx; i++) {
					htmlShow += '<a href="'+path+'/pc/product/'+json.data.orderItemList[i].productList[0].id+'">'
							+ '<img src="'+path+json.data.orderItemList[i].productList[0].defaultImg+'" width="45"'+'height="45" alt="" />'
							+ '<h6>' + '<span>'
							+ json.data.orderItemList[i].quantity + '</span> '
							+ json.data.orderItemList[i].productList[0].name
							+ '</h6> <small>' + (json.data.orderItemList[i].price/100).toFixed(2)
							+ '</small>' + '</a>'
				}
				$("#divCart").html(htmlShow);
				$("#sumprice").html("<strong>TOTAL:</strong>" + "￥" + (json.data.price/100).toFixed(2));
			} else {
				$("#cartnoitems").show();
			}
		}
	});
}
