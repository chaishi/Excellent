$(function(){
	common.serActive(4,0);
	common.addCickToNav(teacherMngr.showContent);
	teacherMngr.addClick();
	teacherMngr.getTeacherList();
});

var teacherMngr = {};

(function(page){
	
	//添加点击事件
	page.addClick = function(){
		$("#addTeacherBtn").click(function(){
			page.addTeacher();
		});
	};
	
	page.showContent = function(i){
		var addTeacherInfo = $("#addTeacherInfo");
		var delTeacherInfo = $("#delTeacherInfo");
		switch(i){
		case 0:{
			addTeacherInfo.show();
			delTeacherInfo.hide();
		}break;
		case 1:{
			addTeacherInfo.hide();
			delTeacherInfo.show();
		}break;
		}
	};
	
	//添加老师
	page.addTeacher = function(){
		var teacherName = $("#teacherName").val();
		var teacherDsb = $("#teacherDsb").val();
		var teacherHeaderFile = $("#teacherHeaderFile").val();
		if(teacherName == "" || teacherDsb == "" || teacherHeaderFile == ""){
			alert("请完善信息！");
			return;
		}
		$.ajax({
			url:"",
			type:"post",
			dataType:"json",
			data:{
				teacherName: teacherName,
				teacherHeadPath: teacherHeaderFile,
				teacherDescrible: teacherDsb
			},
			success:function(data){
				if(data.success === true){
					alert("添加成功！");
				}else{
					alert("添加失败！");
				}
			},
			error:function(){
				alert("添加请求失败！");
			}
		});
	}
	
	//获取老师列表
	page.getTeacherList = function(){
		$.getJSON(
			"/Excellent/teacher/getTeacherList",
			function(data){
				if(data.success){
					var tchList = data.result.details;
					var html = "<tr><th>序号</th><th>姓名</th><th>头像</th><th>删除</th></tr>";
					for(var i = 0, len = tchList.length; i < len; i++){
						html += '<tr>'
							 + 		'<td>'+(i + 1)+'</td><td>'+tchList[i].true_name+'</td>'
							 + 		'<td><img src = "'+tchList[i].photo+'"/></td>'
							 + 		'<td><button type="button" class="btn btn-sm" value = "'+tchList[i].id+'">删除</button></td>'
							 +	'</tr>'; 
					}
					$("#teacherList").html(html);
					page.addDelegateToTable();
				}else{
					alert("获取老师列表失败！");
				}
			}
		);
	}
	//给表格添加点击事件,事件代理， 找到删除单元格
	page.addDelegateToTable = function(){
		$("#teacherList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "删除"){
				alert(val);
				$.ajax({
					url:"/Excellent/teacher/deleteTeacher",
					data:{
						teacherId:val
					},
					type:"post",
					success:function(data){
						if(data.success==true){
							alert("删除成功！");
							page.getTeacherList();
						}
						else{
							alert("删除失败！");
						}
					},
					error:function(){
						alert("请求失败！");
					}
				});
			}
		});
	};
})(teacherMngr);