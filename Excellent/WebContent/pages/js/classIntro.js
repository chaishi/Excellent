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
	};
	
	page.getComputer = function(){
		$("#computerEngineer").show();
	};
	
	page.hideAll = function(){
		$("#softEngineer").hide();
		$("#computerEngineer").hide();
		$("#trainMode").hide();
		$("#teachMode").hide();
		$("#companyTrain").hide();
		$("#logoIntro").hide();
	};
})(classIntro);