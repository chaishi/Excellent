
var common = {};

(function(page){
	page.getBanner = function(i){
		$.ajax({
			url:'bannerMngr.html',
			type:"get",
			dataType:'html',
			success:function(data){
				$("#banner").html(data);
				page.serActive(i);
			}
		});
	}
	
	page.serActive = function(index){
		$("#barLink li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}
		});
	};
	
	/*
	 * 获取班级列表
	 * @param {number} id 待填充内容的id
	 */
	page.getClasses = function(id){
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
					$(id).html(html);
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