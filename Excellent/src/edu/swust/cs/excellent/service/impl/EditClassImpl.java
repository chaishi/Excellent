package edu.swust.cs.excellent.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;
import edu.swust.cs.excellent.service.inter.IEditClass;

@Service("editClassImpl")
public class EditClassImpl implements IEditClass {

	private static final String SELECT_SOFT_CLASS_INFO = "select study_model "
			+  "from class "
			+ "where classNum like \"%软%\"";
	private static final String SELECT_DISK_CLASS_INFO = "select study_model "
			+  "from class "
			+ "where classNum like \"%计%\"";

	private static final String UPDATE_SOFT_CLASS_INFO = "update class set study_model=? "
			+ "where classNum like \"%软%\"";
	private static final String UPDATE_DISK_CLASS_INFO = "update class set study_model=? "
			+ "where classNum like \"%计%\"";
	private static final String INSERT_GROUP = "insert into"
			+ " `group`(group_name,leader,slogan,flags,tips,class_id)  "
			+ "values (?,?,?,?,?,?)";


	@Override
	public boolean add(Class t) {
		try {
			t.save();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			Class.dao.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Class merge(Class t) {

		return null;
	}


	@Override
	public Page<Class> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getDetail(int id) {
		if (id==0){
			return Class.dao.findFirst(SELECT_SOFT_CLASS_INFO);
		}else if (id==1){
			return Class.dao.findFirst(SELECT_DISK_CLASS_INFO);
		}
		return null;
	}

	@Override
	public boolean recordFromExcel(File excelFile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetGroup(int stuId, int grouId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Group> getGroupList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Group> getGroupList(String classNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Class> getList(int numPage, int numPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean merge(Class cls, boolean b) {
		try {
			if (b){
				Db.update(UPDATE_SOFT_CLASS_INFO,cls.getStr("study_model"));
			}else{
				Db.update(UPDATE_DISK_CLASS_INFO,cls.getStr("study_model"));
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean addGroup(Group group) {
		try {
			Db.update(INSERT_GROUP,group.getStr("group_name"),
					group.getInt("leader"),
					group.getStr("slogan"),
					group.getStr("flags"),
					group.getStr("tips"),
					group.getInt("class_id"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteGroup(int id) {
		try {
			Group.dao.deleteById(id);
			return true;
		} catch (Exception e) {
		}
		return false;
	}



}
