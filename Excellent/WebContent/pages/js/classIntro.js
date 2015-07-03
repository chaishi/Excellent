$(function(){
	common.serActive(2,1);
	classIntro.addClick();
	classIntro.hideAll();
	$("#logoIntro").show();
});

var classIntro = {};

(function(page){
	
	page.addClick = function(){
		$("#classIntroNav").delegate('li','click',function(){
			var index = $(this).index();
			page.setActive1(index);
			page.showContent(index); 
		});
	};
	
	page.setActive1 = function(index){
		$("#classIntroNav li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}else{
				$(this).removeClass('active');
			}
		});
	};
	
	page.showContent = function(index){
		var navContent = $("#navContent");
		page.hideAll();
		switch(index){
		case 0 :{$("#logoIntro").show();}break;
		case 1:{page.getSoftIntro()}break;
		case 2:{page.getComputer()}break;
		case 3:{$("#trainMode").show();}break;
		case 4:{$("#teachMode").show();}break;
		case 5:{$("#companyTrain").show();}break;
		}
	};
	
	page.getSoftIntro = function(){
		$("#softEngineer").show();
		page.getEngineerIntro(0);
	};
	
	page.getComputer = function(){
		$("#computerEngineer").show();
		page.getEngineerIntro(1);
	};
	
	page.hideAll = function(){
		$("#softEngineer").hide();
		$("#computerEngineer").hide();
		$("#trainMode").hide();
		$("#teachMode").hide();
		$("#companyTrain").hide();
		$("#logoIntro").hide();
	};
	
	//获取卓越软件(或计科)工程师班级简介,flag = 0,表示卓越软件工程师班简介，flag = 1 表示卓越计科工程师班级简介
	page.getEngineerIntro = function(flag){
		$.getJSON(
			"/Excellent/class/getClassInfo",
			{
				"class":flag,
			},
			function(data){
				if(flag === 0)
					$("#softEngineer").html(data.result.content.study_model);
				else if(flag === 1)
					$("#computerEngineer").html(data.result.content.study_model);
			}
		);
	};
})(classIntro);