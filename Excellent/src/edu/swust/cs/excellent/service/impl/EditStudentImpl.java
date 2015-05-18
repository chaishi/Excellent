package edu.swust.cs.excellent.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.service.inter.IEditStudent;

@Service("editStudentImpl")
public class EditStudentImpl implements IEditStudent {

	 public static final String SELECT_STUDENT_DETAIL = "select a.*,b.group_name,c.id,c.class_num "
              	+ "from student a,group b,class c "
                + "where a.id=? and a.group_id=b.id and b.class_id=c.id";
	 
	 public static final String SELECT_STUDENT_LIST="select id,name,true_name "
	 		                                       + "from student order by school_id";
	 
	@Override
	public boolean add(Student t) {
		try {
			t.save();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			Student.dao.deleteById(id);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public Student merge(Student t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Student> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student getDetail(int id) {	
		return Student.dao.findFirst(SELECT_STUDENT_DETAIL, id);
	}

	@Override
	public Page<Student> getStudentList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Student> getStudentList(String classNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Student> getList(int numPage, int numPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStuList() {
	   return Student.dao.find(SELECT_STUDENT_LIST);
	}

}
