package edu.swust.cs.excellent.service.inter;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Student;

public interface IEditStudent extends IBase<Student> {
  /**
   * 获取所有成员信息
   */
	public Page<Student> getStudentList();
	/**
	 * 获取某班成员信息
	 */
	public Page<Student> getStudentList(String classNum);

}
