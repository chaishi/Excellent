package edu.swust.cs.excellent.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;
import edu.swust.cs.excellent.service.inter.IEditClass;
import edu.swust.cs.excellent.validator.ClassValidator;

public class ClassController extends CommonController{
	@Inject.BY_TYPE
	IEditClass editClassImpl;

	/**
	 * 0 软件
	 * 1 计科
	 */
	public void getClassInfo(){
		add("content",editClassImpl.getDetail(getParaToInt("class")));
		renderJ();
	}


	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void getClassList(){
		renderJ("class_list",editClassImpl.getList(getParaToInt("nowPage",1), getParaToInt("rowNum",10)));
	}


	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void setClassInfo(){
		Class cls = new Class().set("study_model", getPara("introContent",""));
		if (getParaToInt("classType",-1).equals(0)){
			renderJ(editClassImpl.merge(cls, true));   
		}else if (getParaToInt("classType",-1).equals(1)){
			renderJ(editClassImpl.merge(cls, false));
		}
		renderError("请指明班级类型");
	}

	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class,ClassValidator.class
	})
	public void newClass(){
		if (editClassImpl.add(new Class().set("classNum", getPara("className"))
				.set("study_model",getPara("study_model","")))){
			renderJ(true);
		}else{
			renderError(editClassImpl.getLastError());
		}
	}
	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void deleteClass(){
		renderJ(editClassImpl.delete(getParaToInt("classId")));
	}

	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void addGroup(){
		Group group = getModel(Group.class,"group");

		//    	Group group = new Group().set("group_name",getPara("groupName")).
		//    			set("leader",getParaToInt("leader",0)).
		//    			set("slogan",getPara("slogan","")).
		//    			set("flags",getPara("flags","")).
		//    			set("tips",getPara("tips","")).
		//    			set("class_id",getParaToInt("class_id")).
		//    			set("achieve",getPara("achieve"));
		if (editClassImpl.addGroup(group)){
			renderJ(true);
		}else{
			renderError(editClassImpl.getLastError());
		}
	}

	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void deleteGroup(){
		renderJ(editClassImpl.deleteGroup(getParaToInt("id")));
	}

	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void  recordGroupFromExcel(){
		int classNum;
		try {
			classNum = getParaToInt("id");
		} catch (Exception e1) {
			renderError("请选择班级");
			return;
		}
		try {
			renderJ("details",editClassImpl.recordFromExcel(new File(getPara("filePath")),classNum));
		}  catch (IOException e) {
			renderError("文件不存在或者已经过期,请重新上传再试\n请下载使用标准格式:)");
		}
	}
	@Authority({
		Constant.AUTHORITY_ADMIN
	})
	@Before({
		LoginInterceptor.class,AuthorityInterceptor.class
	})
	public void exportGroupToExcel(){
		File file =  new File(Constant.FILE_TEMPORARY_SVAE_DIR);	
		if (editClassImpl.exportGroupToExcel(getParaToInt("class_id"),file)){
			renderFile(file);
			return;
		}
		renderError("该班级未分组");
	}

	public void getGroupList(){
		renderJ("details", editClassImpl.getGroupList(getParaToInt("class_id")));
	}


}
