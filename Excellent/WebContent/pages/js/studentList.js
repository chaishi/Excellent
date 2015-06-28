$(function(){
	common.serActive(3,1);
	common.getClasses(["#classIdforstd"],studentList.getStudentList);//班级列表加载完成后，自动获取第一个班级的学生信息列表
});

var studentList = {};

(function(page){
	
	page.addClick = function(){
		$("#stdSearch").click(function(){
			var classId = $("#classIdforstd").val();//获取选择的班级Id
			page.getStudentList(classId);
		});
	};
	
	//根据班级Id获取学生信息列表
	page.getStudentList = function(classId){
		$.getJSON(
			"/Excellent/stu/queryStudent",
			{
				stdId:20121179,
				stdName:"罗雪",
				classId:classId
			},
			function(data){
				if(data.success === true){
					
				}else{
					alert("获取学生信息列表失败！");
				}
			}
		);
	};
})(studentList);