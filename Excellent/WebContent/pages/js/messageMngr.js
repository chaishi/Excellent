/**
 * @page 审核留言管理
 * @author luoxue
 * @time 20150614
 */
$(function(){
	common.serActive(5,0);
	messageMngr.loadMessages();
});

var messageMngr = {};

(function(page){
	//加载留言列表
	page.loadMessages = function(){
		$.getJSON(
			"/Excellent/note/getNotesList",
			function(data){
				if(data.success){
					var msgList = data.result.details;
					var html = '<tr><th class = "time">时间</th><th>留言</th><th> </th></tr>';
					for(var i = 0, len = msgList.length; i < len; i++){
						html += '<tr>'
				  			 +		'<td class = "time">'+msgList[i].timestamp+'</td>'
				  			 +		'<td>'+msgList[i].content+'</td>'
				  			 +		'<td class = "smWidth"><button type="button" class="btn btn-sm" value = "'+msgList[i].id+'">删除</button></td>'
				  			 +	'</tr>';
					}
					$("#messageList").html(html);
					page.addDelegateToTable();
				}else{
					alert("获取留言列表失败！");
				}
			}
		);
		loadDevidePage(10,5,1,{run:function(){console.log(1);}});
	}
	
	//给表格添加点击事件,事件代理， 找到删除单元格
	page.addDelegateToTable = function(){
		$("#messageList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "删除"){
				alert(val);
				$.ajax({
					url:"/Excellent/note/deleteNote",
					data:{
						note_id: val
					},
					type:"post",
					success:function(data){
						if(data.success === true){
							alert("删除成功！");
							page.loadMessages();
						}else{
							alert("删除失败！");
						}
					},
					error:function(){
						alert("删除请求失败！");
					}
				});
			}
		});
	};
})(messageMngr);