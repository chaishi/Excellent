package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.model.News_extend;
import edu.swust.cs.excellent.service.inter.IEditNews;

@Service("editNewsImpl")
public class EditNewsImpl extends BaseImpl implements IEditNews {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");

	private static final String SELECT_NEWS_DETAIL = "select title,classNum,content,happen_time,pub_time,author,importance "
			+ " from news a,class c"
			+ " where  a.class_id=c.id and a.id=?";
	@Override
	public boolean add(News t) {
		try {
			t.save();
			News_extend ne=new News_extend();
			ne.set("id", t.getInt("id"))
			  .set("browses", 0)
			  .set("up_news", 0).save();
			logger_disk.info("新增新闻:id="+t.getInt("id"));
			return true;
		} catch (Exception e) {
			logger_disk.error("新增新闻:id="+t.getInt("id")+"失败");
			logger_disk_we.error("新增新闻:id="+t.getInt("id")+"失败");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			News_extend.dao.deleteById(id);
			News.dao.deleteById(id);
			logger_disk.info("删除新闻:id="+id);
			return true;
		} catch (Exception e) {
			logger_disk.info("删除新闻:id="+id+"失败");
			logger_disk_we.error("删除新闻:id="+id+"失败");
		}
		return false;
	}

	@Override
	public News merge(News t) {
		try {
			t.update();
			logger_disk.info("修改新闻:id="+t.getInt("id"));
			return t;
		} catch (Exception e) {
			logger_disk.warn("修改新闻:id="+t.getInt("id"));
			logger_disk_we.warn("修改新闻:id="+t.getInt("id"));
		}
		return null;
	}

	@Override
	public Page<News> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News getDetail(int id) {
		News news = News.dao.findFirst(SELECT_NEWS_DETAIL,id);
//		if (news!=null){
//			News_extend.dao.browse(id);
//		}
		return news;
	}

	@Override
	public boolean up_News() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean end_up_News() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commentNews(int newId, String comment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComment(int commentId, boolean p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Page<News> getList(int numPage, int numPerPage,String classNum) {
		String cls="";
		String sql="";
		if (classNum.trim().startsWith("1")){
			cls=classNum.substring(1,classNum.length()-1);
			if (cls.trim().equals(""))
				return getList(numPage, numPerPage);
			sql=" from news where type=2 and classNum like '%"+cls
					+ "%' order by importance,happen_time desc";
			return News.dao.paginate(numPage, numPerPage, "select id,title,pub_time,happen_time,content", sql);
		}
		//else
		if (classNum.trim().startsWith("2")){
			if (classNum.trim().length()!=1)
			   cls=classNum.substring(1,classNum.length()-1);
			if (cls.trim().equals("")){
				return  News.dao.paginate(numPage, numPerPage, "select id,title,pub_time,happen_time,content", " from news where type=2 order by importance,happen_time ");
			}
			sql=" from news where type=1 and classNum like '%"+cls
					+ "%' order by happen_time desc,importance ";
			return News.dao.paginate(numPage, numPerPage,"select id,title,pub_time,happen_time,content",sql);
		}
		return null;
	}


	@Override
	public Page<News> getList(int numPage, int numPerPage) {
		return News.dao.paginate(numPage, numPerPage, "select id,title,pub_time,content,happen_time", " from news order by importance,happen_time desc");
	}



}
