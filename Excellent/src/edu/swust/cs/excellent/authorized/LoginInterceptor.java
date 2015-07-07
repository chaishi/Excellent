package edu.swust.cs.excellent.authorized;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class LoginInterceptor implements Interceptor {

	/**
	 * 权限拦截器，该拦截器必须结合@Authority注解使用
	 */
	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		Object obj = ai.getController().getSession().getAttribute("user");
		if (obj == null) {
			ai.getController().render("/pages/html/loginMngr.html");
		}
		ai.invoke();
	}

}
