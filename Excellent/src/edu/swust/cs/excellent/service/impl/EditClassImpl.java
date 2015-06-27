package edu.swust.cs.excellent.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.model.Group;
import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.service.inter.IEditClass;
import edu.swust.cs.excellent.util.ExcelTool;

@Service("editClassImpl")
public class EditClassImpl extends BaseImpl implements IEditClass {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");

	private static final String SELECT_CLASS_INFO = "select id,classNum "
			+  "from class where id!="+Constant.PUPPET_CLASS_ID
			+" order by classNum ";

	private static final String SELECT_SOFT_CLASS_INFO = "select study_model "
			+  "from class "
			+ "where classNum like '\"%软%'\"";
	private static final String SELECT_DISK_CLASS_INFO = "select study_model "
			+  "from class "
			+ "where classNum like '\"%计%'\"";

	private static final String UPDATE_SOFT_CLASS_INFO = "update class set study_model=? "
			+ "where classNum like \"%软%\"";
	private static final String UPDATE_DISK_CLASS_INFO = "update class set study_model=? "
			+ "where classNum like \"%计%\"";
	private static final String INSERT_GROUP = "insert into"
			+ " `group`(group_name,leader,slogan,flags,tips,achieve,class_id)  "
			+ "values (?,?,?,?,?,?,?)";
	private static final String INSERT_GROUP_FROM_EXCEL = "insert into"
			+ " `group`(group_name,leader,slogan,flags,tips,achieve)  "
			+ "values (?,?,?,?,?,?)";
	private static final String UPDATE_GROUP_FROM_EXCEL = "update `group`"
			+ " set class_id=? where class_id="+Constant.PUPPET_CLASS_ID;

	private static final String EXPORT_GROUP_TO_EXCEL = "select id,leader,group_name,slogan,achieve,flags,tips "
			+  "from `group`  where class_id=?";

	private static final String SELECT_LEADER_IN_GROUP =  "select school_id,true_name "
			+ "from student where a.id=";

	private static final String SELECT_STUDENT_IN_GROUP =  "select school_id,true_name "
			+ "from student a ,group b where a.group_id=b.group_id and a.id!=";

	private static final String SELECT_STUDY_MODEL="select study_model "
			+ " from class where classNum like ";

	@Override
	public boolean add(Class t) {
		try {
			if (t.getStr("study_model").trim().equals("")){
				String sql= SELECT_STUDY_MODEL;
				if (t.getStr("classNum").contains("软")){
					sql+="'%软%'";
				}else if (t.getStr("classNum").contains("计")){
					sql+="'%计%'";
				}else{
					logger_disk.warn("添加非软件或计科班级");
                    lastError="添加非软件或计科班级";
					return false;
				}

				List<Class> lt=Class.dao.find(sql);
				if (lt==null && lt.size()==0){
					logger_mail.warn(t.getStr("classNum")+"未设置默认班级简介");
					lastError=t.getStr("classNum")+"未设置默认班级简介";
					return false;
				}
				for (Class p:lt){
					if (p.getStr("study_model")!=null && !p.getStr("study_model").trim().equals("")){
                           t.set("study_model", p.get("study_model"));
                           break;
					}
				}


			}
			t.save();
			logger_disk.info("新增班级:"+t.getStr("classNum"));
			return true;
		} catch (Exception e) {
			logger_disk.warn("添加班级出错");
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			Class.dao.deleteById(id);
			return true;
		} catch (Exception e) {
			logger_disk.warn("删除班级出错",e);
		}
		return false;
	}

	@Override
	public Class merge(Class t) {

		return null;
	}


	@Override
	public Page<Class> getList() {

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
	public int[] recordFromExcel(File excelFile,int id) throws  IOException {
		int[] r = ExcelTool.readExcel(excelFile.getAbsolutePath(),INSERT_GROUP_FROM_EXCEL, Constant.EXCEL_GROUP_VALID_COLUMNS);
		Db.update(UPDATE_GROUP_FROM_EXCEL,id);
		return r;
	}

	@Override
	public boolean resetGroup(int stuId, int grouId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Group> getGroupList() {
		return null;
	}

	@Override
	public Page<Group> getGroupList(String classNum) {
		return Group.dao.paginate(1, 100, "select *", " from group where class_id=?",classNum);
	}

	@Override
	public Page<Class> getList(int numPage, int numPerPage) {
		return	Class.dao.paginate(1, 100000,"select id,classNum"  ,  " from class where id!="+Constant.PUPPET_CLASS_ID +" order by classNum ");
	}

	@Override
	public boolean merge(Class cls, boolean b) {
		try {
			if (b){
				Db.update(UPDATE_SOFT_CLASS_INFO,cls.getStr("study_model"));
			}else{
				Db.update(UPDATE_DISK_CLASS_INFO,cls.getStr("study_model"));
			}
			logger_disk.info("更新班级简介");
			return true;
		} catch (Exception e) {
			logger_disk.warn("更新班级简介出错");
		}
		return false;
	}

	@Override
	public boolean addGroup(Group group) {
		try {
			
		    Group g = Group.dao.findFirst("select id from `group` where group_name='?' and class_id=?",group.getStr("group_name"),group.getStr("class_id"));
			if (g!=null){
				lastError="该组名在改班级中已经添加";
				logger_disk.warn("该组名在改班级中已经添加");
				return false;
			}
			Db.update(INSERT_GROUP,group.getStr("group_name"),
					group.getInt("leader"),
					group.getStr("slogan"),
					group.getStr("flags"),
					group.getStr("tips"),
					group.getStr("achieve"),
					group.getInt("class_id"));
			logger_disk.info("插入新小组:"+group.toJson());
			return true;
		} catch (Exception e) {
			logger_disk.warn("新增小组出错");
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

	@Override
	public boolean exportGroupToExcel(int class_id, File file) {
		List<Group> group = Group.dao.find(EXPORT_GROUP_TO_EXCEL,class_id);
		if (group == null){
			return false;
		}
		List<ArrayList<Student>> stus = new ArrayList<ArrayList<Student>>();
		int i=0;
		int max=-1;
		for (Group p:group){
			stus.get(i).add(Student.dao.findFirst(SELECT_LEADER_IN_GROUP,p.getInt("id")));
			List<Student> stuList = Student.dao.find(SELECT_STUDENT_IN_GROUP,p.getInt("id"));
			max=max>stuList.size()+1?max:stuList.size()+1;
			stus.get(i).addAll(stuList);
			i++;
		}
		String[] ss = {"队名","口号","成就","好友标签","团队寄语"};
		String[] s = {"group_name","slogan","achieve","flags","tips"};
		ExcelTool.writeCombineExcel(ss,s, group, stus,max, file);
		return true;
	}

	@Override
	public List<Group> getGroupList(int class_id) {
		List<Group> group = Group.dao.find(EXPORT_GROUP_TO_EXCEL,class_id);
		return group;
	}

	
}
