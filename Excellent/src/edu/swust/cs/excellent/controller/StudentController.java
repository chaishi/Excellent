package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Award;
import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.service.inter.IEditAward;
import edu.swust.cs.excellent.service.inter.IEditStudent;


public class StudentController extends CommonController {
  @Inject.BY_NAME
  IEditStudent editStudentImpl;
  @Inject.BY_NAME
  IEditAward editAwardImpl;
  
  public void getStuList(){
	  renderJ("stu_list",editStudentImpl.getStuList(getParaToInt("class_id")));
  }
  
  public void getStuInfo(){
	  renderJ("stu_detail",editStudentImpl.getDetail(getParaToInt()));
  }
  
   @Authority({
	   Constant.AUTHORITY_STUDENT
   })
   @Before({LoginInterceptor.class,AuthorityInterceptor.class})
  public void addStuPrize(){
	  String id = getPara("id");
	  if (!(getUserId().equals(id))){
		  renderError("只能添加自己的获奖情况");
		  return ;
	  }
	 String award = getPara("award","");
	 String comment = getPara("comment","");
	 String level = getPara("level",getPara("level"));
	 renderJ(editAwardImpl.add(new Award().set("award", award)
			                      .set("comment", comment)     
			                      .set("level", level)
			                      .set("flag", getFlag())));
  }

   @Authority({
	   Constant.AUTHORITY_STUDENT
   })
   @Before({LoginInterceptor.class,AuthorityInterceptor.class})
  public void mergeStuPrize(){
	  String id = getPara("id");
	  if (!(getUserId().equals(id))){
		  renderError("只能修改自己的获奖情况");
		  return ;
	  }
	 
	 renderJ(null==editAwardImpl.merge(getModel(Award.class,"award")));
  }

   
   @Authority({
	   Constant.AUTHORITY_STUDENT
   })
   @Before({LoginInterceptor.class,AuthorityInterceptor.class})
  public void deleteStuPrize(){
	  String id = getPara("id");
	  if (!(getUserId().equals(id))){
		  renderError("只能删除自己的获奖情况");
		  return ;
	  }
	 renderJ(editAwardImpl.delete(Integer.parseInt(id)));
  }
   
   @Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
   public void newStudent(){
	   Student stu = getModel(Student.class,"stu");
	   renderJ(editStudentImpl.add(stu));
   }
   
   @Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
   public void deleteStudent(){
	   renderJ(editStudentImpl.delete(getParaToInt("id")));
   }
}
