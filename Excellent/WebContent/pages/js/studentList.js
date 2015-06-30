$(function(){
	common.serActive(3,1);
	common.getClasses(["#classIdforstd"],studentList.getStudentList);//班级列表加载完成后，自动获取第一个班级的学生信息列表
	studentList.addClick();
});

var studentList = {};

(function(page){
	
	page.addClick = function(){
		var flag = 0;
		$("#classIdforstd").click(function(){
			flag++;
			if(flag === 2){
				flag = 0;
				var classId = $("#classIdforstd").val();//获取选择的班级Id
				page.getStudentList(classId);
			}
		});
	};
	
	//根据班级Id获取学生信息列表
	page.getStudentList = function(classId){
		$.getJSON(
			"/Excellent/stu/queryStudent",
			{
				classID:classId,
				rowNum:34,
				nowPage:1
			},
			function(data){
				if(data.success === true){
					var std = data.result.details.list;
					var html = "<tr><th>序号</th><th>姓名</th><th>学号</th><th>班级</th><th>小组</th><th>奖项</th><th>毕业去向</th></tr>";
					for(var i = 0,len = std.length; i < len;i++){
						var prize;
						if(std[i].prize){
							prize = std[i].prize.comment;
						}else{
							prize = "暂无";
						}
						html += '<tr>'
							 + 		'<td>'+(i+1)+'</td><td><a href = "studentInfo.html#'+std[i].id+'">'+std[i].true_name+'</a></td>'
							 + 		'<td>'+std[i].school_id+'</td>'
							 + 		'<td>'+std[i].classNum+'</td>'
							 + 		'<td>'+std[i].group_name+'</td>'
							 + 		'<td>'+prize+'</td>'//奖项
							 + 		'<td>'+std[i].other+'</td>'
						  	 +   '</tr>';
					}
					$("#studentList").html(html);
				}else{
					alert("获取学生信息列表失败！");
				}
			}
		);
	};
})(studentList);