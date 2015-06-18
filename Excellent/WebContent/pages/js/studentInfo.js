$(function(){
	common.serActive(3,1);
	studentInfo.setBack();
});

var studentInfo = {};
(function(page){
	page.setBack = function(){
		$(".indentCenter2").height(window.innerHeight);
		console.log(window.innerHeight);
	};
})(studentInfo);