/**
 * login
 * @author sunmengxin,lixin
 * @time 2015-07-27
 */

$(function(){
	
	$("#loginBtn").on('click',function(){
		
		$("#loginBtn").attr("disabled",true);
		//用户名，密码，验证码
		var userName = null;
		var pswd = null;
		var captcha = null;
		
		userName = $("#user").val();
		pswd = $("#password").val();
		captcha = $("#captcha").val();
		
		if(userName =="" || userName == null  ) {
			alert("请输入用户名！");
			$("#user").focus();
			return ;
		}else if( pswd == "" || pswd==null){
			alert("请输入密码！");
			$("#password").focus();
			return ;
		}else if(captcha == "" || captcha==null){
			alert("请输入验证码！");
			$("#captcha").focus();
			return ;
		}
       // var pswd = hex_sha1($("#password").val());		
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
				
				if(data.success === true){
					$("#loginBtn").attr("disabled",false);
					alert("登录成功！");
				    location.href="/Excellent/pages/classMngr.html";					
				}else{					
					alert("登陆失败！");
				}
			},
			error:function(){
				alert("网络超时！");
			}
		});
	})
	//验证码看不清
	$("#recheckcode").on('click',function(){		
		$("#check_img").attr("src","/Excellent/captcha?"+Math.random());
	});
	
	
});


