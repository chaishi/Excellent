package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.plugin.spring.IocInterceptor;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.service.inter.ILogin;

@Before(IocInterceptor.class)
public class IndexController extends CommonController{

    @Inject.BY_TYPE
	private ILogin loginImpl;
    
	public void index(){
	   System.out.println("ok");
   }
	
}
