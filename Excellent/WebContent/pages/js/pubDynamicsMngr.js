$(function(){
	common.getBanner(0);
	classIntroMngr.pubNews();
});

var classIntroMngr = {};

(function(page){
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			allowFileManager : true
		});
		/*K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		K('input[name=isEmpty]').click(function(e) {
			alert(editor.isEmpty());
		});
		K('input[name=getText]').click(function(e) {
			alert(editor.text());
		});
		K('input[name=selectedHtml]').click(function(e) {
			alert(editor.selectedHtml());
		});
		K('input[name=setHtml]').click(function(e) {
			editor.html('<h3>Hello KindEditor</h3>');
		});
		K('input[name=setText]').click(function(e) {
			editor.text('<h3>Hello KindEditor</h3>');
		});
		K('input[name=insertHtml]').click(function(e) {
			editor.insertHtml('<strong>插入HTML</strong>');
		});
		K('input[name=appendHtml]').click(function(e) {
			editor.appendHtml('<strong>添加HTML</strong>');
		});
		K('input[name=clear]').click(function(e) {
			editor.html('');
		});*/
	});
	
	//获取班级列表
	page.getClasses = function(){
		var html = "";
		//$("#classSelect").html(html);
	}
	
	//发布动态
	page.pubNews = function(){
		$("#pubNews").click(function(){
			var title = $("#title").val();
			var content = editor.html();
			var clas = $("#classSelect").val();
			if(title == "" || content == "" || clas == ""){
				alert("请完善信息！");
				return;
			}
			console.log(title,content,clas);
		});
	};
	
})(classIntroMngr);