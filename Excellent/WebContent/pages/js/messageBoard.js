$(function(){
	common.serActive(6,1);
	messageBoard.loadMessages();
});

var messageBoard = {};

(function(page){
	//加载留言列表
	page.loadMessages = function(){
		$.getJSON(
			"/Excellent/note/getNotesList",
			{
				rowNum: 15,
				nowPage: 1
			},
			function(data){
				if(data.success){
					var msgList = data.result.details;
					var html = '<tr><th class = "time">时间</th><th>留言</th></tr>';
					for(var i = 0, len = msgList.length; i < len; i++){
						html += '<tr><td class = "time">'+msgList[i].timestamp+'</td><td>'+msgList[i].content+'</td></tr>';
					}
					$("#messageList").html(html);
				}else{
					alert("获取留言列表失败！");
				}
			}
		);
		//loadDevidePage(10,5,1,{run:function(){console.log(1);}});
	}
})(messageBoard);