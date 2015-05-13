package edu.swust.cs.excellent.service.impl;

import java.io.File;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;
import edu.swust.cs.excellent.service.inter.IEditClass;

public class EditClassImpl implements IEditClass {

	@Override
	public boolean add(Class t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class merge(Class t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Class> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getDetail(int id) {
		// TODO Auto-generated method stub
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

}
