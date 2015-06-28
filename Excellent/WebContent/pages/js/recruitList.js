$(function(){
	common.serActive(5,1);
	recruitList.getDynamicList.run(1);
});

var recruitList = {};

(function(page){
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
				  				 +	'<td class="timeWidth">'+recuits[i].happen_time+'</td>'
								 +  '</tr>';
						}
						$("#recruitTbl").html(html);
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
	
})(recruitList);