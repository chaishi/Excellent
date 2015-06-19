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
					$("#atyTitle").val(dym.title); //动态标题
					$("#className").val(dym.classNum); //班级
					$("#browsTimes").val(dym.browses); //浏览次数
					$("#happenTime").val(dym.happen_time); //动态时间
					$("#dynamicContent").html(dym.content); //动态内容
				}else{
					alert("获取班级动态详情失败！");
				}
			}
		);
	};
	
})(dinamicInfo);