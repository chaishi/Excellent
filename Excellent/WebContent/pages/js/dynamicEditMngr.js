$(function(){
	common.serActive(0,0);
	common.getClasses(["#classSelect"]);
	editDynamic.getEditor();
	editDynamic.saveDynamic();
	
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart:1,
        autoclose:true,
        minView:"month",
        todayBtn:true,
        todayHighlight:true
    });
});

var editDynamic = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
			editDynamic.getDynamic();
		});
	};
	
	//根据动态Id 获取 动态内容
	page.getDynamic = function(){
		$.getJSON(
			"/Excellent/pages/json/dynamicInfo.json",
			function(data){
				if(data.success){
					var dym = data.result;
					$("#title").val(dym.atyTitle); //动态标题
					$("#classSelect").val(dym.className); //班级
					$("#dynamicDate").val(dym.atyDate); //动态时间
					editor.html(dym.atyContent); //动态内容
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
			console.log(title,content,classId,dynamicDate);
			if(title == "" || content == "" || classId == "" || dynamicDate == ""){
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
					classId:classId,
					dynamicDate:dynamicDate
				},
				success:function(data){
					if(data.success === true){
						alert("保存成功！");
						window.open("/Excellent/pages/DynamicsInfo.html#" + data.result.acyId);
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
})(editDynamic);