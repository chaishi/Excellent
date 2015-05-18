package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Award;
import edu.swust.cs.excellent.service.inter.IEditAward;

@Service("editAwardImpl")
public class EditAward implements IEditAward {

	

	@Override
	public boolean add(Award t) {
		try{
			t.save();
			return true;
		}catch(Exception e){
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Award merge(Award t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Award getDetail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getClassAward(String classNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getClassAward(int classId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getStudentAward(String schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getStudentAward(int stuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Award> getList(int numPage, int numPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

}
