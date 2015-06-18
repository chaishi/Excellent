$(function(){
	common.serActive(2,0);
	common.getClasses(["#classId","#classIddel"],studentInfoMngr.getStdNameList);//函数用来显示默认的姓名列表
	common.addCickToNav(studentInfoMngr.showContent);
	studentInfoMngr.delegateEdit();
	studentInfoMngr.getGroupList();
	studentInfoMngr.addClick();
});

var studentInfoMngr = {};

(function(page){
	
	//事件代理：表格，当点击编辑按钮时
	page.delegateEdit = function(){
		$("#studentList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "编辑"){
				//alert(val);
			}else if(name === "删除"){
				alert(val);
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
		
		var num = 0;
		$("#classId").click(function(){
			num++;
			if(num === 2){
				num = 0;
				var classId = $(this).val();
				page.getGroupList(classId);
			}
		});
	};
	
	//根据classId获取班级分组情况
	page.getGroupList = function(classId){
		$.getJSON(
			"/Excellent/pages/json/groupList.json",
			//{
			//	classId:classId
			//},
			function(data){
				if(data.success === true){
					var html = "";
					var groups = data.result;
					for(var i = 0, len = groups.length; i < len ; i++){
						html += '<option value = "'+groups[i].groupId+'">'+groups[i].groupName+'</option>';
					}
					$("#groupSelect").html(html);
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
		console.log(classId,groupSelect,studentName,studentId,graduateInfo,prizeInfo,describle);
		if(studentName == ""){
			alert("请输入学生姓名！");
			return;
		}
		if(studentId == ""){
			alert("请输入学生学号！");
			return;
		}
		$.ajax({
			url:"",
			type:"post",
			dataType:"json",
			data:{
				classId : classId,
				groupId : groupSelect,
				stdName : studentName,
				stdId : studentId,
				graduate : graduateInfo,
				prizes : prizeInfo,
				stdInfo : describle
			},
			success:function(data){
				if(data.success){
					alert("添加成功！");
					window.open("/Excellent/pages/studentInfo.html");
				}else{
					alert("添加失败！");
				}
			},
			error:function(){
				alert("添加请求失败！");
			}
		});
	}
	
	//获取学生姓名列表
	page.getStdNameList = function(classId){
		$.getJSON(
			"/Excellent/pages/json/studentNameList.json",
			/*{
				classId:classId
			},*/
			function(data){
				if(data.success){
					var stds = data.result;
					var html = '<tr><th>序号</th><th>姓名</th><th>删除</th><th>编辑</th></tr>';
					for(var i = 0, len = stds.length; i < len; i++){
						html += '<tr>'
				  			 +  '<td>1</td><td>'+stds[i].studentName+'</td>'
				  			 +  '<td><button type="button" class="btn btn-sm" value = "'+stds[i].studentId+'">删除</button></td>'
				  			 +  '<td><button type="button" class="btn btn-sm" value = "'+stds[i].studentId+'"  data-toggle="modal" data-target="#editStudent">编辑</button></td>'
				  			 + '</tr>';
					}
					$("#studentList").html(html);
					
				}else{
					alert("获取学生姓名列表失败！");
				}
			}
		);
	};
})(studentInfoMngr);