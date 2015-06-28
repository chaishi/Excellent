$(function(){
	common.serActive(1,1);
	recruitInfo.getrecruitInfo();
});

var recruitInfo = {};

(function(page){
	
	page.getrecruitInfo = function(){
		var dynamicId = location.hash.split("#");
		//console.log(dynamicId[1]);
		$.getJSON(
			"/Excellent/news/showNewsDetail",
			{
				atyId:dynamicId[1]
			},
			function(data){
				if(data.success === true){
					var dym = data.result.details;
					$("#atyTitle").html(dym.title); //招新标题
					$("#browsTimes").html(dym.browses); //浏览次数
					$("#happenTime").html(dym.happen_time); //招新时间
					$("#recruitContent").html(dym.content); //招新内容
				}else{
					alert("获取班级动态详情失败！");
				}
			}
		);
	};
	
})(recruitInfo);