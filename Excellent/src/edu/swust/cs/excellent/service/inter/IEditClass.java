package edu.swust.cs.excellent.service.inter;
import java.io.File;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;

public interface IEditClass extends IBase<Class> {
	/**
	 * 从excel导入分组信息
	 */
	public boolean recordFromExcel(File excelFile);
	/**
	 * 通过修改个人所在组别修改小组成员构成 
	 */
	public boolean resetGroup(int stuId,int grouId);

	/**
	 * @return 所有小组分组列表
	 */
	public Page<Group> getGroupList();
	
	/**
	 * @param classNum 班号
	 * @return 班级分组
	 */
	public Page<Group> getGroupList(String classNum);
	
}
