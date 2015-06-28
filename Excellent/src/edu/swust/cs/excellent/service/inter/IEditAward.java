package edu.swust.cs.excellent.service.inter;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Award;

public interface IEditAward extends IBase<Award>{
   
	public static enum AWARD_LEVEL{
		一等奖,二等奖,三等奖,优秀奖,国家级,省级,校级,院级,专业级
	}
	
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
 
	/**
	 * 根据Id删除某个学生的获奖情况
	 * @param id
	 * @return
	 */
	public boolean deleByStuId(int id);
}
