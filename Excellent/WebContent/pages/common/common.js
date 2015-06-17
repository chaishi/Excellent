
var common = {};

(function(page){
	
	page.serActive = function(index){
		$("#barLink li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}
		});
	};
	
	//为侧边栏添加点击事件
	//@param {function} fun 点击侧边栏时，需要执行的函数，即显示相应的内容
	page.addCickToNav = function(fun){
		$("#navLeft").delegate('li','click',function(){
			var index = $(this).index();
			page.setActiveNav(index);
			fun(index);
		});
	}
	
	//为侧边栏设置active
	page.setActiveNav = function(index){
		$("#navLeft li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}else{
				$(this).removeClass('active');
			}
		});
	}
	/*
	 * 获取班级列表
	 * @param {number} id 待填充内容的id
	 */
	page.getClasses = function(){
		var ids = arguments[0];
		var fun = arguments[1];
		$.ajax({
			url:"/Excellent/pages/json/classNameList.json",
			type:"get",
			dataType:"json",
			success:function(data){
				if(data.success === true){
					var classList = data.result;
					var html = "";
					for(var i = 0, len = classList.length; i < len; i++){
						html += '<option value = "'+classList[i].classId+'">'+classList[i].className+'</option>';
					}
					for(var j = 0, n = ids.length; j < n; j++){
						$(ids[j]).html(html);
					}
					if(typeof fun === "function"){
						fun(classList[0].classId);
					}
				}else{
					console.log("班级列表获取失败!");
				}
			},
			error:function(){
				console.log("班级列表请求失败！");
			}
		});
	}
})(common);

function pageToNew(url,id){
	window.open(url + "#" + id);
}