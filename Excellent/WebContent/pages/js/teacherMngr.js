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
		 $(":button").click(function () {
             if ($("#file1").val().length > 0) {
                 page.ajaxFileUpload();
             }
             else {
                 alert("请选择图片");
             }
         })
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
	
	//上传图片
	page.ajaxFileUpload = function() {
       $.ajaxFileUpload
       (
           {
               url: '/Excellent/file/uploadImg', //用于文件上传的服务器端请求地址
               type: 'post',
              /*  data: {  name: 'imgFile' }, //此参数非常严谨，写错一个引号都不行 */
               secureuri: false, //一般设置为false
               fileElementId: 'file1', //文件上传空间的id属性  <input type="file" id="file" name="file" />
               dataType: 'json', //返回值类型 一般设置为json
               success: function (data)  //服务器成功响应处理函数
               {
                   if (typeof (data.error) != 'undefined') {
                       if (data.error != 0) {
                       	alert('errorCode'+data.error+';msg:'+data.message);
                       	return;
                       } 
                   }
                   console.log(data.url);
                   $("#img1").attr("src", data.url);
               },
               error: function (data, status, e)//服务器响应失败处理函数
               {
                   alert(1);
               }
           }
       )
       return false;
   };
	
	//添加老师
	page.addTeacher = function(){
		var teacherName = $("#teacherName").val();
		var teacherDsb = $("#teacherDsb").val();
		var teacherHeaderFile = $("#img1").attr("src");
		console.log(teacherHeaderFile);
		if(teacherName == "" || teacherDsb == "" || teacherHeaderFile == ""){
			alert("请完善信息！");
			return;
		}
		$.ajax({
			url:"/Excellent/teacher/newTeacher",
			type:"post",
			dataType:"json",
			data:{
				"t.true_name": teacherName,
				"t.photo": teacherHeaderFile,
				"t.tips": teacherDsb
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