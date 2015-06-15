package edu.swust.cs.excellent.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;

import edu.swust.cs.excellent.config.Constant;
import edu.swust.cs.excellent.model.Admin;
import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.model.Teacher;
import edu.swust.cs.excellent.service.inter.ILogin;

@Service("loginImpl")
public class LoginImpl implements ILogin {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");

	@Override
	public boolean login(String uid, String pswd,HttpSession session) {
		if (uid.startsWith("ad")){
			Admin admin = Admin.dao.findFirst("select name,true_name from admin  where name=? and pswd=?",uid,pswd);
			if (admin == null){
				return false;
			}
			session.setAttribute("userType", Constant.ADMIN);
			session.setAttribute("name", admin.get("name"));
			session.setAttribute("trueName", admin.get("true_name"));
			logger_disk.info("管理员:"+admin.get("name")+"登录");
			return true;
		}else if (uid.startsWith("te")){
			Teacher te = Teacher.dao.findFirst("select name,true_name from teacher where school_id=? and pswd=?",uid,pswd);
			if (te == null){
				return false;
			}
			session.setAttribute("userType", Constant.TEACHER);
			session.setAttribute("name", te.get("name"));
			session.setAttribute("trueName", te.get("true_name"));
			logger_disk.info("教师:"+te.get("name")+"登录");
			return true;
		}else{
			Student stu = Student.dao.findFirst("select name,true_name from student where school_id=? and pswd=?",uid,pswd);
			if (stu == null){
				return false;
			}
			session.setAttribute("userType", stu.getStr("classJob"));
			session.setAttribute("name", stu.get("name"));
			session.setAttribute("trueName", stu.get("true_name"));
			logger_disk.info("学生:"+stu.get("name")+"登录");
			return true;
		}
	}

	@Override
	public boolean logout(HttpSession session) {
		try {
			session.removeAttribute("userType");
			session.removeAttribute("name");
			session.removeAttribute("trueName");
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
