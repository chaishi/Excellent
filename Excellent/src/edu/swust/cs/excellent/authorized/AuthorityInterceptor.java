package edu.swust.cs.excellent.authorized;

import java.util.HashMap;


import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;



/**
 * @author moulaotou
 * 权限拦截器
 */
public class AuthorityInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		Authority auth = ai.getMethod().getAnnotation(Authority.class);
		if (auth==null){
			auth = ai.getController().getClass().getAnnotation(Authority.class);
		}
		String s =  (String) ai.getController().getSession().getAttribute("userType");
		
		boolean flag = false;
		String[] v = auth.value();
		for (String p:v){
			if (p.equals(s)){
				flag  = true;
			}
		}
		
		if (!flag){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("error", "没有权限");
			map.put("userType", ai.getController().getSession().getAttribute("userType"));
			ai.getController().renderJson(map);
		}else{
			ai.invoke();
		}
	}

}
