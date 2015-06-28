$(function(){
	common.serActive(3,1);
	studentInfo.getStudentInfo();
});

var studentInfo = {};

(function(page){
	
	//获取学生详情
	page.getStudentInfo = function(){
		var studentdId = location.hash.split("#");
		$.getJSON(
			"/Excellent/stu/getStuInfo",
			{
				stdId:studentdId[1]
			},
			function(data){
				if(data.success){
					var std = data.result.stu_detail;
					$("#stdName").html(std.true_name);
					$("#stdId").html(std.school_id);
					$("#stdGroup").html(std.group_name);
					$("#stdClassname").html(std.classNum);
					$("#stdGraduate").html(std.other);
					var prizes = "";
					for(var i = 0, len = std.prizes.length; i < len; i++){
						prizes += "<p>"+std.prizes[i].comment+"</p>";
					}
					if(i == 0)prizes = "<p>暂无</p>";
					$("#stdPrizes").html(prizes);
					$("#stdOther").html(std.self_sign);
				}else{
					alert("获取学生详情失败！");
				}
			}
		);
	};
})(studentInfo);