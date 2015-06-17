$(function(){
	common.serActive(3);
	recuitEdit.getEditor();
	recuitEdit.saveRecuit();
});

var recuitEdit = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
			page.getRecuitInfo();
		});
	};
	
	//根据动态Id 获取 招新内容
	page.getRecuitInfo = function(){
		$.getJSON(
			"/Excellent/pages/json/recuitInfo.json",
			function(data){
				if(data.success){
					var recuit = data.result;
					$("#title").val(recuit.recuritTitle); //动态标题
					editor.html(recuit.recuritContent); //动态内容
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
			console.log(title,content);
			if(title == "" || content == ""){
				alert("请完善信息！");
				return;
			}
			$.ajax({
				url:"",
				type:"post",
				dataType:"json",
				data:{
					title:title,
					content:content,
				},
				success:function(data){
					if(data.success === true){
						alert("保存成功！");
						window.open("/Excellent/pages/recuritInfo.html#" + data.result.acyId);
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
})(recuitEdit);