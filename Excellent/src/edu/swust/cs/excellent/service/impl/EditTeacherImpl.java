package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Teacher;
import edu.swust.cs.excellent.service.inter.IEditTeacher;

@Service("editTeacherImpl")
public class EditTeacherImpl extends BaseImpl implements IEditTeacher{

	@Override
	public boolean add(Teacher t) {
		return t.save();
	}

	@Override
	public boolean delete(int id) {
		return Teacher.dao.deleteById(id);
	}

	@Override
	public Teacher merge(Teacher t) {
		if (t.update())
			return t;
		else return null;
	}

	@Override
	public Page<Teacher> getList() {
		return null;
	}

	@Override
	public Page<Teacher> getList(int numPage, int numPerPage) {
		return Teacher.dao.paginate(numPage, numPerPage, "select *", "from teacher order by school_id");
	}

	@Override
	public Teacher getDetail(int id) {
		// TODO Auto-generated method stub
		return null;
	}



}
