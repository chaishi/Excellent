$(function(){
	common.serActive(3,0);
	common.addCickToNav(recruitEdit.showContent);
	recruitEdit.getEditor();
	recruitEdit.pubRecuit();
	
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart:1,
        autoclose:true,
        minView:"month",
        todayBtn:true,
        todayHighlight:true
    });
	
	$("#logout").click(function(){
		recruitEdit.clickLogout();
	});

});

var recruitEdit = {};

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
			editor = K.create('textarea[name="content"]',options);
		});
	};
	
	page.showContent = function(i){
		var pubRecuit = $("#pubRecuit");
		var delEditRecuit = $("#delEditRecuit");
		switch(i){
		case 0:{
			pubRecuit.show();
			delEditRecuit.hide();
		}break;
		case 1:{
			pubRecuit.hide();
			delEditRecuit.show();
			page.getDynamicList.run(1);
		}break;
		}
	};
	
	//发布招新信息
	page.pubRecuit = function(){
		$("#pubRecuitBtn").click(function(){
			var title = $("#title").val();
			var dynamicDate = $("#dynamicDate").val();
			var content = editor.html();
			if(title == "" || content == ""){
				alert("请完善信息！");
				return;
			}
			//console.log(title,content);
			$.ajax({
				url:"/Excellent/news/writeNews",
				type:"post",
				data:{
					title: title,
					content: content,
					type:2,
					happen_time:dynamicDate
				},
				success:function(data){
					if(data.success === true){
						alert("发布成功！");
						window.open("/Excellent/pages/recruitInfo.html#" + data.result.id);
					}else{
						alert("发布失败！");
					}
				},
				error:function(){
					alert("发布招新信息请求失败！");
				}
			});
		});
	};
	
	//获取招新信息列表
	page.getDynamicList = {
		rowNum:15,
		pageNum:1,
		loadPage:false,
		run:function(index){
			$.getJSON(
				"/Excellent/news/showClassNewsList",
				{
					rowNum:this.rowNum,
					nowPage:index,
					type:2
				},
				function(data){
					if(data.success === true){
						var recuits = data.result.details;
						var html = "";
						var url = "/Excellent/pages/recruitInfo.html";
						for(var i = 0,len = recuits.length; i < len; i++){
							html += '<tr>'
								 +  '<td class = "titleWidth" onclick = "pageToNew(\''+url+'\','+recuits[i].id+')">'+recuits[i].title+'</td>'
				  				 +	'<td>'+recuits[i].happen_time+'</td>'
				  				 +	'<td><button value = "'+recuits[i].id+'" type="button" class="btn btn-default btn-xs">编辑</button></td>'
				  				 +	'<td><button value = "'+recuits[i].id+'" type="button" class="btn btn-default btn-xs">删除</button></td>'
								 +  '</tr>';
						}
						$("#recuitList").html(html);
						page.addClickRecuits();
						//加载分页
						if(page.getDynamicList.loadPage == false){
							page.getDynamicList.pageNum = data.result.totalPage;
							loadDevidePage(page.getDynamicList.pageNum,10,1,data.result.totalRow,page.getDynamicList);
							page.getDynamicList.loadPage = true;//分页已完成
						}
					}else{
						alert("获取班级动态列表失败，请刷新重试！");
					}
				}
			);
		}
	};
	
	//给动态列表添加点击事件
	page.addClickRecuits = function(){
		$("#recuitList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			if(obj.length === 0){
				return;
			}
			var name= obj.html();
			var val = obj.val();
			if(name === "编辑"){
				pageToNew("/Excellent/pages/recruitEditMngr.html",val);
			}else if(name === "删除"){
				alert(val);
				$.ajax({
					url:"/Excellent/news/deleteNews",
					data:{
						atyId:val
					},
					success:function(data){
						if(data.success==true){
							alert("删除成功");
							page.getDynamicList();
						}
						else{
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
})(recruitEdit);
