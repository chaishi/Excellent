package edu.swust.cs.excellent.controller;

import java.util.HashMap;
import java.util.Map;

public class Response {

	
	   /**
	    * 添加了"data"域的hashmap
	    */
	   private  Map<String,Object> response;
	   /**
	    * response中"data"域的值
	    */
	   private  Map<String,Object> resData;
	   
	   /**
	    * 
	    * @param res  元对象
	    * @return 拷贝
	    */
	   public Response(Response res){
		   this.resData = res.resData;
		   this.response = res.response;
	   }
	   
	   private Response(){
		   response = new HashMap<String, Object>();
		   resData = new HashMap<String, Object>();
	   }
	   
	   /**
	    * 
	    * @return 获取一个新的Response
	    */
	   public static Response getInstance(){
		    return new Response();
	   }
	   
	   /**
	    * 
	    * @param key 键
	    * @param value 值
	    */
	   public  void add(String key,Object value){
           resData.put(key, value);		   
	   }
	   
	   /**
	    * 
	    * @return 返回带data域并且data域中默认"success"域为true的hashmap对象 （*非单例）
	    */
	   public Map<String,Object> render(){
		   Response copyResponse = new Response(this);
		   resData.put("success",true);
	       return copyResponse.resData;
	   }
	   
	   /**
	    * 
	    * @return 操作失败
	    */
	   public Map<String,Object> renderError(){
		  response.put("success", false);
		  resData.clear();		  
		  return response;
	   }
	   
	   /**
	    * 
	    * @param error 错误信息
	    * @return 返回页面的map
	    */
	   public Map<String,Object> renderError(String error){
		   resData.put("error", error);
		   resData.put("success", false);
		   return resData;
	   }	   
	   /**
	    * 清理resData和response
	    */
	   public void clear(){
		   resData.clear();
		   response.put("success",true);
		   response.clear();
	   }
}
