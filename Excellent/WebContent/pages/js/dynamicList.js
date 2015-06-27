$(function(){
	common.serActive(1,1);
	dynamicList.getDynamicList.run(1);
});

var dynamicList = {};

(function(page){
	//获取班级动态列表
	page.getDynamicList = {
		rowNum:15,
		pageNum:1,
		loadPage:false,
		run:function(index){
			$.getJSON(
				"/Excellent/news/showClassNewsList",
				{
					rowNum:this.rowNum,
					nowPage:index
				},
				function(data){
					if(data.success === true){
						var dymList = data.result.details;
						var html = "";
						var url = "/Excellent/pages/dynamicInfo.html";
						for(var i = 0,len = dymList.length; i < len; i++){
							html += '<tr><td class = "newsHover" onclick = "pageToNew(\''+url+'\','+dymList[i].id+')">'+dymList[i].title+'</td><td>'+dymList[i].happen_time+'</td></tr>';
						}
						$("#dynamicsTbl").html(html);
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
	
})(dynamicList);