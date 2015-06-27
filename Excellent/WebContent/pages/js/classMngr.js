/**
 * @function 班级管理
 * @author luoxue 
 * @date 20150615
 */

$(function(){
	common.serActive(1,0);
	common.getClasses(["#classSelect1","#classSelect2"],classMngr.searchGroupList);
	common.addCickToNav(classMngr.showContent);
	classMngr.getEditor();
	classMngr.addClick();
	classMngr.getClassList();
});

var classMngr = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
		});
	};
	
	//选择左边哪一项，右边对应显示哪一项
	page.showContent = function(i){
		var classIntro = $("#classIntro");
		var classAddDel = $("#classAddDel");
		var classGroup = $("#classGroup");
		switch(i){
		case 0:{
			classIntro.hide();
			classAddDel.show();
			classGroup.hide();
		}break;
		case 1:{
			classIntro.hide();
			classAddDel.hide();
			classGroup.show();
		}break;
		case 2:{
			classIntro.show();
			classAddDel.hide();
			classGroup.hide();
		}break;
		}
	}
	
	page.addClick = function(){
		$("#saveEdit").click(function(){
			page.saveClassIntro();
		});
		$("#addClassBtn").click(function(){
			page.addClass();
		});
		$("#addGroupBtn").click(function(){
			page.addGroup();
		});
		$("#searchGroup").click(function(){
			var classId = $("#classSelect2").val();
			page.searchGroupList(classId);
		});
	};
	
	//保存编辑 班级简介
	page.saveClassIntro = function(){
		var content = editor.html(); //班级简介内容
		var classTypeSelect = $("#classTypeSelect").val();//班级类型
		console.log(content,classTypeSelect);
		if(content == "" || classTypeSelect == ""){
			alert("请完善信息！");
			return;
		}
		$.ajax({
			url:"/Excellent/class/setClassInfo",
			type:"post",
			dataType:"json",
			data:{
				introContent: content,
				classType: classTypeSelect
			},
			success:function(data){
				if(data.success === true){
					alert("保存成功！");
					window.open("/Excellent/pages/classIntro.html");
				}else{
					alert("保存失败，请重新尝试！");
				}
			},
			error:function(){
				alert("保存请求失败！");
			}
		});
	};
	
	//添加班级
	page.addClass = function(){
		var className = $("#className").val();
		$.ajax({
			url:"/Excellent/class/newClass",
			type:"post",
			dataType:"json",
			data:{
				className:className
			},
			success:function(data){
				if(data.success === true){
					alert("添加成功！");
					page.getClassList();
				}else{
					alert("添加失败: "+data.error+" ！");
				}
			},
			error:function(){
				alert("添加请求失败！");
			}
		});
	}
	
	//获取班级列表-表格形式
	page.getClassList = function(){
		$.ajax({
			url:"/Excellent/class/getClassList",
			type:"get",
			dataType:"json",
			success:function(data){
				if(data.success === true){
					var classList = data.result.class_list.list;
					var html = "<tr><th>序号</th><th>班级</th><th>删除</th></tr>";
					for(var i = 0, len = classList.length; i < len; i++){
						html += '<tr><td>'+(i + 1)+'</td><td>'+classList[i].classNum+'</td><td><button type="button" class="btn btn-sm" value = "'+classList[i].id+'">删除</button></td></tr>';
					}
					$("#classList").html(html);
					page.deleteClass();
				}else{
					console.log("班级列表获取失败!");
				}
			},
			error:function(){
				console.log("班级列表请求失败！");
			}
		});
	};
	
	//删除班级
	page.deleteClass = function(){
		$("#classList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "删除"){
				$.ajax({
					url:"/Excellent/class/deleteClass",
					dataType:"json",
					data:{
						classId:val
					},
					success:function(data){
						if(data.success){
							alert("删除成功！");
							location.reload();
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
	
	//为某个班级添加分组
	page.addGroup = function(){
		var groupName = $("#groupName").val();
		var classSelect = $("#classSelect1").val();
		$.ajax({
			url:"/Excellent/class/addGroup",
			type:"post",
			dataType:"json",
			data:{
				"group.group_name":groupName,
				"group.class_id":classSelect
			},
			success:function(data){
				if(data.success === true){
					alert("添加成功，请刷新查看！");
				}else{
					alert("添加失败：" + data.error + " ！");
				}
			},
			error:function(){
				alert("添加请求失败！");
			}
		});
	};
	
	//删除某个班级的某个分组
	page.deleteGroup = function(){
		$("#groupList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "删除"){
				$.ajax({
					url:"/Excellent/class/deleteGroup",
					dataType:"json",
					data:{
						id:val
					},
					success:function(data){
						if(data.success){
							alert("删除成功！请刷新查看");
							location.reload();
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
	
	//根据班级查询分组情况
	page.searchGroupList = function(classId){
		$.ajax({
			url:"/Excellent/class/getGroupList",
			type:"get",
			dataType:"json",
			data:{
				class_id:classId
			},
			success:function(data){
				var groupList = data.result.details;
				var html = "<tr><th>序号</th><th>分组</th><th>删除</th></tr>";
				if(data.success === true){
					for(var i = 0, len = groupList.length; i < len; i++){
						html += '<tr><td>'+(i + 1)+'</td><td>'+groupList[i].group_name+'</td><td><button type="button" class="btn btn-sm" value = "'+groupList[i].id+'">删除</button></td></tr>';
					}
					$("#groupList").html(html);
					page.deleteGroup();
				}else{
					alert("查询分组失败，请重新尝试！");
				}
			},
			error:function(){
				alert("查询分组请求失败！");
			}
		});
	};
	
})(classMngr);