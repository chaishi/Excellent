package edu.swust.cs.excellent.service.inter;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Award;

public interface IEditAward extends IBase<Award>{
   /**
    * 获得班级获奖情况列表
    */
	public Page<Award> getClassAward(String classNum);
    /**
     * 获得班级获奖情况列表
     */
	public Page<Award> getClassAward(int classId);
	
	/**
	 * 获得个人获奖情况列表
	 */
	public Page<Award> getStudentAward(String schoolId);
	/**
	 * 活得个人获奖情况列表
	 */
	public Page<Award> getStudentAward(int stuId);
}
