package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.model.Teacher;
import edu.swust.cs.excellent.service.inter.IEditTeacher;



public class TeacherController extends CommonController{
	 @Inject.BY_NAME
	private IEditTeacher editTeacherImpl;
	
	@Authority({
	 		Constant.AUTHORITY_ADMIN
	 	})
	 	@Before({
	 		LoginInterceptor.class,AuthorityInterceptor.class
	 	})
	 public void newTeacher(){
		if (editTeacherImpl.add(getModel(Teacher.class,"t"))){
		 renderJ();
		}else {
			renderError("添加教师失败");
		}
	 }
	
	public void getTeacherList(){
		int pageNum = getParaToInt("nowPage",1);
		int numPerPage = getParaToInt("rowNum",10000);
		Page<Teacher> page = CacheKit.get("teacher_cache",pageNum+"-"+numPerPage ,
				new IDataLoader(){
			public Object load() {    
				return editTeacherImpl.getList(pageNum,numPerPage);  
			}}); 
		renderP(page,"details");
	}
	
	@Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
	public void deleteTeacher(){
		if (editTeacherImpl.delete(getParaToInt("teacherId"))){
			renderJ();
		}else {
			renderError("删除老师失败");
		}
	}
}

