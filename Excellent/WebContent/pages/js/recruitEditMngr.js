$(function(){
	common.serActive(3,0);
	recuitEdit.getEditor();
	recuitEdit.saveRecuit();
	
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart:1,
        autoclose:true,
        minView:"month",
        todayBtn:true,
        todayHighlight:true
    });
	
	$("#logout").click(function(){
		recuitEdit.clickLogout();
	});
});

var recuitEdit = {};

(function(page){
	var editor;
	page.getEditor = function(){
		var options = {
		    basePath:'kindeditor-4.1.10/',
			allowFileManager:false,
			uploadJson:"/Excellent/file/uploadfile",
			imageUploadJson:"/Excellent/file/uploadImg"	,
			afterUpload:function(url){
				$('textarea[name="content"]').innerHtml='<img src='+url+'>';
			},
		};
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', options);
			page.getRecuitInfo();
		});
	};
	
	//根据动态Id 获取 招新内容
	page.getRecuitInfo = function(){
		atyIds = location.hash.split("#");
		$.getJSON(
			"/Excellent/news/showNewsDetail",
			{
				atyId:atyIds[1]
			},
			function(data){
				if(data.success){
					var recuit = data.result.details;
					$("#title").val(recuit.title); //动态标题
					$("#recruitDate").val(recuit.happen_time);
					editor.html(recuit.content); //动态内容
				}else{
					alert("获取招新信息失败！");
				}
			}
		);
	};
	
	//保存编辑
	page.saveRecuit = function(){
		$("#saveRecuit").click(function(){
			var title = $("#title").val(); //动态标题
			var content = editor.html(); //动态内容
			var recruitDate = $("#recruitDate").val();
			var recruitId = location.hash.split("#")[1];
			//console.log(title,content);
			if(title == "" || content == ""){
				alert("请完善信息！");
				return;
			}
			$.ajax({
				url:"/Excellent/news/mergeNews",
				type:"post",
				data:{
					"news.id":recruitId,
					"news.type":2,
					"news.title": title,
					"news.content": content,
					"news.happen_time": recruitDate
				},
				success:function(data){
					if(data.success === true){
						alert("保存成功！");
						window.open("/Excellent/pages/recruitInfo.html#" + recruitId);
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
})(recuitEdit);