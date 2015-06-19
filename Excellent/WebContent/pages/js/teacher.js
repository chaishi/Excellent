$(function(){
	common.serActive(4,1);
	//teacher.setBack();
	teacher.getTeacherData();
	teacher.addclick();
});

var teacher = {};

(function(page){
	page.addclick = function(){
		$("#allTeachers").delegate('div','click',function(){
			var index = $(this).index();
			page.setActive1(index);
		});
	};
	
	page.setActive1 = function(index){
		$("#allTeachers div").each(function(i){
			if(i == index){
				$(this).addClass('activeT');
				var obj = $(this).find('span');
				var teacherInfos = obj.html().split("###");
				$("#teacherName").html(teacherInfos[0]);
				$("#teacherHead").attr({"src":teacherInfos[1]});
				$("#teacherDescrible").html(teacherInfos[2]);
			}else{
				$(this).removeClass('activeT');
			}
		});
	};
	
	//获取教师列表信息
	page.getTeacherData = function(){
		$.getJSON(
			"/Excellent/teacher/getTeacherList",
			function(data){
				if(data.success){
					var tchList = data.result.details;
					var html = "";
					for(var i = 0, len = tchList.length; i < len; i++){
						var index = (Math.random() * 12 | 0)  % 4 + 1;
						html += '<div class = "tchName'+index+'">'+tchList[i].name+'<span style = "display:none">'+tchList[i].name+'###'+tchList[i].photo+'###'+tchList[i].study_area+'</span></div>';
					}
					html += '<br style = "clear:both">';
					$("#allTeachers").html(html);
				}else{
					alert("获取教师列表失败！");
				}
			}
		);
	};
})(teacher);