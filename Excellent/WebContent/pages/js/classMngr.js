$(function(){
	common.getBanner(1);
	classMngr.getEditor();
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
		$("#navLeft").delegate('li','click',function(){
			var index = $(this).index();
			page.setActiveNav(index);
			page.showContent(index);
		});
	}
	
	//为侧边栏设置active
	page.setActiveNav = function(index){
		$("#navLeft li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}else{
				$(this).removeClass('active');
			}
		});
	}
	
	page.showContent = function(i){
		var classIntro = $("#classIntro");
		var classAddDel = $("#classAddDel");
		var classGroup = $("#classGroup");
		switch(i){
		case 0:{
			classIntro.show();
			classAddDel.hide();
			classGroup.hide();
		}break;
		case 1:{
			classIntro.hide();
			classAddDel.show();
			classGroup.hide();
		}break;
		case 2:{
			classIntro.hide();
			classAddDel.hide();
			classGroup.show();
		}break;
		}
	}
	
})(classMngr);