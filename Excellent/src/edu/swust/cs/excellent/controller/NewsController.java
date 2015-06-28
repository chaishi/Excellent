package edu.swust.cs.excellent.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;


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
import edu.swust.cs.excellent.util.HtmlUtil;

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
						String summary=getCharacter(content);
						summary=summary.substring(0,Math.min(Constant.NEWS_PREVIEW_LENGTH, summary.length()-1))+"...";
						p.put("summary",summary);
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
						String summary=getCharacter(content);
						summary=summary.substring(0,Math.min(Constant.NEWS_PREVIEW_LENGTH, summary.length()-1))+"...";
						p.put("summary",summary);
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
//		String r="";
//		String regex="([(u4e00-\\u9fa5)]+)";
//		Matcher matcher = Pattern.compile(regex).matcher(str);
//		int i=0;
//		String rr=null;
//		while (matcher.find()){
//			r+=matcher.group(0);
//		}
		str = str.replaceAll("[、.，,。？]", " ");
		return str.replaceAll("[^、 \u4e00-\u9fa5]+","");
		//return HtmlUtil.getTextFromHtml(str);
		//return r;
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
	
	
//	public static void main(String args[]){
//		String str="计算机科学与技术学院第三届卓越班OJ选拔考试须知', '<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	<strong>一、&nbsp;<span class=\"Apple-converted-space\">&nbsp;</span></strong><strong>考试地点 ：</strong>\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	由于实验室容量有限，特将同学们分配到 东六A317和东六A319两个实验室， 请同学们自己下载名单, 确认自己所在的考试地点。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	<strong>二、&nbsp;<span class=\"Apple-converted-space\">&nbsp;</span></strong><strong>考试时间 ：</strong>\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	所有考生统一在5月20日中午12:30 ~ 17:30进行考试，请同学们提前15分钟抵达考场，迟到30分钟不允许进入考场，完成答题可提前离开。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	<strong>三、考试规则 ：</strong>\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;&nbsp; &nbsp;（1）迟到30分钟不允许进入考场，可提前交卷。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;&nbsp; &nbsp;（2）请每位同学携带 身份证 和 校园一卡通 进入考场。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;&nbsp; &nbsp;（3）考试期间不允许相互交谈，一经发现，立刻取消考试资格。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;&nbsp;&nbsp; （4）考试只可携带《C程序设计》（谭浩强 第四版），不允许携带其它参考资料。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	<strong>四、录取：</strong>\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	此次机考完毕后录取成绩排名前64名进入下一轮面试环节。\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;2012.5.17\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	&nbsp;\n</p>\n<p style=\"font-size:14.3999996185303px;color:#404040;text-indent:2em;font-family:Arial, Verdana, Helvetica, Arial, sans-serif;font-style:normal;font-weight:normal;text-align:start;background:#FFFFFF;\">\n	<a href=\"http://www.cs.swust.edu.cn/uploads/%E7%AC%AC%E4%B8%89%E5%B1%8A%E5%8D%93%E8%B6%8A%E7%8F%ADOJ%E8%80%83%E8%AF%95%E5%AE%89%E6%8E%92.xls\">考试名单及地点安排</a>\n</p>\n<br />";
//		System.out.println(new NewsController().getCharacter(str));
//	}
	
}
