$(function(){
	common.serActive(0,0);
	editDynamic.getEditor();
	editDynamic.saveDynamic();
	
	//日期选择器
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart:1,
        autoclose:true,
        minView:"month",
        todayBtn:true,
        todayHighlight:true
    });
	
	$("#logout").click(function(){
		editDynamic.clickLogout();
	});
});

var editDynamic = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			var options = {
			    basePath:'kindeditor-4.1.10/',
				allowFileManager:false,
				uploadJson:"/Excellent/file/uploadfile",
				imageUploadJson:"/Excellent/file/uploadImg"	,
				afterUpload:function(url){
					$('textarea[name="content"]').innerHtml='<img src='+url+'>';
				},
			};
			editor = K.create('textarea[name="content"]', options);
			editDynamic.getDynamic();
		});
	};
	
	//获取班级列表，并将当前班级设为selected
	page.getClasses1 = function(){
		var ids = arguments[0];
		var className = arguments[1];
		$.ajax({
			url:"/Excellent/class/getClassList",
			type:"get",
			dataType:"json",
			success:function(data){
				if(data.success === true){
					var classList = data.result.class_list.list;
					var html = "";
					for(var i = 0, len = classList.length; i < len; i++){
						if(classList[i].classNum == className)
							html += '<option value = "'+classList[i].id+'" selected>'+classList[i].classNum+'</option>';
						else
							html += '<option value = "'+classList[i].id+'">'+classList[i].classNum+'</option>';
					}
					for(var j = 0, n = ids.length; j < n; j++){
						$(ids[j]).html(html);
					}
				}else{
					console.log("班级列表获取失败!");
				}
			},
			error:function(){
				console.log("班级列表请求失败！");
			}
		});
	}
	
	//根据动态Id 获取 动态内容
	page.getDynamic = function(){
		var dynamicId = location.hash.split("#");
		//console.log(dynamicId[1]);
		$.getJSON(
			"/Excellent/news/showNewsDetail",
			{
				atyId:dynamicId[1]
			},
			function(data){
				if(data.success){
					var dym = data.result.details;
					$("#title").val(dym.title); //动态标题
					//$("#classSelect").val(dym.classNum); //班级
					page.getClasses1(["#classSelect"],dym.classNum);
					$("#dynamicDate").val(dym.happen_time); //动态时间
					editor.html(dym.content); //动态内容
				}else{
					alert("获取班级动态详情失败！");
				}
			}
		);
	};
	
	//保存编辑
	page.saveDynamic = function(){
		$("#saveDynamic").click(function(){
			var title = $("#title").val(); //动态标题
			var content = editor.html(); //动态内容
			var classId = $("#classSelect").val(); //班级
			var dynamicDate = $("#dynamicDate").val(); //动态时间
			var dynamicId = location.hash.split("#");
			//console.log(title,content,classId,dynamicDate);
			if(title == "" || content == "" || classId == "" || dynamicDate == ""){
				alert("请完善信息！");
				return;
			}
			$.ajax({
				url:"/Excellent/news/mergeNews",
				type:"post",
				dataType:"json",
				data:{
					"news.id":dynamicId[1],
					"news.type":1,
					"news.title":title, 
					"news.content":content,
					"news.class_id":classId, 
					"news.happen_time":dynamicDate
				},
				success:function(data){
					if(data.success === true){
						alert("保存成功！");
						window.open("/Excellent/pages/dynamicInfo.html#" + dynamicId[1]);
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
	
	
	page.clickLogout = function(){		
		$.ajax({
			url:"/Excellent/logout",
			type:"post",
			success:function(data){
				if(data.success === true){
					alert("退出成功！");
					location.href="/Excellent/pages/home.html";
				}else{
					alert("退出失败！");
				}
			},
			error:function(msg){
				alert("网络超时！");
			}
		});
	}
})(editDynamic);