package edu.swust.cs.excellent.model;

/**
 * 轻量化的student
 * @author moulaotou
 *
 */
public class SimpleStudent {
	int id;
	String name;
	String photo;
	String schoolId;
    public SimpleStudent(Student stu){
    	id = stu.getInt("id");
    	name  = stu.getStr("name");
    	photo = stu.getStr("photo");
    	schoolId = stu.getStr("schoolId");
    }
}
