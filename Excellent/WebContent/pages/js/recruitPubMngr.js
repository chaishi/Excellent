$(function(){
	common.serActive(3);
	common.addCickToNav(recruitEdit.showContent);
	recruitEdit.getEditor();
	recruitEdit.pubRecuit();
});

var recruitEdit = {};

(function(page){
	var editor;
	page.getEditor = function(){
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true
			});
		});
	};
	
	page.showContent = function(i){
		var pubRecuit = $("#pubRecuit");
		var delEditRecuit = $("#delEditRecuit");
		switch(i){
		case 0:{
			pubRecuit.show();
			delEditRecuit.hide();
		}break;
		case 1:{
			pubRecuit.hide();
			delEditRecuit.show();
		}break;
		}
	};
	
	//发布招新信息
	page.pubRecuit = function(){
		$("#pubRecuitBtn").click(function(){
			var title = $("#title").val();
			var content = editor.html();
			if(title == "" || content == "" || clas == ""){
				alert("请完善信息！");
				return;
			}
			console.log(title,content);
		});
	};
})(recruitEdit);
