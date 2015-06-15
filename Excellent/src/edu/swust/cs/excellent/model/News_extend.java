package edu.swust.cs.excellent.model;

import com.jfinal.plugin.activerecord.Model;

public class News_extend extends Model<News_extend>{
   public static News_extend dao = new News_extend();
   
   public void browse(int id){
	   int count = dao.findById(id).getInt("browses");
	   dao.findById(id).set("browses", count+1 ).update();
   }
}
