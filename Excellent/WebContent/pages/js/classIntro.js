$(function(){
	common.serActive(2,1);
	classIntro.addClick();
});

var classIntro = {};

(function(page){
	
	page.addClick = function(){
		var flag1 = 0;
		$("#guideSoft").click(function(){
			$("#guideSoft").addClass('active');
			$("#guideCom").removeClass('active');
			if(flag1 === 0){
				page.getSoftIntro();
				flag1 = 1;
			}
		});
		
		var flag2 = 0;
		$("#guideCom").click(function(){
			$("#guideSoft").removeClass('active');
			$("#guideCom").addClass('active');
			if(flag2 === 0){
				page.getComputer();
				flag2 = 1;
			}
		});
	};
	
	page.getSoftIntro = function(){
		
	};
	
	page.getComputer = function(){
		
	};
})(classIntro);