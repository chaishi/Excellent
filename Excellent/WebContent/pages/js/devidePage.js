var g_nowGroup = 1;//当前页批次
var flagDelegate = false;//是否已经添加事件代理

/**
 * @author luoxue
 * @param {totalPage:总页数; avgNum:每一批显示多少页数;page:当前批; totalNum:总条数; fun: 点击页号时，待执行的函数}
 * @return {}
 * @notes 函数功能：获取分页
 */
function loadDevidePage(totalPage,avgNum,page,totalNum,fun){
	//创建一个ul
  	var ul = $("<ul></ul>");
  	//为ul添加内容
  	ul.append( $("<li class='last'><a><span aria-hidden='true'>&laquo;</span></a></li>") );
  	var start = (page - 1) * avgNum + 1;
  	if(start <= totalPage && start > 0){
  		for(i = start; i < start + avgNum; i++){
	  		var lis = $("<li class='pageNum'></li>");
	  		var a = $("<a>"+i+"</a>");
	  		lis.append(a);
	  		ul.append(lis);
	  		if(i >= totalPage)
	  			break;
	  	}
  	}else if(totalPage == 0){   //没有内容的时候
  			var lis = $("<li class='pageNum'><a>"+1+"</a></li>");
	  		ul.append(lis);
  	}else{
  		return;
  	}
  	ul.append( $("<li class='next'><a><span aria-hidden='true'>&raquo;</span></a></li>") );
  	var totalNumHtml = "<span class = 'totalNum'>共  <span id = 'totalNum'>"+totalNum+"</span> 条记录</span> ";
  	$("#devidePage").empty();
  	$("#devidePage").append(totalNumHtml);
  	$("#devidePage").append(ul);
  	$("#devidePage").append("<br style = 'clear:left'>");
  	//添加css样式
  	$("#devidePage ul").addClass("pagination");
  	somePageClick(fun);
  	lastAndNext(totalPage,avgNum,totalNum,fun);
}
/**
 * @author luoxue
 * @param {function} fun 点击后待执行的函数
 * @return {}
 * @notes:当点击某个页面页号时
 */
function somePageClick(fun){
	if(flagDelegate)return;
	flagDelegate = true;
	$("#devidePage").delegate(".pageNum","click",function(){
		var num = $(this).index();
		setActive(num);
		fun.run($($(this).children()).html());
	});
}

/**
 * @author luoxue
 * @param {Object} index 被点击页号的下标
 * @return {}
 * @notes:当某个页号被点击时，将其背景色设置为蓝色
 */
function setActive(index){
	$("#devidePage li").each(function(i){
		if(i == index){
			$(this).addClass("active");
		}else{
			$(this).removeClass("active");
		}
	});
}
 
/**
 * @author luoxue
 * @param {number} totalPage 总页数
 * @param {number} avgNum 每一个批多少页
 * @param {number} fun 点击页号待执行的函数
 * @return {}
 * @notes:添加点击事件,上一批或者下一批页号
 */
function lastAndNext(totalPage,avgNum,totalNum,fun){
	var lis = $("#devidePage .pageNum");
	$(lis[0]).addClass("active");
	$("#devidePage .last").click(function(){
		var obj = $("#devidePage ul").find("active");
		console.log(obj.html());
		//alert(fun.run($(obj.children()).html()));
		if(g_nowGroup - 1 > 0){
			g_nowGroup--;
			loadDevidePage(totalPage,avgNum,g_nowGroup,totalNum,fun);
		}
	});
	$("#devidePage .next").click(function(){
		var obj = $("#devidePage ul").children();
		console.log(obj.html());
		//alert(fun.run($(obj.children()).html()));
		if(g_nowGroup + 1 < totalPage){
			g_nowGroup++;
			loadDevidePage(totalPage,avgNum,g_nowGroup,totalNum,fun);
		}
	});
}



