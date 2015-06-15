$(function(){
	common.getBanner(2);
	studentInfoMngr.delegateEdit();
	studentInfoMngr.addCickToNav();
});

var studentInfoMngr = {};

(function(page){
	
	//事件代理：表格，当点击编辑按钮时
	page.delegateEdit = function(){
		$("#studentList").delegate('td','click',function(){
			var obj = $($(this).find('button'));
			var name= obj.html();
			var val = obj.val();
			if(name === "编辑"){
				//alert(val);
			}else if(name === "删除"){
				alert(val);
			}
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
	
	//选择左边哪一项，右边对应显示哪一项
	page.showContent = function(i){
		var addStudent = $("#addStudent");
		var delStudent = $("#delStudent");
		switch(i){
		case 0:{
			addStudent.show();
			delStudent.hide();
		}break;
		case 1:{
			addStudent.hide();
			delStudent.show();
		}break;
		case 2:{
			addStudent.hide();
			delStudent.hide();
		}break;
		}
	}
})(studentInfoMngr);