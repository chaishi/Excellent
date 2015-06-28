$(function(){
	common.serActive(6,1);
	messageBoard.loadMessages.run(1);
});

var messageBoard = {};

(function(page){
	//加载留言列表
	page.loadMessages = {
		rowNum:15,
		pageNum:1,
		loadPage:false,
		run:function(index){
			$.getJSON(
				"/Excellent/note/getNotesList",
				{
					rowNum: 15,
					nowPage: index
				},
				function(data){
					if(data.success){
						var msgList = data.result.details;
						var html = '<tr><th class = "time">时间</th><th>留言</th></tr>';
						for(var i = 0, len = msgList.length; i < len; i++){
							html += '<tr><td class = "time">'+msgList[i].timestamp+'</td><td>'+msgList[i].content+'</td></tr>';
						}
						$("#messageList").html(html);
						//加载分页
						if(page.loadMessages.loadPage == false){
							page.loadMessages.pageNum = data.result.totalPage;
							loadDevidePage(page.loadMessages.pageNum,10,1,data.result.totalRow,page.loadMessages);
							page.loadMessages.loadPage = true;//分页已完成
						}
					}else{
						alert("获取留言列表失败！");
					}
				}
			);
		}
	};
})(messageBoard);