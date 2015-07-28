package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.plugin.spring.IocInterceptor;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.cache.MyCacheName;
import edu.swust.cs.excellent.cache.MyEvictInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Note;
import edu.swust.cs.excellent.service.inter.IEditNote;
import edu.swust.cs.excellent.validator.WordValidator;

public class NoteController extends CommonController {
	@Inject.BY_TYPE
	IEditNote editNoteImpl;

	/**
	 * 留言
	 * flag 0 学生留言
	 *      1 教师留言
	 */
	@Authority({
		Constant.AUTHORITY_STUDENT,Constant.AUTHORITY_TEACHER,Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class
	})
	@MyCacheName("note_cache")
	public void saySomething(){
		renderJ(editNoteImpl.add(new Note().set("content", getPara("msgContent"))
				.set("noter_id", getUserId())));
	}


	public void getNotesList(){
		final int pageNum = getParaToInt("nowPage",1);
		final int numPerPage = getParaToInt("rowNum",10);
//		if (getSessionAttr("userType").equals(Constant.ADMIN)){
//			Page<Note> notePages = CacheKit.get("note_cache", "admin"+pageNum+"-"+numPerPage,
//					                       new IDataLoader(){
//				                                public Object load() {    
//					                                return editNoteImpl.getAllNoteList(pageNum,numPerPage);  
//				                           }});
//			renderP(notePages, "details");
//			return ;
//		}
		Page<Note> notePages = CacheKit.get("note_cache", pageNum+"-"+numPerPage,
                new IDataLoader(){
                     public Object load() {    
                         return editNoteImpl.getList(pageNum, numPerPage);  
                }});
		renderP(notePages, "details");
	}


	/**
	 * 留言
	 * flag 0 学生留言
	 *      1 教师留言
	 */
	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class,WordValidator.class
	})
	@MyCacheName("note_cache")
	public void deleteNote(){
		renderJ(editNoteImpl.delete(getParaToInt("note_id")));
	}

	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class,MyEvictInterceptor.class
	})
	@MyCacheName("note_cache")
	public void pass(){
		if (null==editNoteImpl.merge(Note.dao.findById(getParaToInt("note_id")))){
			renderJ(false);
			return;
		}
		renderJ(true);
	}
}
