package edu.swust.cs.excellent.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Award;
import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.service.inter.IEditStudent;

@Service("editStudentImpl")
public class EditStudentImpl extends BaseImpl implements IEditStudent {

	public static final String SELECT_STUDENT_DETAIL = "select a.id,a.true_name,a.photo,a.group_id,a.class_id,a.school_id,a.other,a.self_sign,b.group_name,c.id,c.classNum "
			+ "from student a,`group` b,class c "
			+ "where a.id=? and a.group_id=b.id and b.class_id=c.id";

	public static final String SELECT_STUDENT_LIST="select id,name,true_name "
			+ "from student  where class_id=? order by school_id";

	public static final String SELECT_STUDENT_QUERY=" from student a,class b,`group` c where a.group_id=c.id and  a.class_id=b.id ";
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
		if (t.update()){
			logger_disk.info("修改学号为"+t.getStr("school_id")+"姓名为:"+t.getStr("true_name")+"id为:"+t.getInt("id")+"的学生信息");
			return t;
		}
		lastError="尝试修改学号为"+t.getStr("school_id")+"姓名为:"+t.getStr("true_name")+"的学生信息失败";
		logger_disk.info("尝试修改学号为"+t.getStr("school_id")+"姓名为:"+t.getStr("true_name")+"id为:"+t.getInt("id")+"的学生信息失败");
		logger_disk_we.error("尝试修改学号为"+t.getStr("school_id")+"姓名为:"+t.getStr("true_name")+"id为:"+t.getInt("id")+"的学生信息失败");
		return null;
	}

	@Override
	public Page<Student> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student getDetail(int id) {	
		Student stu = Student.dao.findFirst(SELECT_STUDENT_DETAIL, id);

		List<Award> awards = Award.dao.find("select comment from award where refrence_id=? and flag=1",id);
		stu.put("prizes", awards);
		return stu;
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
	public List<Student> getStuList(int class_id) {
		return Student.dao.find(SELECT_STUDENT_LIST,class_id);
	}

	@Override
	public Page<Student> queryStudent(Student stu,String cls, int nowPage, int pageSize) {
		String sql=SELECT_STUDENT_QUERY;
		if  (stu.getStr("school_id")!=null && !stu.getStr("school_id").trim().equals("")){
			sql=sql+" and school_id like '%"+stu.getStr("school_id")+"%'";
		}
		if (stu.getStr("true_name")!=null && !stu.getStr("true_name").trim().equals("")){
			sql+=" and true_name like '%"+stu.getStr("true_name")+"%'";
		}
		if (!cls.equals("")){
			int cid=0;
			try {
				cid=Integer.parseInt(cls);
			} catch (Exception e) {
				lastError="班级数字序号错误";
				return  null;
			}
			sql+=" and b.id="+cid;
		}
		return Student.dao.paginate(nowPage, pageSize,"select a.id,a.true_name,a.photo,a.group_id,a.class_id,a.school_id,a.other,a.self_sign,b.classNum,c.group_name " , sql);
	}


}
