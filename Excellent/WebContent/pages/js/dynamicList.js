$(function(){
	common.serActive(1,1);
	dynamicList.getDynamicList();
});

var dynamicList = {};

(function(page){
	//获取班级动态列表
	page.getDynamicList = function(){
		$.getJSON(
			"/Excellent/news/showClassNewsList",
			{
				rowNum:15,
				nowPage:1
			},
			function(data){
				if(data.success === true){
					var dymList = data.result.details;
					var html = "";
					var url = "/Excellent/pages/dynamicInfo.html";
					for(var i = 0,len = dymList.length; i < len; i++){
						html += '<tr><td class = "newsHover" onclick = "pageToNew(\''+url+'\','+dymList[i].id+')">'+dymList[i].title+'</td><td>'+dymList[i].happen_time+'</td></tr>';
					}
					$("#dynamicsTbl").html(html);
				}else{
					alert("获取班级动态列表失败，请刷新重试！");
				}
			}
		);
	};
	
})(dynamicList);