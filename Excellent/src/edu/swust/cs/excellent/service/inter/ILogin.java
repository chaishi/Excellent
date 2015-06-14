package edu.swust.cs.excellent.service.inter;

import javax.servlet.http.HttpSession;

/**
 * 检查登陆情况
 * @author moulaotou
 *
 */
public interface ILogin {
	/**
	 * 
	 * @param uid 用户名 ad开头为管理员,te开头为教师，其余为学生
	 * @param pswd
	 * @param session 存放用户登录状态
	 * @return 登录是否成功
	 */
   boolean login(String uid,String pswd,HttpSession session);
   
  /**
   * 
   * @param session
   * @return
   */
   boolean logout(HttpSession session);
}
