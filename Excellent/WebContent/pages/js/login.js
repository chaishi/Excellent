/**
 * login
 * @author sunmengxin,lixin
 * @time 2015-07-12
 */

$(function(){
	/*按ESC键退出*/
	$("#myModal").modal({
        keyboard: true,
        show:false      
     });
	
	$("#login").on('click',function(){
		
		//用户名，密码，验证码
		var userName = null;
		var pswd = null;
		
		userName = $("#user").val();
		pswd = $("#password").val();
		if(userName =="" || userName == null) {
			alert("请输入用户名！");
			$("#user").focus();
		}else if( pswd == "" || pswd==null){
			alert("请输入密码！");
			$("password").focus();
		}
       // var pswd = hex_sha1($("#password").val());
		
		var captcha = $("#input1").val();
		//console.log(userName+"@@@@"+pswd);
		$.ajax({
			url:"/Excellent/login",
			type:"post",
			data:{
				"userName":userName,
				"pswd":pswd,
				"captcha":captcha
			},
			dataType:'json',
			success:function(data){
				console.log(data);
				//console.log(userName+"@@@@"+pswd);
				if(data.success === true){
					alert("登录成功！");
					$("#myModal").modal("hide");
					$("#login_ul span").text("退出");
					
				}else{
					//console.log(userName+"@@@@"+pswd);
					alert("登陆失败！");
				}
			},
			error:function(){
				alert("请求失败！");
			}
		});
	})
	$("#recheckcode").on('click',function(){
		//验证码看不清
		$("#check_img").attr("src","/Excellent/captcha");
	})
	
});


