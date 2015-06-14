$(function(){
	common.getBanner(1);
	classMngr.getEditor();
	classMngr.setActiveNav(0);
	classMngr.addCickToNav();
});

var classMngr = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
		});
	};
	
	//为侧边栏添加点击事件
	page.addCickToNav = function(){
		$("#navLeft").delegate('div','click',function(){
			page.setActiveNav($(this).index());
		});
	}
	
	//为侧边栏设置active
	page.setActiveNav = function(index){
		$("#navLeft div").each(function(i){
			if(i == index){
				$(this).addClass('activeNav');
			}else{
				$(this).removeClass('activeNav');
			}
		});
	}
	
})(classMngr);