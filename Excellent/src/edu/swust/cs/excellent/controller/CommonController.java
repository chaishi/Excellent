package edu.swust.cs.excellent.controller;

import java.util.Map;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.spring.IocInterceptor;

import edu.swust.cs.excellent.config.Constant;

@Before(IocInterceptor.class)
public class CommonController extends Controller{

	private Response response = Response.getInstance();

	/**
	 * 
	 * @return 身份标签
	 *         0 学生
	 *         1教师
	 *         -1 其他
	 */
	int getFlag(){
		if (getSession().getAttribute("userType").equals(Constant.TEACHER)){
			return 1;
		}else if (getSession().getAttribute("userType").equals(Constant.STUDENT)){
			return 0;
		}
		return -1;
	}
	
	/**
	 * 
	 * @return 登录用户Id
	 */
	String getUserId(){
		return (String)  getSession().getAttribute("id");
	}

	/**
	 * 
	 * @return 用户名
	 */
	String getName(){
		return (String) getSession().getAttribute("name");
	}
	
	/**
	 * 
	 * @return 封装了getJson返回结构的Response
	 */
	Response getRes(){
		return response;
	}

	/**
	 * 
	 * @param key 键
	 * @param value 值
	 */
	void add(String key,Object value){
		response.add(key,value);
	}

	
	/**
	 * 错误返回
	 * @param error  错误信息
	 */
	public void  renderError(String error){
		renderJson(getRes().renderError(error));
	}
	/**
	 * 正确返回
	 */
	public void renderJ(){		
		add("userType",getSession().getAttribute("userType"));
		renderJson(getRes().render());
	}
	
	/**
	 * 
	 * @param key 键
	 * @param obj 值
	 */
	public void renderJ(String key,Object obj){
		add(key, obj);
		renderJ();
	}
	/**
	 * 操作成功
	 */
	public void renderSuccess(){
		renderJson(getRes().renderSuccess());
	}
	/**
	 * 返回boolean值
	 */
	public void renderJ(boolean b){
		if (b)
			renderSuccess();
		else
			renderError("");
	}
	
	
	/**
	 * 
	 * @param map 序列化的map
	 */
	public void renderM(Map<String, Object> map){
		map.put("success", true);
		renderJson(map);
	}
	
	/**
	 * 
	 * @param p 分页数据
	 */
	public void renderP(Page p,String key){
         add("pageSize",p.getPageSize());
         add("pageNumber", p.getPageNumber());
         add("totalRow", p.getTotalRow());
         add("totalPage", p.getTotalPage());
         add(key, p.getList());
         renderJ();
	}
}
