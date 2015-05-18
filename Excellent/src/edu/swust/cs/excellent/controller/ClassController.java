package edu.swust.cs.excellent.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.Inject;

import edu.swust.cs.excellent.authorized.Authority;
import edu.swust.cs.excellent.authorized.AuthorityInterceptor;
import edu.swust.cs.excellent.authorized.LoginInterceptor;
import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;
import edu.swust.cs.excellent.service.inter.IEditClass;

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
    public void setClassInfo(){
    	 Class cls = new Class().set("study_model", getPara("introContent",""));
    	if (getPara("classType","").equals("卓越软件")){
    		renderJ(editClassImpl.merge(cls, true));   
    	}else if (getPara("classType","").equals("卓越计科"))
    	 renderJ(editClassImpl.merge(cls, false));
    }
    
    @Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
    public void newClass(){
    	renderJ(editClassImpl.add(new Class().set("classNum", getPara("className"))
    			                      .set("study_model",getPara("study_model",""))));
    }
    @Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
    public void deleteClass(){
    	renderJ(editClassImpl.delete(getParaToInt()));
    }
    
    @Authority({
 		Constant.AUTHORITY_ADMIN
 	})
 	@Before({
 		LoginInterceptor.class,AuthorityInterceptor.class
 	})
    public void addGroup(){
    	Group group = new Group().set("group_name",getPara("groupName")).
    			set("leader",getParaToInt("group_leader",0)).
    			set("slogan",getPara("slogan","")).
    			set("flags",getPara("flags","")).
    			set("tips",getPara("tips","")).
    			set("class_id",getParaToInt("class_id"));
    	renderJ(editClassImpl.addGroup(group));
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
    
}
