package edu.swust.cs.excellent.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;



public class Group extends Model<Group>{
   public static Group dao = new Group();
	
	/**
    * 小组成员
    */
   List<SimpleStudent> members;
   
}
