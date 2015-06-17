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
		renderJ("stu_detail",editStudentImpl.getDetail(getParaToInt("stdId")));
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
		int cid=getParaToInt("class_id");
		int gid=getParaToInt("group_id");
		String tName=getPara("true_name");
		String sid=getPara("school_id");
		String other=getPara("others");
		String prize=getPara("prizes");

		Student stu = new Student();
		stu.set("class_id", cid);
		stu.set("group_id",gid);
		stu.set("true_name", tName);
		stu.set("school_id", sid);
		stu.set("other", other);
		editStudentImpl.add(stu);

		Award award=new Award();
		award.set("comment", prize);
		award.set("refrence_id", stu.getInt("id"));
		editAwardImpl.add(award);
		renderJ("details",stu.getInt("id"));
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

	@Authority({
		Constant.AUTHORITY_STUDENT,Constant.AUTHORITY_ADMIN
	})
	@Before({LoginInterceptor.class,AuthorityInterceptor.class})
	public void updateStudent(){
		int id=getParaToInt("id");
		int cid=getParaToInt("class_id");
		int gid=getParaToInt("group_id");
		String tName=getPara("true_name");
		String sid=getPara("school_id");
		String other=getPara("others");
		String prize=getPara("prizes");

		Student stu = new Student();
		stu.set("id", id);
		stu.set("class_id", cid);
		stu.set("group_id",gid);
		stu.set("true_name", tName);
		stu.set("school_id", sid);
		stu.set("other", other);
		try {
			editStudentImpl.merge(stu);
		} catch (Exception e) {
			renderError();
		}

		Award award=new Award();
		award.set("comment", prize);
		award.set("refrence_id", stu.getInt("id"));
		try {
			editAwardImpl.merge(award);
		} catch (Exception e) {
			renderError();
		}
		renderJ("details",stu.getInt("id"));
	}

	public void queryStudent(){
		int id=getParaToInt("stdId");
		String name=getPara("stdName");
		int clsType;
		clsType = getParaToInt("classType",-1);

		int rowNum=getParaToInt("rowNum",10);
		int nowPage=getParaToInt("nowPage",1);
		Student stu=new Student();
		stu.set("id", id);
		stu.set("true_name", name);
		renderJ("details",editStudentImpl.queryStudent(stu,clsType , nowPage, rowNum));
	}

}
