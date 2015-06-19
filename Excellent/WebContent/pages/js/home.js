/**
 * 首页
 * @author luoxue 
 * @time 2015-06-18
 */

$(function(){
	common.serActive(0,1);
	home.addClick();
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
	}
})(home);
