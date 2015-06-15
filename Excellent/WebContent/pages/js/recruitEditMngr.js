$(function(){
	common.getBanner(3);
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
	
	//发布招新信息
	page.pubRecuit = function(){
		$("#pubRecuit").click(function(){
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
