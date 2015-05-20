package edu.swust.cs.excellent.controller;


import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;








import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.jfinal.plugin.ehcache.IDataLoader;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.cache.MyCacheName;
import edu.swust.cs.excellent.cache.MyEvictInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.service.inter.IEditNews;

import com.jfinal.plugin.spring.Inject;

public class NewsController extends CommonController {

	@Inject.BY_TYPE
	private IEditNews editNewsImpl;
	public void showClassNewsList(){
		int pageNum = getParaToInt("nowPage",1);
		int numPerPage = getParaToInt("rowNum",10);
		Page<News> page = CacheKit.get("news_cache",pageNum+"-"+numPerPage ,
				           new IDataLoader(){
			                             public Object load() {    
			                            	 return editNewsImpl.getList(pageNum,numPerPage);  
			                            	 }}); 

		//Page<News> page=editNewsImpl.getList(pageNum,numPerPage);
		renderP(page,"result");
	}

	public void showNewsDetail(){
		int id = getParaToInt("atyId");
		News news = CacheKit.get("news_cache", id) ;
		if (news == null){
			news = editNewsImpl.getDetail(id);
			CacheKit.put("news_cache", id, news);
		}
		renderJ("news_detail", news);
	}

	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class})
	@MyCacheName({"index_cache","news_cache"})
	public void writeNews(){
		renderJ(editNewsImpl.add(new News().set("title", getPara("atyTitle",""))
				.set("content", getPara("atyContent",""))
				.set("author", getName())
				.set("class_id", getPara("class_id",""))));
	}


	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class})
	@MyCacheName({"index_cache","news_cache"})
	public void mergeNews(){
		renderJ(null==editNewsImpl.merge(getModel(News.class,"news")));
	}

	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class})
	@MyCacheName({"index_cache","news_cache"})
	public void deleteNews(){
		renderJ(editNewsImpl.delete(getParaToInt("atyId")));
	}

}
