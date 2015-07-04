/**
 * 首页
 * @author luoxue 
 * @time 2015-06-18
 */

$(function(){
	common.serActive(0,1);
	home.addClick();
	home.getTeacherData();
	home.getClassDynamic();
});

var home = {};

(function(page){
	page.addClick = function(){
		$("#pubMessage").click(function(){
			page.pubMessage();
		});
	};
	
	//发表留言
	page.pubMessage = function(){
		var msgContent = $("#msgContent").val();
		$.ajax({
			url:"/Excellent/note/saySomething",
			type:"post",
			data:{
				msgContent : msgContent
			},
			success:function(data){
				if(data.success === true){
					alert("发表成功，请到留言板查看！");
				}else{
					alert("发表留言失败！");
				}
			},
			error:function(){
				alert("发表请求失败！");
			}
		});
	};
	
	//student show.获取班级动态列表
	page.getClassDynamic = function(){
		$.getJSON(
			"/Excellent/news/showClassNewsList",
			{
				rowNum:5,
				nowPage:1
			},
			function(data){
				if(data.success === true){
					var dymList = data.result.details;
					var html = "";
					var url = "/Excellent/pages/dynamicInfo.html";
					$("#dymList").empty();
					for(var i = 0,len = dymList.length; i < len; i++){
						html += '<div class = "oneDym">'
							 +  	'<h5><img src = "/Excellent/pages/images/guide.png"> '+dymList[i].title+'</h5>'
							 +  	'<p>'+dymList[i].summary+'</p>'
							 +  '</div>';	
					}
					$("#dymList").html(html);
				}else{
					alert("获取班级动态列表失败！");
				}
			}
		);
	};
	
	//teacher show.获取教师列表信息
	page.getTeacherData = function(){
		$.getJSON(
			"/Excellent/teacher/getTeacherList",
			{
				rowNum:4,
				nowPage:1
			},
			function(data){
				if(data.success){
					var tchList = data.result.details;
					var html = "";
					for(var i = 0, len = tchList.length; i < len; i++){
						html += '<div class = "oneTch">'
							 +    '<div class = "dis1"><img src = "'+tchList[i].photo+'"/> </div>'
							 +    '<div class = "dis2">'
							 +	  '<span class = "teacherName">'+tchList[i].true_name+'</span><br><br>'
							 +	  '<p>'+tchList[i].study_area+'</p>'
							 +    '</div>'
							 +    '<br style = "clear:both">'
						     +  '</div>';
					}
					html+='<br style = "clear:both">';
					$("#tchList").html(html);
				}else{
					alert("获取教师列表失败！");
				}
			}
		);
	};
})(home);
