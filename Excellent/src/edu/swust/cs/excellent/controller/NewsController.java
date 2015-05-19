package edu.swust.cs.excellent.controller;


import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;








import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.service.inter.IEditNews;

import com.jfinal.plugin.spring.Inject;

public class NewsController extends CommonController {
   
	@Inject.BY_TYPE
	private IEditNews editNewsImpl;
	public void showClassNewsList(){
		Page<News> page=editNewsImpl.getList(getParaToInt("nowPage",1),getParaToInt("rowNum",10));
        renderP(page,"result");
	}
	
	public void showNewsDetail(){
		News news = editNewsImpl.getDetail(getParaToInt("atyId"));
	    renderJ("news_detail", news);
	}
	
	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class})
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
	@Before({LoginInterceptor.class,AuthorityInterceptor.class})
	public void mergeNews(){
		renderJ(null==editNewsImpl.merge(getModel(News.class,"news")));
	}
	
	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class})
	public void deleteNews(){
		renderJ(editNewsImpl.delete(getParaToInt("atyId")));
	}
	
}
