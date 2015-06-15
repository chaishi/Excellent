/**
 * @page 审核留言管理
 * @author luoxue
 * @time 20150614
 */
$(function(){
	common.getBanner(4);
	messageMngr.loadMessages();
});

var messageMngr = {};

(function(page){
	
	//加载留言列表
	page.loadMessages = function(){
		loadDevidePage(10,5,1,{run:function(){console.log(1);}});
	}
})(messageMngr);