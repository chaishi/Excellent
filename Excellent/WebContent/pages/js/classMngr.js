/**
 * @function 班级管理
 * @author luoxue 
 * @date 20150615
 */

$(function(){
	common.getBanner(1);
	common.getClasses("#classSelect1");
	common.getClasses("#classSelect2");
	classMngr.getEditor();
	classMngr.addCickToNav();
	classMngr.saveClassIntro();
	classMngr.addClass();
	classMngr.addGroup();
	classMngr.addClickToSearchGroup();
	classMngr.searchGroupList();
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
	
	//为侧边栏添加点击事件
	page.addCickToNav = function(){
		$("#navLeft").delegate('li','click',function(){
			var index = $(this).index();
			page.setActiveNav(index);
			page.showContent(index);
		});
	}
	
	//为侧边栏设置active
	page.setActiveNav = function(index){
		$("#navLeft li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}else{
				$(this).removeClass('active');
			}
		});
	}
	
	//选择左边哪一项，右边对应显示哪一项
	page.showContent = function(i){
		var classIntro = $("#classIntro");
		var classAddDel = $("#classAddDel");
		var classGroup = $("#classGroup");
		switch(i){
		case 0:{
			classIntro.show();
			classAddDel.hide();
			classGroup.hide();
		}break;
		case 1:{
			classIntro.hide();
			classAddDel.show();
			classGroup.hide();
			page.getClassList();
		}break;
		case 2:{
			classIntro.hide();
			classAddDel.hide();
			classGroup.show();
		}break;
		}
	}
	
	//保存编辑 班级简介
	page.saveClassIntro = function(){
		$("#saveEdit").click(function(){
			var content = editor.html(); //班级简介内容
			var classTypeSelect = $("#classTypeSelect").val();//班级类型
			console.log(content,classTypeSelect);
			if(content == "" || classTypeSelect == ""){
				alert("请完善信息！");
				return;
			}
			$.ajax({
				url:"",
				type:"post",
				dataType:"json",
				data:{
					content:content,
					classTypeSelect:classTypeSelect
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
		});
	};
	
	//添加班级
	page.addClass = function(){
		$("#addClassBtn").click(function(){
			var className = $("#className").val();
			$.ajax({
				url:"",
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
						alert("添加失败，请重新尝试！");
					}
				},
				error:function(){
					alert("添加请求失败！");
				}
			});
		});
	}
	
	//获取班级列表-表格形式
	page.getClassList = function(){
		$.ajax({
			url:"/Excellent/pages/json/classNameList.json",
			type:"get",
			dataType:"json",
			success:function(data){
				if(data.success === true){
					var classList = data.result;
					var html = "<tr><th>序号</th><th>班级</th><th>删除</th></tr>";
					for(var i = 0, len = classList.length; i < len; i++){
						html += '<tr><td>'+(i + 1)+'</td><td>'+classList[i].className+'</td><td><button type="button" class="btn btn-sm" value = "'+classList[i].classId+'">删除</button></td></tr>';
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
				alert(val);
				$.ajax({
					url:"",
					dataType:"json",
					data:{
						classId:val
					},
					success:function(data){
						if(data.success){
							alert("删除成功！");
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
		$("#addGroupBtn").click(function(){
			var groupName = $("#groupName").val();
			var classSelect = $("#classSelect1").val();
			$.ajax({
				url:"",
				type:"post",
				dataType:"json",
				data:{
					groupName:groupName,
					classId:classSelect
				},
				success:function(data){
					if(data.success === true){
						alert("添加成功，请刷新查看！");
					}else{
						alert("添加失败，请重新尝试！");
					}
				},
				error:function(){
					alert("添加请求失败！");
				}
			});
		});
	};
	
	//删除某个班级的某个分组
	page.deleteGroup = function(){
		$("#groupList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "删除"){
				alert(val);
				$.ajax({
					url:"",
					dataType:"json",
					data:{
						groupId:val
					},
					success:function(data){
						if(data.success){
							alert("删除成功！");
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
	
	page.addClickToSearchGroup = function(){
		$("#searchGroup").click(function(){
			page.searchGroupList();
		});
	};
	
	//根据班级查询分组情况
	page.searchGroupList = function(){
		var classId = $("#classSelect2");
		$.ajax({
			url:"/Excellent/pages/json/groupList.json",
			type:"get",
			dataType:"json",
			/*data:{
				classId:classId
			},*/
			success:function(data){
				var groupList = data.result;
				var html = "<tr><th>序号</th><th>分组</th><th>删除</th></tr>";
				if(data.success === true){
					for(var i = 0, len = groupList.length; i < len; i++){
						html += '<tr><td>'+(i + 1)+'</td><td>'+groupList[i].groupName+'</td><td><button type="button" class="btn btn-sm" value = "'+groupList[i].groupId+'">删除</button></td></tr>';
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