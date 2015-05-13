package edu.swust.cs.excellent.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import edu.swust.cs.excellent.model.Admin;
import edu.swust.cs.excellent.model.Student;
import edu.swust.cs.excellent.model.Teacher;
import edu.swust.cs.excellent.service.inter.ILogin;

@Service("loginImpl")
public class LoginImpl implements ILogin {

	@Override
	public boolean login(String uid, String pswd,HttpSession session) {
		if (uid.startsWith("ad")){
			Admin admin = Admin.dao.findFirst("select name where name=? and pswd=?",uid,pswd);
			if (admin == null){
				return false;
			}
			session.setAttribute("userType", "admin");
			session.setAttribute("name", admin.get("name"));
			return true;
		}else if (uid.startsWith("te")){
			Teacher te = Teacher.dao.findFirst("select name where school_id=? and pswd=?",uid,pswd);
			if (te == null){
				return false;
			}
			session.setAttribute("userType", "教师");
			session.setAttribute("name", te.get("name"));
		}else{
			Student stu = Student.dao.findFirst("select name where school_id=? and pswd=?",uid,pswd);
			if (stu == null){
				return false;
			}
			session.setAttribute("userType", stu.getStr("classJob"));
			session.setAttribute("name", stu.get("name"));
			return true;
		}
		return false;
	}

}
