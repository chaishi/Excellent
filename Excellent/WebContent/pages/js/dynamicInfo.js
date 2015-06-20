$(function(){
	common.serActive(1,1);
	dinamicInfo.getDynamicInfo();
});

var dinamicInfo = {};

(function(page){
	
	page.getDynamicInfo = function(){
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
					$("#atyTitle").html(dym.title); //动态标题
					$("#className").html(dym.classNum); //班级
					$("#browsTimes").html(dym.browses); //浏览次数
					$("#happenTime").html(dym.happen_time); //动态时间
					$("#dynamicContent").html(dym.content); //动态内容
				}else{
					alert("获取班级动态详情失败！");
				}
			}
		);
	};
	
})(dinamicInfo);