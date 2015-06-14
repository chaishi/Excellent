package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Award;
import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.service.inter.IEditAward;

@Service("editAwardImpl")
public class EditAward implements IEditAward {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");

	@Override
	public boolean add(Award t) {
		try{
			t.save();
			logger_disk.info("存储新奖项:id="+t.getInt("id"));
			return true;
		}catch(Exception e){
			logger_disk.warn("存储新奖项:id="+t.getInt("id")+"失败");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			Award.dao.deleteById(id);
			logger_disk.info("删除新奖项:id="+id);
			return true;
		} catch (Exception e) {
			logger_disk.info("删除新奖项:id="+id+"失败");
		}
		return false;
	}

	@Override
	public Award merge(Award t) {
		try {
			t.update();
			logger_disk.info("修改个人获奖情况"+t.getInt("id"));
			return t;
		} catch (Exception e) {
			logger_disk.warn("修改个人获奖情况"+t.getInt("id"));
		}
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
