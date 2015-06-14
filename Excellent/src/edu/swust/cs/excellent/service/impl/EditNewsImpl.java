package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.model.News_extend;
import edu.swust.cs.excellent.service.inter.IEditNews;

@Service("editNewsImpl")
public class EditNewsImpl implements IEditNews {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");
	
	private static final String SELECT_NEWS_DETAIL = "select title,content,author,importance,up_news,browses "
			                                          + " from news a,news_extend b"
			                                          + " where a.id=b.id and a.id=?";
	@Override
	public boolean add(News t) {
		try {
			t.save();
			logger_disk.info("新增新闻:id="+t.getInt("id"));
			return true;
		} catch (Exception e) {
			logger_disk.info("新增新闻:id="+t.getInt("id")+"失败");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			News.dao.deleteById(id);
			logger_disk.info("删除新闻:id="+id);
			return true;
		} catch (Exception e) {
			logger_disk.info("删除新闻:id="+id+"失败");
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
		if (news!=null){
			News_extend.dao.browse(id);
		}
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
		return News.dao.paginate(numPage, numPerPage, "select id,title,pub_time", " from news where classNum=? order by importance ",classNum);
	}
	
	
	@Override
	public Page<News> getList(int numPage, int numPerPage) {
		return News.dao.paginate(numPage, numPerPage, "select id,title,pub_time", " from news order by importance ");
	}

}
