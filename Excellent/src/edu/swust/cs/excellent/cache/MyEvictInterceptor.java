package edu.swust.cs.excellent.cache;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.plugin.ehcache.CacheKit;


public class MyEvictInterceptor implements Interceptor {

	final public void intercept(ActionInvocation ai) {
		ai.invoke();
		Map<Integer,String[]> map = buildCacheName(ai);

		//若非MycacaheNamekey
		String[] cacheNames = map.get(1);
		if (cacheNames!=null){
			for (String p:cacheNames){
				CacheKit.removeAll(p);
			}
		}

		String[] cahceNameKeys =  map.get(2);
		if (cahceNameKeys!=null && cahceNameKeys.length%2!=0) {
			throw new 
			RuntimeException("MyEvictInterceptor MyCahceNameKey  annotation keys shoud be even account.");
		}else if (cahceNameKeys!=null && cacheNames!=null){
			for (int i=0;i<cacheNames.length;i+=2){
				CacheKit.remove(cacheNames[i],cacheNames[i+1]);
			}
		}
	}

	private Map<Integer,String[]> buildCacheName(ActionInvocation ai) {

		Map<Integer,String[]> map = new HashMap<Integer,String[]>();
		MyCaChaNameKey cacheNameKey = ai.getController().getClass().getAnnotation(MyCaChaNameKey.class);
		if (cacheNameKey != null)
			map.put(1, cacheNameKey.value());
		cacheNameKey = ai.getMethod().getAnnotation(MyCaChaNameKey.class);
		if (cacheNameKey != null)
			map.put(1, cacheNameKey.value());


		MyCacheName cacheName = ai.getController().getClass().getAnnotation(MyCacheName.class);
		if (cacheName != null)
			map.put(2, cacheName.value());
		cacheName = ai.getMethod().getAnnotation(MyCacheName.class);
		if (cacheName != null)
			map.put(2, cacheName.value());

		if (cacheNameKey==null && cacheName==null)
			throw new
			RuntimeException("MyEvictInterceptor shoud have MyCahceNameKey or CahceNameKey annotation keys");
		return map;		 
	}
}
