
var common = {};

(function(page){
	page.getBanner = function(i){
		$.ajax({
			url:'bannerMngr.html',
			type:"get",
			dataType:'html',
			success:function(data){
				$("#banner").html(data);
				page.serActive(i);
			}
		});
	}
	
	page.serActive = function(index){
		$("#barLink li").each(function(i){
			if(i == index){
				$(this).addClass('active');
			}
		});
	};
})(common);