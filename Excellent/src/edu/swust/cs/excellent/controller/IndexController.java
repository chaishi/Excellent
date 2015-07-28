package edu.swust.cs.excellent.controller;

import org.quartz.JobExecutionException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.IocInterceptor;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.cache.MyEvictInterceptor;
import edu.swust.cs.excellent.service.inter.ILogin;
import edu.swust.cs.excellent.util.DBBackUpJob;
import edu.swust.cs.excellent.validator.TokenValidator;


@Before(IocInterceptor.class)
public class IndexController extends CommonController{

	@Inject.BY_TYPE
	private ILogin loginImpl;

	public void index(){
		System.out.println("ok");
		render("/pages/home.html");
	}

	//获得token值
	public void token(){
		createToken("Token", 30*60); //过期时间设置为30分钟
		render("/pages/login.html");
	}
	
	public void captcha()
	{
		render(new MyCaptchaRender(60,22,4,true));
	}
	@Before({
		TokenValidator.class
	})
	public void login(){
		String captcha = getPara("captcha");
		String uid  = getPara("userName");
		String pswd = getPara("pswd");
		System.out.println(pswd);
		System.out.println(uid);
		if (!MyCaptchaRender.validate(this, captcha)){
			renderError("验证码错误");
			return;
		}
		renderJ(loginImpl.login(uid, pswd,getSession()));
	}
	@Before(LoginInterceptor.class)
	public void logout(){
		renderJ(loginImpl.logout(getSession()));
	}
	public  void getUserStatus(){
		if (getSession()!=null ){
			if (getSession().getAttribute("userTye")!=null){
				renderJ("userType",getSession().getAttribute("userType"));
				return;
			}else{
				renderError("100");
				return;
			}
		}else{
			renderError("101");
			return ;
		}
	}
	//	/**
	//	 * 非常危险的操作
	//	 * 需要再次验证用户
	//	 */
	//	@Authority({
	//		Constant.AUTHORITY_ADMIN
	//	})
	//	@Before({LoginInterceptor.class,AuthorityInterceptor.class})
	//	public void getSQL(){
	//		
	//	}
}
