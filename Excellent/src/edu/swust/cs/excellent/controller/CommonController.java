package edu.swust.cs.excellent.controller;

import java.util.Map;



import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class CommonController extends Controller{

	private Response response = Response.getInstance();

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
