package edu.swust.cs.excellent.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import edu.swust.cs.excellent.model.News_extend;
import edu.swust.cs.excellent.service.inter.IEditNews;

import com.jfinal.plugin.spring.Inject;

public class NewsController extends CommonController {

	@Inject.BY_TYPE
	private IEditNews editNewsImpl;
	public void showClassNewsList(){
		int pageNum = getParaToInt("nowPage",1);
		int numPerPage = getParaToInt("rowNum",10);
		String classNum=getPara("calssNum","");
		int type = getParaToInt("type",1);
		String para=type+classNum;
		if (type==1){
			Page<News> page = CacheKit.get("news_cache",type+"-"+pageNum+"-"+numPerPage ,
					new IDataLoader(){
				public Object load() {    
					Page<News> news = editNewsImpl.getList(pageNum,numPerPage);  
					if (news==null)
						return null;
					for (News p:news.getList()){
						String  content=p.getStr("content");
						if (content.trim().equals(""))
							continue;
						String summary=content.substring(0,Math.min(Constant.NEWS_PREVIEW_LENGTH, content.length()-1))+"...";
						p.put("summary",getCharacter(summary));
						p.remove("content");
					}
					return news;
				}}); 

			//Page<News> page=editNewsImpl.getList(pageNum,numPerPage);
			renderP(page,"details");
		}else {
			Page<News> page = CacheKit.get("news_cache",type+"-"+pageNum+"-"+numPerPage ,
					new IDataLoader(){
				public Object load() {    
					Page<News> news = editNewsImpl.getList(pageNum,numPerPage,para);
					if (news==null)
						return null;
					for (News p:news.getList()){
						String  content=p.getStr("content");
						if (content.trim().equals(""))
							continue;
						String summary=content.substring(0,Math.min(Constant.NEWS_PREVIEW_LENGTH, content.length()-1))+"...";
						p.put("summary",getCharacter(summary));
						p.remove("content");
					}
					return news;
				}}); 

			//Page<News> page=editNewsImpl.getList(pageNum,numPerPage);
			renderP(page,"details");
		}
	}

	private String getCharacter(String str){
		if (str==null || str.equals(""))
			return "";
		String r="";
		String regex="([\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if(matcher.find()){
			r+=matcher.group(0);
		}
		return r;
	}
	
	public void showNewsDetail(){
		int id = getParaToInt("atyId");
		try {
			News_extend.dao.browse(id);
		} catch (Exception e) {
			//means 该新闻已经删除
			renderError("该动态已被删除");
			return;
		}
		News news = CacheKit.get("news_cache", id) ;
		if (news == null){
			news = editNewsImpl.getDetail(id);
			CacheKit.put("news_cache", id, news);
		}
		News_extend ne=News_extend.dao.findById(id);

		news.put("browses", ne.getInt("browses"));
		news.put("up_news", ne.getInt("up_news"));

		renderJ("details", news);
	}

	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class})
	@MyCacheName({"index_cache","news_cache"})
	public void writeNews(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String time = format.format(calendar.getTime());
		News  news = new News().set("title", getPara("title",""))
				.set("type", getParaToInt("type",1))
				.set("content", getPara("content",""))
				.set("author", getName())
				.set("happen_time", getPara("happen_time",time))
				.set("class_id", getPara("class_id","1"));
		boolean r=editNewsImpl.add(news);
		if  (r){
			add("id",news.getInt("id"));
			renderJ();
		}else{
			renderError("添加失败:"+editNewsImpl.getLastError());
		}
	}


	@Authority({
		Constant.AUTHORITY_ADMIN,Constant.AUTHORITY_MONITOR,Constant.AUTHORITY_SECRETARY,
		Constant.AUTHORITY_LIFE_REP,Constant.AUTHORITY_ACADEMIC_REP,Constant.AUTHORITY_SPORTS_REP
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class})
	@MyCacheName({"index_cache","news_cache"})
	public void mergeNews(){
		renderJ(null!=editNewsImpl.merge(getModel(News.class,"news")));
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
