$(function(){
	common.serActive(2,0);
	common.getClasses(["#classId","#classIddel","#classIdEdit"],studentInfoMngr.afterGetClassList);//函数用来显示默认的分组列表
	common.addCickToNav(studentInfoMngr.showContent);
	studentInfoMngr.delegateEdit();
	studentInfoMngr.addClick();
});

var studentInfoMngr = {};

(function(page){
	//获取学生姓名列表
	page.getStdNameList = function(classId){
		$.getJSON(
			"/Excellent/stu/getStuList",
			{
				class_id:classId
			},
			function(data){
				if(data.success){
					var stds = data.result.stu_list;
					var html = '<tr><th>序号</th><th>姓名</th><th>删除</th><th>编辑</th></tr>';
					for(var i = 0, len = stds.length; i < len; i++){
						html += '<tr>'
				  			 +  '<td>'+(i+1)+'</td><td>'+stds[i].true_name+'</td>'
				  			 +  '<td><button type="button" class="btn btn-sm" value = "'+stds[i].id+'">删除</button></td>'
				  			 +  '<td><button type="button" class="btn btn-sm" value = "'+stds[i].id+'"  data-toggle="modal" data-target="#editStudent">编辑</button></td>'
				  			 + '</tr>';
					}
					$("#studentList").html(html);
					
				}else{
					alert("获取学生姓名列表失败！");
				}
			}
		);
	};
	
	//事件代理：表格，当点击编辑按钮时
	page.delegateEdit = function(){
		$("#studentList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var studentId = obj.val();
			if(name === "编辑"){
				page.getStudentInfo(studentId);
				//为保存编辑添加点击事件
				$("#saveEditStudent").click(function(){
					page.saveEditStudent(studentId);
				});
			}else if(name === "删除"){
				$.ajax({
					url:"/Excellent/stu/deleteStudent",
					type:"post",
					data:{
						id:studentId
					},
					success:function(data){
						if(data.success){
							alert("删除成功，请刷新查看！");
						}else{
							alert("删除失败");
						}
					}
				});
			}
		});
	};
	
	
	//选择左边哪一项，右边对应显示哪一项
	page.showContent = function(i){
		var addStudent = $("#addStudent");
		var delStudent = $("#delStudent");
		switch(i){
		case 0:{
			addStudent.show();
			delStudent.hide();
		}break;
		case 1:{
			addStudent.hide();
			delStudent.show();
		}break;
		}
	}
	
	//添加点击事件
	page.addClick = function(){
		$("#searchStdNames").click(function(){
			var classId = $("#classIddel").val();
			page.getStdNameList(classId);
		});
		
		$("#addStudentBtn").click(function(){
			page.addStudent();
		});
		
		//添加学生界面，选择不同的班级获取对应的分组情况
		var num = 0;
		$("#classId").click(function(){
			num++;
			if(num === 2){
				num = 0;
				var classId = $(this).val();
				page.getGroupList(classId,"#groupSelect");
			}
		});
		
		//编辑学生界面，选择不同的班级获取对应的分组情况
		var num1 = 0;
		$("#classIdEdit").click(function(){
			num1++;
			if(num1 === 2){
				num1 = 0;
				var classId = $(this).val();
				page.getGroupList(classId,"#groupIdEdit");
			}
		});
		
	};
	
	//根据classId获取班级分组情况
	page.getGroupList = function(classId,contentId){
		$.getJSON(
			"/Excellent/class/getGroupList",
			{
				class_id:classId
			},
			function(data){
				if(data.success === true){
					var html = "";
					var groups = data.result.details;
					for(var i = 0, len = groups.length; i < len ; i++){
						html += '<option value = "'+groups[i].id+'">'+groups[i].group_name+'</option>';
					}
					$(contentId).html(html);
				}else{
					alert("获取分组失败！");
				}
			}
		);
	};
	
	//添加学生信息
	page.addStudent = function(){
		var classId = $("#classId").val();
		var groupSelect = $("#groupSelect").val();
		var studentName = $("#studentName").val();
		var studentId = $("#studentId").val();
		var graduateInfo = $("#graduateInfo").val();
		var prizeInfo = $("#prizeInfo").val();
		var describle = $("#describle").val();
		//console.log(classId,groupSelect,studentName,studentId,graduateInfo,prizeInfo,describle);
		if(studentName == ""){
			alert("请输入学生姓名！");
			return;
		}
		if(studentId == ""){
			alert("请输入学生学号！");
			return;
		}
		$.ajax({
			url:"/Excellent/stu/newStudent",
			type:"post",
			dataType:"json",
			data:{
				class_id: classId,
				group_id: groupSelect,
				true_name: studentName,
				school_id: studentId,
				others: graduateInfo,
				prizes: prizeInfo,
				self_sign: describle
			},
			success:function(data){
				if(data.success){
					alert("添加成功！");
					window.open("/Excellent/pages/studentInfo.html#"+data.result.details);
				}else{
					alert("添加失败！");
				}
			},
			error:function(){
				alert("添加请求失败！");
			}
		});
	}
	
	//获取学生详情
	page.getStudentInfo = function(studentdId){
		$.getJSON(
			"/Excellent/stu/getStuInfo",
			{
				stdId:studentdId
			},
			function(data){
				if(data.success){
					var std = data.result.stu_detail;
					$("#classIdEdit").val(std.class_id);
					$("#studentNameEdit").val(std.true_name);
					$("#studentIdEdit").val(std.school_id);
					$("#graduateEdit").val(std.other);
					var prizes = "";
					for(var i = 0, len = std.prizes.length; i < len; i++){
						prizes += ""+std.prizes[i]+"";
						if(i != len - 1)
							prizes += ",";
					}
					if(i == 0)prizes = "暂无";
					$("#prizesEdit").val(prizes);
					$("#otherEdit").val(std.self_sign);
					
					//获取分组
					page.getGroupList(std.class_id,"#groupIdEdit");
					$("#groupIdEdit").val(std.group_name);//分组id
				}else{
					alert("获取学生详情失败！");
				}
			}
		);
	};
	
	//一进入页面，获取班级列表后，需要立即执行的函数，1：该班级的分组情况 2：该班级的学生列表
	page.afterGetClassList = function(classId){
		page.getGroupList(classId);
		page.getStdNameList(classId);
	};
	
	//保存编辑后的学生资料
	page.saveEditStudent = function(stdId){
		var classId = $("#classIdEdit").val();
		var groupId = $("#groupIdEdit").val();
		var studentName = $("#studentNameEdit").val();
		var studentId = $("#studentIdEdit").val();
		var graduateInfo = $("#graduateEdit").val();
		var prizeInfo = $("#prizesEdit").val();
		var describle = $("#otherEdit").val();
		console.log(groupId);
		if(studentName == ""){
			alert("请输入学生姓名！");
			return;
		}
		if(studentId == ""){
			alert("请输入学生学号！");
			return;
		}
		$.ajax({
			url:"/Excellent/stu/updateStudent",
			type:"post",
			data:{
				id: stdId,
				class_id: classId,
				group_id: groupId,
				true_name: studentName,
				school_id: studentId,
				others: graduateInfo,
				prizes: prizeInfo,
				self_sign: describle
			},
			success:function(data){
				if(data.success === true){
					alert("编辑成功!");
					window.open("/Excellent/pages/studentInfo.html#" + stdId);
				}else{
					alert("编辑失败！");
				}
			},
			error:function(){
				alert("编辑请求失败！");
			}
		});
	};
})(studentInfoMngr);