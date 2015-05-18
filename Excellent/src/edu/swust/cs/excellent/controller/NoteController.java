package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.IocInterceptor;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Note;
import edu.swust.cs.excellent.service.inter.IEditNote;

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
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void saySomething(){
		renderJ(editNoteImpl.add(new Note().set("content", getPara("msgContent"))
				            .set("noter_id", getUserId())));
	}


	public void getNotesList(){
		if (getSessionAttr("userType").equals(Constant.ADMIN)){
			renderP(editNoteImpl.getAllNoteList(getParaToInt("nowPage",1), getParaToInt("rowNum",10)), "result");
			return ;
		}
         renderP(editNoteImpl.getList(getParaToInt("nowPage",1), getParaToInt("rowNum",10)), "result");
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
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void deleteNote(){
		renderJ(editNoteImpl.delete(getParaToInt("note_id")));
	}
	
	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void pass(){
		if (null==editNoteImpl.merge(Note.dao.findById(getParaToInt("note_id")))){
			renderJ(false);
			return;
		}
		renderJ(true);
	}
}
