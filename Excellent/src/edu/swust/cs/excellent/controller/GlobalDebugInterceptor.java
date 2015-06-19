package edu.swust.cs.excellent.controller;

import java.util.Calendar;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class GlobalDebugInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		System.out.println("===========Excllent=============");
		System.out.println(Calendar.getInstance().getTime());
		System.out.println("URL:  "+ai.getControllerKey());
		System.out.println("Method:  "+ai.getMethodName());
		System.out.println("Param:  "+ai.getController().getPara());
		ai.invoke();
	    System.out.println("================================");
	    System.out.println("");
	}

}
