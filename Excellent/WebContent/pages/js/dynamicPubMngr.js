$(function(){
	common.serActive(0);
	common.getClasses(["#classSelect"]);
	common.addCickToNav(classIntroMngr.showContent);
	classIntroMngr.getEditor();
	classIntroMngr.pubNews();
	
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart:1,
        autoclose:true,
        minView:"month",
        todayBtn:true,
        todayHighlight:true
    });
});

var classIntroMngr = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
		});
	};
	
	page.showContent = function(i){
		var pubDynamicCon = $("#pubDynamicCon");
		var delEditDinamics = $("#delEditDinamics");
		switch(i){
		case 0:{
			pubDynamicCon.show();
			delEditDinamics.hide();
		}break;
		case 1:{
			pubDynamicCon.hide();
			delEditDinamics.show();
			page.getDynamicList();
		}break;
		}
	};
	
	//发布动态
	page.pubNews = function(){
		$("#pubNews").click(function(){
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
						alert("发布成功！");
						window.open("/Excellent/pages/DynamicsInfo.html");
					}else{
						alert("发布失败，请重新尝试！");
					}
				},
				error:function(){
					alert("发布请求失败！");
				}
			});
		});
	};
	
	
	//获取班级动态列表
	page.getDynamicList = function(){
		$.getJSON(
			"/Excellent/pages/json/dynamicList.json",
			/*{
				rowNum:15,
				nowPage:1
			},*/
			function(data){
				if(data.success === true){
					var dymList = data.result;
					var html = "";
					var url = "/Excellent/pages/dynamicInfo.html";
					for(var i = 0,len = dymList.length; i < len; i++){
						html += '<tr>'
							 +  '<td class = "titleWidth" onclick = "pageToNew(\''+url+'\','+dymList[i].atyId+')">'+dymList[i].atyTitle+'</td>'
			  				 +	'<td>'+dymList[i].actyTime+'</td>'
			  				 +	'<td><button value = "'+dymList[i].atyId+'" type="button" class="btn btn-default btn-xs">编辑</button></td>'
			  				 +	'<td><button value = "'+dymList[i].atyId+'" type="button" class="btn btn-default btn-xs">删除</button></td>'
							 +  '</tr>';
					}
					$("#dynamicList").html(html);
					page.addClickDymList();
				}else{
					alert("获取班级动态列表失败，请刷新重试！");
				}
			}
		);
	};
	
	//给动态列表添加点击事件
	page.addClickDymList = function(){
		$("#dynamicList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			if(obj.length === 0){
				return;
			}
			var name= obj.html();
			var val = obj.val();
			if(name === "编辑"){
				pageToNew("/Excellent/pages/dynamicEditMngr.html",val);
			}else if(name === "删除"){
				alert(val);
			}
		});
	};
	
})(classIntroMngr);

